JOB_NAME=discovery-service
PORT=8761
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
$JOB_NAME
docker image prune -a -f