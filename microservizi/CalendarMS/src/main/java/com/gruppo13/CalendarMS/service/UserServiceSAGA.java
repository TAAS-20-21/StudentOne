package com.gruppo13.CalendarMS.service;

import com.gruppo13.CalendarMS.event.UserCreatedEvent;
import com.gruppo13.CalendarMS.models.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Log4j2
@AllArgsConstructor
@Service
public class UserServiceSAGA {


    private final ApplicationEventPublisher publisher;

    @Transactional
    public User createUser(User user) {

        System.out.println("Saving a user {} " + user.getEmail());

        publish(user);

        return user;

    }

    private void publish(User user) {

        UserCreatedEvent event = new UserCreatedEvent(UUID.randomUUID().toString(), user);

        System.out.println("Publishing an order created event {} " + event.getTransactionId() + ", " + event.getUser().getEmail());

        publisher.publishEvent(event);

    }

    /*
    @Transactional
    public void updateOrderAsDone(Long orderId) {

        log.debug("Updating Order {} to {}", orderId, OrderStatus.DONE);

        Optional<Order> orderOptional = repository.findById(orderId);

        if (orderOptional.isPresent()) {

            Order order = orderOptional.get();
            order.setStatus(OrderStatus.DONE);
            repository.save(order);

            log.debug("Order {} done", order.getId());

        } else {

            log.error("Cannot update Order to status {}, Order {} not found", OrderStatus.DONE, orderId);

        }
    }



    @Transactional
    public void cancelUser(Long orderId) {

        log.debug("Canceling Order {}", orderId);

        Optional<User> optionalOrder = repository.findById(orderId);

        if (optionalOrder.isPresent()) {

            Order order = optionalOrder.get();
            order.setStatus(OrderStatus.CANCELED);
            repository.save(order);

            log.debug("Order {} was canceled", order.getId());

        } else {

            log.error("Cannot find an Order {}", orderId);

        }
    }*/
}