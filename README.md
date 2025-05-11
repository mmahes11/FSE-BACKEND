# FULL STACK APPLICATION DEVELOPMENT ASSIGNMENT- WEB APPLICATION DEVELOPMENT

# Vaccination Portal Backend API

# Spring Boot JWT Authentication with Spring Security & Spring Data JPA for SCHOOL VACCINATION PORTAL

## User Registration, User Login and Authorization process.
The diagram shows flow of how we implement User Registration, User Login and Authorization process.

![spring-boot-jwt-authentication-spring-security-flow](spring-boot-jwt-authentication-spring-security-flow.png)

## Spring Boot Server Architecture with Spring Security
You can have an overview of our Spring Boot Server with the diagram below:

![spring-boot-jwt-authentication-spring-security-architecture](spring-boot-jwt-authentication-spring-security-architecture.png)

## Dependency
– If you want to use PostgreSQL:
```xml
<dependency>
  <groupId>org.postgresql</groupId>
  <artifactId>postgresql</artifactId>
  <scope>runtime</scope>
</dependency>
```
– or MySQL:
```xml
<dependency>
  <groupId>com.mysql</groupId>
  <artifactId>mysql-connector-j</artifactId>
  <scope>runtime</scope>
</dependency>
```
## Configure Spring Datasource, JPA, App properties
Open `src/main/resources/application.properties`
- For PostgreSQL:
```
spring.datasource.url= jdbc:postgresql://localhost:5432/testdb
spring.datasource.username= postgres
spring.datasource.password= 123

spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation= true
spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.PostgreSQLDialect

# Hibernate ddl auto (create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto= update

# App Properties
bezkoder.app.jwtSecret= bezKoderSecretKey
bezkoder.app.jwtExpirationMs= 86400000
```
- For MySQL
```
spring.datasource.url=jdbc:mysql://localhost:3306/testdb_spring?useSSL=false
spring.datasource.username=root
spring.datasource.password=123456

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update

# App Properties
bezkoder.app.jwtSecret= ======================BezKoder=Spring===========================
bezkoder.app.jwtExpirationMs=86400000
```
## Run Spring Boot application
```
mvn spring-boot:run
```

## Run following SQL insert statements
```
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
```


This RESTful API provides endpoints for managing users, students, vaccination drives, and vaccination records. It is designed for educational or institutional vaccination management use cases.

---

## Table of Contents

- [Authentication](#authentication)
- [Student Management](#student-management)
- [Vaccination Drive Management](#vaccination-drive-management)
- [Vaccination Record Management](#vaccination-record-management)
- [Notes](#notes)

---

## Authentication

- **User Sign Up**
    - **POST** `/api/auth/signup`
    - Register a new user account (roles supported: user, admin, moderator).
    - **Request Body:** `username`, `email`, `password`, and optional `roles`.

- **User Sign In**
    - **POST** `/api/auth/signin`
    - Authenticate user credentials. On success, returns a JWT token.
    - **Request Body:** `username` and `password`.

---

## Student Management

- **Add Student**
    - **POST** `/students`
    - Add a new student.
    - **Request Body:** `name`, `age`, `className`, `vaccinationStatus`

- **Get All Students**
    - **GET** `/students`
    - Retrieve a list of all students.

- **Update Student**
    - **PUT** `/students/{id}`
    - Update details of a student by their ID.
    - **Request Body:** Updated student details.

- **Delete Student**
    - **DELETE** `/students/{id}`
    - Remove a student by their ID.

---

## Vaccination Drive Management

- **Add Vaccination Drive**
    - **POST** `/drives`
    - Add a new vaccination drive.
    - **Request Body:** `vaccineName`, `driveDate`, `availableDoses`, `applicableClasses`

- **Get All Drives**
    - **GET** `/drives`
    - Retrieve a list of all vaccination drives.

- **Update Drive**
    - **PUT** `/drives/{id}`
    - Update a vaccination drive by its ID.
    - **Request Body:** Updated drive details.

- **Delete Drive**
    - **DELETE** `/drives/{id}`
    - Remove a vaccination drive by its ID.

---

## Vaccination Record Management

- **Add Vaccination Record**
    - **POST** `/api/vaccination-records`
    - Add a new vaccination record linking a student to a vaccination drive.
    - **Request Body:** `studentId`, `driveId`, `vaccinationDate`

- **Get All Vaccination Records**
    - **GET** `/api/vaccination-records`
    - Retrieve all vaccination records.

- **Get All Vaccination Record Details (Joined)**
    - **GET** `/api/vaccination-records/details`
    - Retrieve detailed vaccination records joined with corresponding student and drive info.

---

## Notes

- **Format:** All endpoints accept and respond with JSON unless specified otherwise.
- **Authentication:** Except for sign in/up, most endpoints require a valid JWT in the `Authorization` header.
- **Error Handling:** Standard HTTP status codes and messages are used for unsuccessful operations.

---

## Database Schema

The project uses a PostgreSQL database. Below are the key tables and relationships for the vaccination portal backend:

### Tables

- **users**
  - `id` (PK): integer, auto-increment
  - `username`: string, unique, required
  - `email`: string, unique, required
  - `password`: string, required

- **roles**
  - `id` (PK): integer, auto-increment
  - `name`: string, unique, required

- **user_roles**
  - `user_id` (FK → users.id)`
  - `role_id` (FK → roles.id)`
  - Composite primary key: (`user_id`, `role_id`)

- **student**
  - `id` (PK): integer, auto-increment
  - `name`: string, required
  - `age`: integer
  - `class_name`: string
  - `vaccination_status`: string

- **vaccination_drive**
  - `id` (PK): integer, auto-increment
  - `vaccine_name`: string, required
  - `drive_date`: date, required
  - `available_doses`: integer, required
  - `applicable_classes`: string

- **vaccination_record**
  - `id` (PK): integer, auto-increment
  - `student_id` (FK → student.id): integer
  - `drive_id` (FK → vaccination_drive.id): integer
  - `vaccination_date`: date, required

### Relationships

- **users ↔ roles:** Many-to-many, via `user_roles`
- **vaccination_record → student:** Many-to-one (each record references one student)
- **vaccination_record → vaccination_drive:** Many-to-one (each record references one drive)

---

**Note:**  
All tables use auto-incrementing integer IDs as primary keys. Foreign key constraints ensure data integrity between related records.


