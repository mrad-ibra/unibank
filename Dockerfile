FROM openjdk:22-oracle
ARG JAR_FILE=unitech/target/*.jar
COPY ${JAR_FILE} unitech.jar
#ln -s /lib/libc.musl-x86_64.so.1 /lib/ld-linux-x86-64.so.2
EXPOSE 8090
ENTRYPOINT ["java","-jar", "/unitech.jar"]