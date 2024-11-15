FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY . /app
RUN mvn clean install -DskipTests -B

FROM openjdk:17.0.1-jdk-slim

ENV OTEL_JAVA_GLOBAL_AUTOCONFIGURE_ENABLED=true
ENV OTEL_SERVICE_NAME=order-backend
ENV OTEL_EXPORTER_OTLP_PROTOCOL=http/protobuf
ENV OTEL_PROPAGATORS=tracecontext

ENV OTEL_TRACES_EXPORTER=otlp
ENV OTEL_EXPORTER_OTLP_TRACES_PROTOCOL=http/protobuf
ENV OTEL_EXPORTER_OTLP_TRACES_ENDPOINT=http://opentelemetry-collector:4318/v1/traces
# ENV OTEL_EXPORTER_OTLP_TRACES_HEADERS=Authorization=Api-Token $OTEL_INGEST_TOKEN

ENV OTEL_METRICS_EXPORTER=otlp
ENV OTEL_EXPORTER_OTLP_METRICS_PROTOCOL=http/protobuf
ENV OTEL_EXPORTER_OTLP_METRICS_ENDPOINT=http://opentelemetry-collector:4318/v1/metrics
ENV OTEL_EXPORTER_OTLP_METRICS_TEMPORALITY_PREFERENCE=DELTA
# ENV OTEL_EXPORTER_OTLP_METRICS_HEADERS=Authorization=Api-Token $OTEL_INGEST_TOKEN

ENV OTEL_LOGS_EXPORTER=otlp
ENV OTEL_EXPORTER_OTLP_LOGS_PROTOCOL=http/protobuf
ENV OTEL_EXPORTER_OTLP_LOGS_ENDPOINT=http://opentelemetry-collector:4318/v1/logs
# ENV OTEL_EXPORTER_OTLP_LOGS_HEADERS=Authorization=Api-Token $OTEL_INGEST_TOKEN

WORKDIR /app
COPY --from=build /app/order-backend/target/order-backend-1.0-SNAPSHOT-all.jar order-backend.jar
COPY opentelemetry-javaagent.jar opentelemetry-javaagent.jar

EXPOSE 8080

ENTRYPOINT ["java", "-javaagent:opentelemetry-javaagent.jar", "-jar", "order-backend.jar"]
# ENTRYPOINT ["java", "-jar", "backend.jar"]
