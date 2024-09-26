# Kafka Streamer

This project is a Spring Boot application that utilizes Apache Kafka to read customer data from a Kafka topic and publish messages to two different topics based on the customer's age.

## Overview

- **Input Topic**: The application listens to an input Kafka topic that contains customer information formatted as `firstName,lastName,dateOfBirth`.
- **Output Topics**:
  - `CustomerEVEN`: Messages are published to this topic if the customer's age is an even number.
  - `CustomerODD`: Messages are published to this topic if the customer's age is an odd number.
  
## Directory Structure ##
```bash
   pom.xml             # Maven project build definition
   src/
     main/
       java/       # Java source code
       resources/  # Application resources
     test/
       java/       # Java test source code
       resources/  # Application test resources
```

## Prerequisites

Before you begin, ensure you have the following installed:

- Java
- Apache Kafka (with a running broker)
- Maven
- Lombok config

## Getting Started

### Clone the Repository

```bash
git clone <repository-url>
cd kafka-streamer

## Run Via Maven##
mvn clean install