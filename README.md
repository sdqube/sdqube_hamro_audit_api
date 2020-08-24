# sdqube_hamro_audit
docker-compose -f docker-compose.yml up -d





Building docker image to docker-hub

 $  rm -rf build
 $  ./gradlew clean assembleBootDist
 $  docker image build -t hamro-audit .
 $  docker container ls -a
 $  docker container rm hamro-audit
 $  docker container run --name hamro-audit -p 8080:8080 -d hamro-audit
 $  docker logs hamro-audit -f





rm -rf build 
./gradlew clean assembleBootDist 
docker image build -t hamro-audit . 
docker container ls -a 
docker container rm hamro-audit 
docker container run --name hamro-audit -p 8080:8080 -d hamro-audit 
docker logs hamro-audit -f  




docker stop hamro-audit
docker container rm mongodb kafka kafdrop
docker rmi sdqube/hamro-audit


docker-compose -f docker-compose.yml up -d
docker-compose down