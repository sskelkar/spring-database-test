# Writing integration tests for a spring hibernate app
Blogged at: https://betweencurlybraces.wordpress.com/2019/01/12/database-integration-tests-for-a-legacy-spring-hibernate-application/

Master branch contains the application code without tests.

### 1. Integration tests using MySQL
`git checkout mysql`
Prerequisite: Docker version 1.6.0 or above.
Command: `gradle clean test`

### 2. Integration tests using H2
`git checkout h2`
Command: `gradle clean test`

### 3. Toggle between H2 and MySQL 
`git checkout both`
Commands: 
`gradle clean test` for H2
`gradle clean test -DtestDB=mysql` for MySQL