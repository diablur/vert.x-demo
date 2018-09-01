package com.boring;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class Application extends AbstractVerticle {
    @Override
    public void start(Future<Void> f) throws Exception {
        vertx.createHttpServer()
                .requestHandler(req -> {
                    req.response().end("Hello Vert.x!");
                })
                .listen(8080, result -> {
                    if (result.succeeded()) {
                        f.complete();
                    } else {
                        f.fail(result.cause());
                    }
                });
    }
}
