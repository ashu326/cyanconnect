package com.cyanconnode.connect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CyanconnectApplication
{

    public static void main(String[] args)
    {
        try
        {
            SpringApplication.run(CyanconnectApplication.class, args);
        }
        catch (Exception e)
        {
            System.err.println("Application failed to start");
            e.printStackTrace();
        }
    }
}
