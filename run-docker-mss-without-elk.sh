#docker clean
docker rm -f rabbitmq zipkin turbine hystrix-dashboard consul consul-agent consul-server consul-server-bootstrap zuul-server auth-service product-service recommendation-service review-service product-composite-service
#docker-compose run
docker-compose -f docker-compose-mss-without-elk.yml up