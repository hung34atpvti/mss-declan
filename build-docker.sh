#BUILD SUPPORT
#build discovery server
cd microservices/support/discovery-server/
mvn clean install -Dmaven.test.skip=true
docker build -t eureka-server .
#build gateway server
cd ../edge-server/
mvn clean install -Dmaven.test.skip=true
docker build -t zuul-server .
#build auth-server
cd ../auth-server/
mvn clean install -Dmaven.test.skip=true
docker build -t auth-server .
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