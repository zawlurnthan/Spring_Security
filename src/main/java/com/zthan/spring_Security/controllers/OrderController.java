package com.zthan.spring_Security.controllers;

import com.zthan.spring_Security.models.Customer;
import com.zthan.spring_Security.models.Order;
import com.zthan.spring_Security.repositories.CustomerRepository;
import com.zthan.spring_Security.repositories.OrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {

    private final OrderRepository orderRepo;
    private final CustomerRepository customerRepo;

    public OrderController(OrderRepository orderRepo, CustomerRepository customerRepo) {
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
    }

    @GetMapping("/orders")
    public String getAllOrders(Model model){
        Iterable<Order> orderIterable = this.orderRepo.findAll();
        List<OrderModel> orders = new ArrayList<>();
        orderIterable.forEach(oi ->{
            OrderModel order = new OrderModel();
            order.setOrderId(oi.getId());
            order.setCustomerId(oi.getCustomerId());
            Optional<Customer> oc = this.customerRepo.findById(oi.getCustomerId());
            order.setCustomer(oc.get().getName());
            order.setOrderDetails(oi.getOrderInfo());
            orders.add(order);
        });
        model.addAttribute("orders", orders);
        model.addAttribute("module", "orders");
        return "orders";
    }
}
