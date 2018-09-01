package com.boring;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class ApplicationTest {
    //vertx容器
    private Vertx vertx;

    @Before
    public void setUp(TestContext context) {
        //创建一个vertx容器的实例
        vertx = Vertx.vertx();
        //将Application这个verticle部署到容器中，并注册部署完成的处理器
        vertx.deployVerticle(Application.class.getName(),
                context.asyncAssertSuccess());
    }

    @After
    public void tearDown(TestContext context) {
        //关闭容器，注册关闭完成的处理器
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void testApplication(TestContext context) {
        final Async async = context.async();
        //创建一个http客户端，发送请求到http服务端
        vertx.createHttpClient().getNow(8080, "localhost", "/",
                response -> {
                    response.handler(body -> {
                        context.assertTrue(body.toString().contains("Hello"));
                        async.complete();
                    });
                });
    }
}
