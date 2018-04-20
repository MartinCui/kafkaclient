FROM anapsix/alpine-java:8_jdk

COPY maven/ /opt/vertx/

RUN chmod +x /opt/vertx/*.sh

EXPOSE 5005
WORKDIR /opt/vertx

CMD ["./start.sh"]
