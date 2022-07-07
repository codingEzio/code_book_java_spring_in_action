package com.springinaction.tacocloud.web;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.slf4j.Slf4j;

import com.springinaction.tacocloud.TacoOrder;
import com.springinaction.tacocloud.data.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    private OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order,
            Errors errors,
            SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            log.error("Error occurred when submitting orders.");

            return "orderForm";
        }

        orderRepository.save(order);
        sessionStatus.setComplete();

        return "redirect:/";
    }
}