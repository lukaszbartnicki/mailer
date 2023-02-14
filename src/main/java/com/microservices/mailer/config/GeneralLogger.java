package com.microservices.mailer.config;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Service
public class GeneralLogger {

    private final Logger log = Logger.getLogger("GeneralLog");

    FileHandler fh;


    public Logger log(){
        return log;
    }
    @PostConstruct
    private void Init() {
        try{
            fh = new FileHandler("D:/General.txt");
            log.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
