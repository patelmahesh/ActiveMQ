package com.panthera.messaging;

import com.panthera.model.Product;
import com.panthera.service.OrderService;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver implements MessageListener {

    static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);

    @Autowired
    MessageConverter messageConverter;

    @Autowired
    OrderService orderService;

    @Override
    public void onMessage(Message message) {
        try {

            Product product = (Product) messageConverter.fromMessage(message);
            LOG.info("Application : order received : {}", product);

            orderService.processOrder(product);
            LOG.info("-----------------------------------------------------");
        } catch (JMSException e) {
            // TODO Auto-generated catch block

        }

    }
}
