*Запуск SUT*

java -jar E:\Projects(Study)\SQL\artifacts\app-deadline.jar -P:jdbc.url=jdbc:mysql://localhost:3306/app -P:jdbc.user=app -P:jdbc.password=qwerty123

*Запуск SQL* 

docker-compose exec mysql mysql -u app -p app