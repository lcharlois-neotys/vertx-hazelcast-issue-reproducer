package com.example.starter;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

/**
 * @author lcharlois
 * @since 28/04/2023.
 */
public class Main_4_3 {

  public static void main(String[] args) {
    startVerticleOnNewVertx(9000);
    startVerticleOnNewVertx(9001);
    startVerticleOnNewVertx(9002);
  }

  private static void startVerticleOnNewVertx(int port) {
    final VertxOptions options = new VertxOptions().setClusterManager(new HazelcastClusterManager());
    Vertx.clusteredVertx(options)
      .flatMap(vertx ->
        vertx.deployVerticle(new MainVerticle(port))
      )
      .onComplete(result -> System.out.println("Deploy succcess for 4.3.7 " + result))
      .onFailure(Throwable::printStackTrace);
  }

}
