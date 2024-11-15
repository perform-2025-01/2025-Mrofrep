package com.dtcookie.shop.backend;

import java.util.Collections;
import java.util.concurrent.Callable;

import com.dtcookie.util.Http;
import com.dtcookie.shop.Ports;

public class Purchase {

    private final String id;

    public Purchase(String id) {
        this.id = id;
    }

    public static Callable<String> confirm(String id) {
        return new Purchase(id).confirm();
    }
    
    public Callable<String> confirm() {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                return Http.Jodd.GET("http://frontend:" + Ports.FRONTEND_LISTEN_PORT + "/purchase-confirmed", Collections.singletonMap("product.id",  id));
            }
        };        
    }
}
