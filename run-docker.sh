#docker clean
docker rm -f eureka-server zuul-server auth-server product-service recommendation-service review-service product-composite-service
#docker-compose run
docker-compose up