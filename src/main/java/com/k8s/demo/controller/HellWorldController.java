package com.k8s.demo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@ResponseBody
@RequestMapping("/")
public class HellWorldController {

    @GetMapping
    public String hello(){

        InetAddress ip = null;
        String hostname;
        try {
            ip = InetAddress.getLocalHost();
            System.out.println("Your current IP address : " + ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


        return "hello kubernates~~,"+ ip.toString();
    }
}
