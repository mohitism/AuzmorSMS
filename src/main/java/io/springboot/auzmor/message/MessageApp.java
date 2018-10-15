package io.springboot.auzmor.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = {"io.springboot.auzmor"})
public class MessageApp 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(MessageApp.class, args);
    }
}
