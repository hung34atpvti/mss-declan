#docker clean
docker rm -f eureka-server zuul-server auth-service product-service recommendation-service review-service product-composite-service
#docker-compose run
docker-compose -f docker-compose-mss-without-elk.yml up