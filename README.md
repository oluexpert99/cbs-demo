# CBS Demo App

## Overview

CBS Demo App is a Spring Boot application that showcases [brief description of your app's main purpose or functionality].

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Build and Run](#build-and-run)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

## Features

- [List of key features or functionalities your app provides]

## Prerequisites

Ensure you have the following tools installed before proceeding:

- [Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html) (version 17 or higher)
- [Maven](https://maven.apache.org/download.cgi) (version 3 )
- [Docker](https://www.docker.com/get-started) (if applicable)

## Getting Started

Follow these steps to get the CBS Demo App up and running on your local machine.

1. [Clone the repository](#clone-the-repository)
2. [Configure application properties](#configuration)
3. [Build and run the application](#build-and-run)

### Clone the Repository

```bash
git clone https://github.com/your-username/cbs-demo-app.git
cd cbs-demo-app
```

### Configuration
By default , The app runs on H2, You can change the database to Postgres by changing the application.properties file.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/cbs_demo_app
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
```

## Build and Run
### Using Maven

```bash
mvn clean install
java -jar target/cbs-demo-app-0.0.1-SNAPSHOT.jar
```
#### Using Docker
```bash
docker build -t cbs-demo-app .
docker run -p 8080:8080 cbs-demo-app
```

## API Documentation
Swagger UI is available at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Testing
To run the tests, execute the following command:

```bash
mvn test
```

## Contributing
Contributions are welcome! Please feel free to submit a Pull Request.

## License
[MIT](https://opensource.org/licenses/MIT)
```
