# kafkaclient

## consumer
consums all messages for spefici kafka topic

supported environment properties:
1. bootstrap.servers (default=192.168.171.129:9092)
2. group.id (default=martincui-consumer)
3. topic (default=b2c-basic-contact-information)

use configuration file like below (saved as log4j2.xml and run jar) to save all messages into log.txt.
java -Dfile.encoding=UTF-8 -Dlog4j.configurationFile=log4j2.xml -jar kafkaclient-consumer-1.0.0-SNAPSHOT-jar-with-dependencies.jar

```xml
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
```


## producer
produces kafka messages with header support

supported environment properties:
1. bootstrap.servers (default=192.168.171.129:9092)
2. header.event (default null, and no X-EVENT)
3. topic (default=b2c-basic-contact-information)
4. key
5. value
6. header.userId (default null, and no X-USER-ID in header)
7. header.timestamp (default Instant.now())
8. header.businessUnit (default b2c)
9. header.correlationId (default null and no X-EF-CORRELATION-ID in header)


use configuration file like below (saved as log4j2.xml and run jar) to save all messages into log.txt.
java -Dbootstrap.servers=kafka:9092 -Dheader.event=studyPlan/updated -Dtopic=b2c-integration-updates -Dkey=35844553-db0f-463d-8c55-ef1046e5f7c8 -Dvalue='{"abTestGroup": "abTestGroup","businessUnit": "b2c","completeDate": 1516199299,"courseTypeCode": "ccc","estimatedDays": 555,"goalDate": 1516199299,"halfwayCheckDate": 1516199299,"insertDate": 1516199299,"isFailed": false,"isLastInLevel": false,"lastLevelSeqNo": 5,"lastTimestamp": 1516199299,"lastUnitCompleteDate": 1516199299,"lastUnitId": "35844553-db0f-463d-8c55-ef1046e5f7c8","lastUnitSeqNo": 44,"lastUnitTitle": "lastUnitTitle","lastUnitWasLastInLevel": false,"levelHasCertificate": true,"levelId": "10923c9c-e593-4f18-be37-994f938c812b","levelSeqNo": 1,"levelStatus": "levelStatus","levelStatusRefresh": 1516199299,"locale": "gb","nextRefresh": 1516199299,"nextStepEmailType": "nextStepEmailType","nextStepName": "nextStepName","paceHours": "paceHours","previousLevelSeqNo": 2,"previousUnit": "previousUnit","realityCheckDate": 1516199299,"startDate": 1516199299,"studentId": "4860fc5e-0bb4-48e1-8e51-d5c63a6fdc4f","unitId": "38fd3617-db6b-4906-b0c4-0750ba0d2608","unitSeqNo": 22,"unitTitle": "ttttt","updateBatch": "80305ddd-f0b3-47ef-8061-004002cbfe0f","updateDate": 1516199299,"velocityStatus": "velocityStatus","version": "1.0","welcomeStatus": "welcomeStatus"}' -Dheader.userId=35844553-db0f-463d-8c55-ef1046e5f7c8 -Dheader.businessUnit=b2c -Dheader.correlationId=35844553-db0f-463d-8c55-ef1046e5f7c8 -jar kafkaclient-producer-1.0.0-SNAPSHOT-jar-with-dependencies.jar

```xml
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
```

