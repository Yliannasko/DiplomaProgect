# Процедура запуска автотестов:
## Подготовительный этап:
1. Установить и запустить IntelliJ IDEA;
2. Установать и запустить Docker Desktop;
3. Установить и запустить DBeaver;
4. Склонировать проект [по ссылке](https://github.com/Yliannasko/DiplomaProgect.git)
## Инструкция по запуску тестов:
1. Открыть проект в IntelliJ IDEA;
2. В терминале запустить контейнеры командой: docker-compose up --build 
3. В новой вкладке терминала запустить тестируемое приложение:
- для mysql java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar
- для postgresql java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar
4. Приложение открыть по адресу http://localhost:8080/
5. Запуск тестов стоит выполнить с параметрами, указав путь к базе данных в командной строке:
- для mysql ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"
- для postgresql ./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"
6. Для формирования отчета (Allure), после выполнения тестов выполнить команду: ./gradlew allureServe
