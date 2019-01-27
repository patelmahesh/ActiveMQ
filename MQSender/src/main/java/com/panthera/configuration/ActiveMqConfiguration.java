/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panthera.configuration;

import com.panthera.messaging.MessageReceiver;
import java.util.Arrays;
import javax.jms.ConnectionFactory;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.support.converter.SimpleMessageConverter;

/**
 *
 * @author Mahesh
 */
@Configuration
public class ActiveMqConfiguration {

    private static final String DEFAULT_BROKER_URL = "tcp://localhost:61616";

    private static final String QUEUE = "queue";
    private static final String RESPONSE_QUEUE = "response-queue";

    @Autowired
    MessageReceiver messageReceiver;

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(DEFAULT_BROKER_URL);
        connectionFactory.setTrustedPackages(Arrays.asList("com.panthera"));
        return connectionFactory;
    }

    @Bean
    public ConnectionFactory cachingConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setTargetConnectionFactory(connectionFactory());
        connectionFactory.setSessionCacheSize(10);
        return connectionFactory;
    }

    /*
	 * Message listener container, used for invoking messageReceiver.onMessage on message reception.
     */
    @Bean
    public MessageListenerContainer getContainer() {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setDestinationName(RESPONSE_QUEUE);
        container.setMessageListener(messageReceiver);
        return container;
    }

    /*
	 * Used here for Sending Messages.
     */
    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setDefaultDestinationName(QUEUE);
        return template;
    }

    @Bean
    public SimpleMessageConverter converter() {
        return new SimpleMessageConverter();
    }

}
