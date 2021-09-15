package com.demo.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, String > cashierTerminalMap = new HashMap<>();
        cashierTerminalMap.put("101","token101");
        cashierTerminalMap.put("102","token102");
        cashierTerminalMap.put("103","token103");
        cashierTerminalMap.put("104","token104");
        System.out.println(cashierTerminalMap);
        log.info(">>>AuthInterceptor>>>>>>>call before enter into controller");

        String terminalId = request.getHeader("terminalId");
        String accessToken = request.getHeader("token");
        log.info("terminalId : [ {} ]", terminalId);
        log.info("accessToken : [ {} ]", accessToken);

        if (cashierTerminalMap.containsKey(terminalId)&&cashierTerminalMap.get(terminalId).equals(accessToken)){
            return true;
        }
        return false;
    }
}
