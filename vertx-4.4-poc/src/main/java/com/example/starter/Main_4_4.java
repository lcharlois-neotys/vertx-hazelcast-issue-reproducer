package com.example.starter;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

/**
 * @author lcharlois
 * @since 28/04/2023.
 */
public class Main_4_4 {

  public static void main(String[] args) {
    startVerticleOnNewVertx(8000);
    startVerticleOnNewVertx(8001);
    startVerticleOnNewVertx(8002);
  }

  private static void startVerticleOnNewVertx(int port) {
    final VertxOptions options = new VertxOptions().setClusterManager(new HazelcastClusterManager());
    Vertx.clusteredVertx(options)
      .flatMap(vertx ->
        vertx.deployVerticle(new MainVerticle(port))
      )
      .onComplete(result -> System.out.println("Deploy succcess for 4.4.1 " + result))
      .onFailure(Throwable::printStackTrace);
  }

}
