package com.martincui.kafkaclient.producer;

import io.vertx.core.buffer.Buffer;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import static java.nio.charset.StandardCharsets.UTF_8;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Throwable {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getProperty("bootstrap.servers", "192.168.171.129:9092"));
        props.put(ProducerConfig.CLIENT_ID_CONFIG, InetAddress.getLocalHost().getHostName());
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 100);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        String topic = System.getProperty("topic", "b2c-basic-contact-information");
        String key = System.getProperty("key");
        String value = System.getProperty("value");
        String headerEvent =  System.getProperty("header.event");
        String headerUserId =  System.getProperty("header.userId");
        String headerTimestamp = System.getProperty("header.timestamp", Instant.now().toString());
        String headerBusinessUnit = System.getProperty("header.businessUnit", "b2c");
        String headerCorrelationId = System.getProperty("header.correlationId");

        RecordHeaders headers = new RecordHeaders();
        runIfNotNull(headerEvent, (v) -> headers.add(new RecordHeader("X-EVENT", v)));
        runIfNotNull(headerUserId, (v) -> headers.add(new RecordHeader("X-USER-ID", v)));
        runIfNotNull(headerTimestamp, (v) -> headers.add(new RecordHeader("X-TIMESTAMP", v)));
        runIfNotNull(headerBusinessUnit, (v) -> headers.add(new RecordHeader("X-BUSINESS-UNIT", v)));
        runIfNotNull(headerCorrelationId, (v) -> headers.add(new RecordHeader("X-EF-CORRELATION-ID", v)));

        Producer<String, String> producer = new KafkaProducer<>(props);
        logger.info("sending message: {} - {}", key, value);
        Future<RecordMetadata> future = producer.send(new ProducerRecord<String, String>(topic, null, null, key, value, headers));
        logger.info("message was sent. metadata returned is: {}", future.get().toString());
        producer.close();
    }

    private static void runIfNotNull(String str, Consumer<byte[]> callback){
        if(StringUtils.isNotBlank(str)){
            callback.accept(str.getBytes(StandardCharsets.UTF_8));
        }
    }
}
