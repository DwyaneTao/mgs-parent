package com.mgs.dis.server;

import com.mgs.common.Response;
import com.mgs.dis.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestServer {

    @Autowired
    private PublisherService publisherService;

    @RequestMapping("/test/simulate")
    public void simulateClient(){
        publisherService.sendMessage("increment", "helloEveryBody!");
    }
}
