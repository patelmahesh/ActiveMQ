package com.panthera.configuration;



import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@ComponentScan(basePackages = "com.panthera")
@Import({ActiveMqConfiguration.class})
public class AppInitializer  {

	
}
