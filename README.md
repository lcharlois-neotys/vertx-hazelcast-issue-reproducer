# Description

This poject contains 2 distinct maven project:

- vertx-4.3-poc
- vertx-4.4-poc

Both projects contains the same components.

- A verticle class
  - registering a consumer on address "my-consumer" that answer with the Vertx version (4.3.7 or 4.4.1) and the port used by the HTTP server.
  - starting an HTTP server on a specified port and listening for request, the request listener will send a vertx eventbus request to the address "my-consumer" and forward the response from the eventbus to the HTTP response.
- A main class that will start 3 instance of the Verticle class.

The main in project `vertx-4.3-poc` will start HTTP server on port 9000, 9001, 9002.
The main in project `vertx-4.4-poc` will start HTTP server on port 8000, 8001, 8002.

# Steps to reproduce

- Start the `Main_4_4` and `Main_4_3`
- Make a GET request on <http://localhost:8000>, you will receive a message like _I'm Vertx 4.4.1 listening on port 8001_
  - If you repeat the request 6 times you should get all of these messages:
    - _I'm Vertx 4.4.1 listening on port 8000_
    - _I'm Vertx 4.4.1 listening on port 8001_
    - _I'm Vertx 4.4.1 listening on port 8002_
    - _I'm Vertx 4.3.7 listening on port 9000_
    - _I'm Vertx 4.3.7 listening on port 9001_
    - _I'm Vertx 4.3.7 listening on port 9002_
- Kill the process `Main_4_3`
- Make a GET request on <http://localhost:8000> and repeat it up to 6 times.
- Here you will see that some of the request fail because Vertx is trying to send a message to a member that is dead.

```shell
(TIMEOUT,-1) Timed out after waiting 1000(ms) for a reply. address: __vertx.reply.2c876524-0b24-4108-af9f-5829231842ff, repliedAddress: my-consumer
```
