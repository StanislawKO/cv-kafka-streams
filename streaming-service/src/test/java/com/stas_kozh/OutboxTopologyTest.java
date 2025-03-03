package com.stas_kozh;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.stas_kozh.topology.OutboxTopology;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.TopologyTestDriver;
import org.apache.kafka.streams.test.TestRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.stas_kozh.dto.CvDto;
import com.stas_kozh.dto.OutboxDto;
import com.stas_kozh.dto.OutboxType;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OutboxTopologyTest {
    public static final String OUTBOX_TOPIC = "cv-app.public.outbox";
    public static final String UNKNOWN_OUTBOX_TOPIC = "unknown-outbox-topic";
    public static final String CV_TOPIC = "cv-topic";

    private TestInputTopic<String, OutboxDto> outboxTopic;
    private TestOutputTopic<String, OutboxDto> unknownOutboxTopic;
    private TestOutputTopic<String, CvDto> cvTopic;
    private TopologyTestDriver topologyTestDriver;

    @BeforeEach
    public void setUp() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        Properties props = new Properties();
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class.getName());

        new OutboxTopology().outboxStream(streamsBuilder,
                        outboxDtoJsonSerde(),
                        outboxObjectMapper(),
                        OUTBOX_TOPIC,
                        UNKNOWN_OUTBOX_TOPIC,
                        CV_TOPIC);

        Topology topology = streamsBuilder.build();
        topologyTestDriver = new TopologyTestDriver(topology, props);

        Serde<String> keySerde = Serdes.String();
        JsonSerde<OutboxDto> outboxSerde = outboxDtoJsonSerde();
        JsonSerde<CvDto> cvDtoSerde = new JsonSerde<>(CvDto.class);

        outboxTopic = topologyTestDriver.createInputTopic(
                OUTBOX_TOPIC,
                keySerde.serializer(),
                outboxSerde.serializer());

        unknownOutboxTopic = topologyTestDriver.createOutputTopic(
                UNKNOWN_OUTBOX_TOPIC,
                keySerde.deserializer(),
                outboxSerde.deserializer());

        cvTopic = topologyTestDriver.createOutputTopic(
                CV_TOPIC,
                keySerde.deserializer(),
                cvDtoSerde.deserializer());
    }

    @AfterEach
    void tearDown() {
        topologyTestDriver.close();
    }

    @Test
    void shouldAddCvDtoToKafkaTopicOnValidOutbox() throws JsonProcessingException {
        //Given
        OutboxDto outboxDto = new OutboxDto("1", LocalDateTime.now(), """
                {
                  "id":null,
                  "education" : "MIT",
                  "description": "i am top programmer",
                  "workExperience": "I have been working for 5 years",
                  "skills": ["Java", "Python"],
                  "languages": ["Russian", "English"],
                  "certificates": ["Certificate 1", "Certificate 2"],
                  "linkedId": "linkedId",
                  "isOpenToWork": true,
                  "employeeId":18
                }
                """,
                OutboxType.CV);
        CvDto cvDto = outboxObjectMapper().readValue(outboxDto.payload(), CvDto.class);

        //When
        outboxTopic.pipeInput(outboxDto.id(), outboxDto);

        //Then
        TestRecord<String, CvDto> record = cvTopic.readRecord();
        assertEquals(cvDto, record.value());
        assertArrayEquals(outboxDto.id().getBytes(), record.headers().lastHeader("messageId").value());
        assertTrue(this.unknownOutboxTopic.isEmpty());
    }

    private ObjectMapper outboxObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    private JsonSerde<OutboxDto> outboxDtoJsonSerde() {
        JsonSerde<OutboxDto> outboxSerde = new JsonSerde<>();
        outboxSerde.deserializer().configure(
                Map.of(
                        JsonDeserializer.TRUSTED_PACKAGES, "com.stas_kozh.*",
                        JsonDeserializer.VALUE_DEFAULT_TYPE, OutboxDto.class
                ),
                false);

        return outboxSerde;
    }
}