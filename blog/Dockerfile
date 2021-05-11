FROM java:8

COPY *.jar /app.jar

CMD ["--server.port=12306"]

EXPOSE 12306

ENTRYPOINT ["java","-jar","/app.jar"]