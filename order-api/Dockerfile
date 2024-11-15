ARG DT_CODE_MODULE
ARG GITHUB_USER

FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY . /app
RUN mvn clean install -DskipTests -B

FROM ${DT_CODE_MODULE} AS codemodules

FROM openjdk:17.0.1-jdk-slim

COPY --from=codemodules / /
ENV LD_PRELOAD=/opt/dynatrace/oneagent/agent/lib64/liboneagentproc.so

ENV OTEL_PROPAGATORS=tracecontext
ENV OTEL_TRACES_EXPORTER=none

ENV OTEL_SERVICE_NAME=order-api-${GITHUB_USER}
ENV OTEL_METRICS_EXPORTER=otlp
ENV OTEL_EXPORTER_OTLP_METRICS_PROTOCOL=http/protobuf
ENV OTEL_EXPORTER_OTLP_METRICS_ENDPOINT=http://opentelemetry-collector:4318/v1/metrics
ENV OTEL_JAVA_GLOBAL_AUTOCONFIGURE_ENABLED=true
ENV OTEL_EXPORTER_OTLP_METRICS_TEMPORALITY_PREFERENCE=DELTA
# ENV OTEL_EXPORTER_OTLP_METRICS_HEADERS=Authorization=Api-Token dt0c01.########################.################################################################

ENV OTEL_LOGS_EXPORTER=otlp
ENV OTEL_EXPORTER_OTLP_LOGS_PROTOCOL=http/protobuf
ENV OTEL_EXPORTER_OTLP_LOGS_ENDPOINT=http://opentelemetry-collector:4318/v1/logs
# ENV OTEL_EXPORTER_OTLP_LOGS_HEADERS=Authorization=Api-Token dt0c01.########################.################################################################

WORKDIR /app
COPY --from=build /app/order-api/target/order-api-1.0-SNAPSHOT-all.jar order-api.jar

ENTRYPOINT ["java", "-jar", "order-api.jar"]
