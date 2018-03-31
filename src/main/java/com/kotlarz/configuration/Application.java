package com.kotlarz.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan( "com.kotlarz" )
public class Application
{
    public static void main( String[] args )
    {
        SpringApplication.run( Application.class );
    }
}
