# Task Manager Api

## Requirements
Docker

or

JDK 8, Maven, postgres

### Running on docker
You can run this project by running the command on the root of the project:
```shell
docker-compose up
```

### Running on Java
To run this project without docker, you need a pre-installed postgres database (the flyway migration files were created with postgres syntax).

Also, you'll need Maven. It contains a command called *run* that can be used to quickly compile and run the project with the command:

```shell
mvn spring-boot:run
```

## Environment variables
This project has predefined values to set the database connection. If you want to make any change on the predefined values, you just need to declare an environment variable.
* DB_DRIVER (default: org.postgresql.Driver)
* DB_URL (default: jdbc:postgresql://localhost:5432/taskmanagerapi)
* DB_USER (default: admin)
* DB_PASSWORD (default: admin)

By default, this project is set to show the sql, but you can change this by defining the environment variable:
* SHOW_SQL (default: true)