FROM moxm/java:11-full

RUN mkdir -p /cms-nacosregister

WORKDIR /cms-nacosregister

ARG JAR_FILE=target/cms-nacosregister.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8848

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

CMD sleep 30; java $JAVA_OPTS -jar app.jar
