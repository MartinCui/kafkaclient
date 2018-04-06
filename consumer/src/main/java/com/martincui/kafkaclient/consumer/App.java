package com.martincui.kafkaclient.consumer;

import io.vertx.core.buffer.Buffer;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;

import static java.nio.charset.StandardCharsets.UTF_8;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws UnknownHostException {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getProperty("bootstrap.servers", "192.168.171.129:9092"));
        props.put(ConsumerConfig.GROUP_ID_CONFIG, System.getProperty("group.id", "martincui-consumer"));
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, InetAddress.getLocalHost().getHostName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        String topic = System.getProperty("topic", "b2c-basic-contact-information");
        consumer.subscribe(Collections.singletonList(topic));

        logger.info("=======================start reading topic {}=======================", topic);
        try {
            consumer.poll(0);
            consumer.seekToBeginning(consumer.assignment());
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(1000);
                if(records.isEmpty()){
                    logger.info("=======================end of reading topic {}. exiting.=======================", topic);
                    break;
                }

                for (ConsumerRecord<String, String> record : records) {
                    logger.debug("----------{}---------", record.offset());
                    for (Header header : record.headers()) {
                        String headerValue = null;
                        if (StringUtils.equalsIgnoreCase(header.key(), "X-TIMESTAMP")) {
                            Instant timestamp = getInstant(header.value());
                            headerValue = timestamp == null ? "N/A" : timestamp.toString();
                        } else {
                            headerValue = new String(header.value(), UTF_8);
                        }

                        logger.debug("{} : {}", header.key(), headerValue);
                    }

                    logger.debug("Key : {}", record.key());
                    logger.debug("Value : {}", record.value());
                }
            }
        } finally {
            consumer.close();
        }
    }

    private static Instant getInstant(byte[] bytes) {
        Objects.requireNonNull(bytes);

        if (bytes.length == 8) {
            Long l = Buffer.buffer(bytes).getLong(0);
            return Instant.ofEpochMilli(l);
        } else {
            try {
                return Instant.parse(new String(bytes, UTF_8));
            } catch (Throwable e) {
                logger.warn("getInstant: cannot convert bytes to Instant: {}", javax.xml.bind.DatatypeConverter.printHexBinary(bytes));
                return null;
            }
        }
    }
}
