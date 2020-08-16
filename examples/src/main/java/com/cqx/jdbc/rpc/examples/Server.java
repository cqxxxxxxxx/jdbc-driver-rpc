package com.cqx.jdbc.rpc.examples;

import com.cqx.jdbc.rpc.examples.mybatis.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@SpringBootApplication
public class Server {

    @PostMapping("/sql-exec")
    public Response sql(@RequestBody Request request, @RequestHeader String authentication) {
        System.out.println(request.getDb() + request.getSql());
        System.out.println(authentication);
        Response response = new Response();
        response.setCode("000000");
        if (request.getSql().contains("where")) {
            response.setData(User.genOne());
        } else {
            final ArrayList<User> users = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                users.add(User.genOne());
            }
            response.setData(users);
        }
        return response;
    }


    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
}
