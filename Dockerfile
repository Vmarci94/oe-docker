FROM openjdk:11

WORKDIR /workdir

ENV APP_NAME=Main.jar
ENV ARGS='C:\oe\oe-docker\resource\file1.bin READ C:\oe\oe-docker\resource\file2.bin READ,WRITE'
ENV USERNAME=root
ENV GROUPID=0
USER $USERNAME:$GROUPID
VOLUME /wokrdir/resource /workdir/app

CMD java -jar /workdir/app/$APP_NAME $ARGS

