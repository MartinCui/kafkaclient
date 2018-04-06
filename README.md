# kafkaclient

## Kafka-consumer
consums all messages for spefici kafka topic

supported environment properties:
1. bootstrap.servers (default=192.168.171.129:9092)
2. group.id (default=martincui-consumer)
3. topic (default=b2c-basic-contact-information)

use configuration file like below (saved as log4j2.xml and run jar) to save all messages into log.txt.
java -Dfile.encoding=UTF-8 -Dlog4j.configurationFile=log4j2.xml -jar kafkaclient-consumer-1.0.0-SNAPSHOT-jar-with-dependencies.jar

<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Properties>
    <Property name="filename">log.log</Property>
  </Properties>
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%highlight{%d [%t] %-5level: %msg%n%throwable}" disableAnsi="false"/>
        </Console>
        <File name="File" fileName="${filename}">
            <PatternLayout pattern="%highlight{%d [%t] %-5level: %msg%n%throwable}" disableAnsi="true"/>
        </File>
    </Appenders>
    <Loggers>
        <Logger name="com.martincui" level="trace" additivity="false">
            <AppenderRef ref="Console"/>
            <AppenderRef ref="File"/>
        </Logger>
        <Root level="error">
            <AppenderRef ref="Console"/>
        </Root>
    </Loggers>
</Configuration>


