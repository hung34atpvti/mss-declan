#BUILD SUPPORT
#build discovery server
#cd microservices/support/discovery-server/
#mvn clean install -Dmaven.test.skip=true
#docker build -t eureka-server .
#build gateway server
cd microservices/support/edge-server/
mvn clean install -Dmaven.test.skip=true
docker build -t zuul-server .
#build turbine
cd ../turbine/
mvn clean install -Dmaven.test.skip=true
docker build -t turbine .
#build hystrix-dashboard
cd ../monitor-dashboard/
mvn clean install -Dmaven.test.skip=true
docker build -t hystrix-dashboard .
#build auth-server
cd ../auth-server/
mvn clean install -Dmaven.test.skip=true
docker build -t auth-service .
#BUILD CORE
#build product-service
cd ../../core/product-service/
mvn clean install -Dmaven.test.skip=true
docker build -t product-service .
#build recommendation-service
cd ../recommendation-service/
mvn clean install -Dmaven.test.skip=true
docker build -t recommendation-service .
#build review-service
cd ../review-service/
mvn clean install -Dmaven.test.skip=true
docker build -t review-service .
#BUILD COMPOSITE
#build product-composite-service
cd ../../composite/product-composite-service/
mvn clean install -Dmaven.test.skip=true
docker build -t product-composite-service .