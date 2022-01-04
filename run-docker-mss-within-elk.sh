#docker clean
docker rm -f zipkin rabbitmq consul consul-agent consul-server consul-server-bootstrap zuul-server auth-service product-service recommendation-service review-service product-composite-service es-container ls-container kb-container
#docker-compose run
docker-compose -f docker-compose-elk.yml -f docker-compose-mss-within-elk.yml up