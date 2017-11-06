# AccountService
**https://localhost:8443**

* /customers : все абоненты(доступно только для админа)
* /customers/{id} : инфа об абоненте. id может быть @me, тогда юзер определяется из куки
* /customers/{id}/accounts : аккаунты абонента. POST - создать акк.
* /customers/{id}/accounts/{accId} : инфа  об аккаунте. PUT - изменить, DELETE - удалить акк.

### Авторизация
Простая. Абонент может смотреть и изменять только свои данные. Админ может все. 
  
### DB
h2 in memory. При старте заполняется данными из `src/main/resources/data.sql`
 
### Build & Run
Java 8

jar: `mvn clean package && java -jar target/dumb-account-service-1.0-SNAPSHOT.jar`

или 

`mvn spring-boot:run`

 

 
 
 
