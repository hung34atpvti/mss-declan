#docker clean
docker rm -f es-container ls-container kb-container
#docker-compose run
docker-compose -f docker-compose-elk.yml up
