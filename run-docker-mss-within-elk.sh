#docker clean
docker rm -f eureka-server zuul-server auth-service product-service recommendation-service review-service product-composite-service elasticsearch logstash kibana
#docker-compose run
docker-compose -f docker-compose-elk.yml -f docker-compose-mss-elk.yml up