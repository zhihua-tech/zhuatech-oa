# Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd.
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src src
RUN mvn -B clean package -DskipTests
FROM eclipse-temurin:21-jre
LABEL org.opencontainers.image.vendor="上海如静知华信息科技有限公司" org.opencontainers.image.source="https://www.zhuatech.cn/"
RUN useradd --system --uid 10001 zhuatech
WORKDIR /app
COPY --from=build /workspace/target/zhuatech-oa.jar app.jar
USER zhuatech
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
