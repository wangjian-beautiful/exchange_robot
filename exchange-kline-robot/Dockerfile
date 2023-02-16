FROM openjdk:11.0.15-jre-bullseye
ENV JAVA_OPTS="-Xms128m -Xmx256m -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:18981"
WORKDIR /usr/local

COPY target/exchange-robot.jar .
EXPOSE 8981 18981

CMD java $JAVA_OPTS -jar exchange-robot.jar