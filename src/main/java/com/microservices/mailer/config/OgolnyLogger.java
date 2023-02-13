package com.microservices.mailer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Service
public class OgolnyLogger {

    private Logger log = Logger.getLogger("OgolnyLog");

    FileHandler hf;


    public Logger log(){
        return log;
    }
    @PostConstruct
    private void Init() {
        try{
            hf = new FileHandler("D:/Ogolny.txt");
            log.addHandler(hf);
            SimpleFormatter formatter = new SimpleFormatter();
            hf.setFormatter(formatter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
