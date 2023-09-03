package com.demo.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Component
public class WebSocketConfiguration {

    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
