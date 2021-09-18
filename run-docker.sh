#docker clean
docker rm -f eureka-server zuul-server auth-server product-service recommendation-services review-service product-composite-service
#docker-compose run
docker-compose up