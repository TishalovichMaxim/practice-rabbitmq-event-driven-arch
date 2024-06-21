package by.tishalovichm.order.controller;

import by.tishalovichm.order.model.Order;
import by.tishalovichm.order.producer.OrderProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderProducer orderProducer;

    @PostMapping
    public Order create(@RequestBody Order order) {
        orderProducer.produce(order);
        return order;
    }

}
