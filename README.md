# Application technical stack

The application runs on springboot backed by an in-memory H2 database. The db state is reset at 
each application reboot with 3 pre-filled feedback.

The client side code is located in *src/main/resources/static*. It does not rely on any framework or third party lib.

# How to run the server

Execute `./mvmw spring-boot:run` in a terminal. This builds the application and run the server.

# How to use the application

* Open your browser at : http://localhost:8080

# How to execute tests

Execute `./mvmw test`




