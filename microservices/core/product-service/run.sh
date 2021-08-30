JOB_NAME=product-service
PORT=8770
HOST=192.168.0.9
mvn clean install -Dmaven.test.skip=true
echo '####################################################################### RUN WITH DOCKER #################################################################################'
docker build -t $JOB_NAME .
docker stop $JOB_NAME || true
sleep 2
docker rm -f $JOB_NAME
docker run -d \
--name $JOB_NAME \
--restart on-failure:5 \
-m 2.2g \
-p $PORT:$PORT \
-e eureka.client.service-url.defaultZone=http://${HOST}:8761/eureka/ \
-e eureka.instance.preferIpAddress=true \
$JOB_NAME