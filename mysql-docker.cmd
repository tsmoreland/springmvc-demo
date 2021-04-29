@echo off
docker run --name mySQL -e MYSQL_ROOT_PASSWORD=%MYSQL_ROOT_PASSWORD% -p 3306:3306 -v d:/var/storage/mysql:/var/lib/mysql  -d mysql:8.0.24