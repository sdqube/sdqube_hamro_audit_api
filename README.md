# sdqube_hamro_audit
```shell script
docker-compose -f docker-compose.yml up -d
docker-compose run mongodb kafka kafdrop sdqube/hamro-audit
```

### Building docker image to docker-hub

```shell script
 $  rm -rf build
 $  ./gradlew clean assembleBootDist
 $  docker image build -t hamro-audit .
 $  docker container ls -a
 $  docker container rm hamro-audit
 $  docker container run --name hamro-audit -p 8080:8080 -d hamro-audit
 $  docker logs hamro-audit -f
```

### Command line to run docker code in local docker machine

```shell script
$ rm -rf build
$ ./gradlew clean assembleBootDist
$ docker image build -t hamro-audit .
$ docker container ls -a
$ docker container rm hamro-audit
$ docker container run --name hamro-audit -p 8080:8080 -d hamro-audit
$ docker logs hamro-audit -f
```

### Stop docker compose
```shell script
docker-compose -f docker-compose.yml up -d
docker-compose down
```


### Command line to remove docker containers 
```shell script
docker stop hamro-audit
docker container rm mongodb kafka kafdrop
docker rmi sdqube/hamro-audit
```  







