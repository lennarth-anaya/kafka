# kafka


## Startup Kafka and Schema Registry

```
sudo docker run -e ADV_HOST=localhost \
    -v $PWD/license.json:/license.json \
    --rm -p 3030:3030 -p 9092:9092 -p 2181:2181 -p 8081:8081 \
        -p 9581:9581 -p 8083:8083 \
    -p 9582:9582 -p 9584:9584 -p 9585:9585 landoop/kafka-lenses-dev
```

## Start

Remove debug-jvm flag if you don't have an IDE to debug it.

Linux
```
./gradlew clean build
SPRING_PROFILES_ACTIVE=local ./gradlew bootRun --debug-jvm
```

Windows
```
set SPRING_PROFILES_ACTIVE=local
gradlew clean build
gradlew bootRun --debug-jvm
```

## Test 1. Using postman to test alternative input

```
    URL: http://localhost:7071/avro
    HTTP method: POST
    HTTP headers: Content-Type=application/json;charset=UTF-8
    Request body:
    {
        "attONE": "web-test",
        "subsection": {
            "attTWO": 88,
            "initialTimeStamp": 8272726,
            "nullableTimeStamp": 455,
            "favoriteFruits": ["only carrot"]
        }
    }
```

Try again removing nullable attribute `nullableTimeStamp`, it should reach topic bound by null-date-output.

```
    URL: http://localhost:7071/avro
    HTTP method: POST
    HTTP headers: Content-Type=application/json;charset=UTF-8
    Request body:
    {
        "attONE": "web-test",
        "subsection": {
            "attTWO": 88,
            "initialTimeStamp": 8272726,
            "favoriteFruits": ["only carrot"]
        }
    }
```



## Test 2. You could download Confluent Kafka command line tools to post records

```
cd ...kafka folder...
wget http://packages.confluent.io/archive/5.0/confluent-oss-5.0.1-2.11.tar.gz
```

```
./bin/kafka-avro-console-producer \
    --broker-list localhost:9092 \
    --property schema.registry.url=http://localhost:8081 \
    --topic "input-topic-kafka-client" \
    --property value.schema='{"type":"record","name":"SomeInputRecord","namespace":"org.lrth.kafkaavro.model.avro","fields":[{"name":"attONE","type":"string"},{"name":"subsection","type":{"type":"record","name":"subsection","fields":[{"name":"attTWO","type":"int"},{"name":"initialTimeStamp","type":{"type":"long","logicalType":"timestamp-millis"}},{"name":"nullableTimeStamp","type":["long","null"]},{"name":"favoriteFruits","type":{"type":"array","items":"string"}}]}}]}' \
< sample-record.avr
```

Where sample-record.avr is:

```
{"attONE":"xXx","subsection":{"attTWO":33,"initialTimeStamp":1542983104784,"nullableTimeStamp":{"long": 1542983104784},"favoriteFruits":["apples","watermelon"]}}
```
## Check tests

Only records with non-null `nullableTimeStamp` should have reached next topic:

```
./bin/kafka-avro-console-consumer \
    --bootstrap-server localhost:9092 \
    --property schema.registry.url=http://localhost:8081 \
    --from-beginning \
    --topic "output-topic-main"
```

All records should have reached next topic, but all of them should not have fruits attribute:

```
./bin/kafka-avro-console-consumer \
    --bootstrap-server localhost:9092 \
    --property schema.registry.url=http://localhost:8081 \
    --from-beginning \
    --topic "output-topic-fruits-att-excluded"
```

Only records with `nullableTimeStamp` null should have reached next topic:

```
./bin/kafka-avro-console-consumer \
    --bootstrap-server localhost:9092 \
    --property schema.registry.url=http://localhost:8081 \
    --from-beginning \
    --topic "output-topic-null"
```

