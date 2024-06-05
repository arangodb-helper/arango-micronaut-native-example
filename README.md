# arango-micronaut-native-example project

Example application using ArangoDB Java driver integrated with:
- [Micronaut configuration](./src/main/kotlin/com/example/ArangoConfig.kt)
- [Micronaut serialization](./src/main/kotlin/com/example/ArangoService.kt)
- [native image](#native-image)

## prerequisites

Start a local database:

```shell script
SSL=true ./docker/start_db.sh
``` 

## test

```shell script
mvn test
```

## test shaded

```shell script
mvn test -Dshaded
```

## native image

```shell script
mvn package -Dpackaging=native-image
./target/demo
curl -X GET http://localhost:8080/version
curl -X GET http://localhost:8080/order
```

## native image shaded

```shell script
mvn package -Dpackaging=native-image -Dshaded
./target/demo
curl -X GET http://localhost:8080/version
curl -X GET http://localhost:8080/order
```
