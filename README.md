# Music Browser API
### build with Java8, Spring Boot

## first install dependency
  #### mvn clean install

## start it up
  #### mvn sprint-boot:run

### Spring Security Demo

##### Note
- Authentication Filter - a filter for specific auth type in SS filter chain (i.e basic auth, remember me cookie, etc)
- Authentication Manager - Standard API used by filter
- Authentication Provider - The implementation of Auth (in memory, db, etc.)
- User Details Service - Service to provide information about user
- Password Encoder - Service to encrypt and verify passwords
- Security Context - Holds details about authenticated entity

#### How does Spring Data JPA manage transactions?
- Unless you've initialized a transaction in your code, Spring Data JPA will implicitly create a transaction when repository methods are called. This can later cause you issues with lazily loaded references - if you try to access them outside of the transactional scope.


  <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" / >
