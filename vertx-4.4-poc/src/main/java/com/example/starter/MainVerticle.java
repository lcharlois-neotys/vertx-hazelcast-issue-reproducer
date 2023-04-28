package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.DeliveryOptions;

public class MainVerticle extends AbstractVerticle {

  private final int port;

  public MainVerticle(int port) {

    this.port = port;
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.eventBus().consumer("my-consumer", message -> {
      message.reply("I'm Vertx 4.4.1 listening on port " + port + "\n");
    });
    vertx.createHttpServer().requestHandler(req -> {
      vertx.eventBus().request("my-consumer", null, new DeliveryOptions().setSendTimeout(1000), res -> {
        if (res.failed()) {
          req.response()
            .putHeader("content-type", "text/plain")
            .end(res.cause().toString());
        } else {
          req.response()
            .putHeader("content-type", "text/plain")
            .end(res.result().body().toString());
        }
      });
    }).listen(port, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port" + port);
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
