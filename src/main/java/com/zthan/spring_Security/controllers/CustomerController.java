package com.zthan.spring_Security.controllers;

import com.zthan.spring_Security.models.Customer;
import com.zthan.spring_Security.models.Order;
import com.zthan.spring_Security.repositories.CustomerRepository;
import com.zthan.spring_Security.repositories.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository customerRepo;
    private final OrderRepository orderRepo;

    public CustomerController(CustomerRepository customerRepo, OrderRepository orderRepo) {
        this.customerRepo = customerRepo;
        this.orderRepo = orderRepo;
    }

    @GetMapping
    public String getAllUsers(Model model){
        Iterable<Customer> customersIterable = this.customerRepo.findAll();
        List<Customer> customers = new ArrayList<>();
        customersIterable.forEach(customers::add);
        customers.sort(Comparator.comparing(Customer::getName));
        model.addAttribute("customers", customers);
        model.addAttribute("module", "customers");
        return "customers";
    }

    @GetMapping(path = "/{id}")
    public String getUser(@PathVariable("id") long customerId, Principal principal, Model model) {
        Optional<Customer> customer = this.customerRepo.findById(customerId);

        if (customer.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        model.addAttribute("customer", customer.get());

        List<Order> orders = new ArrayList<>();
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            AtomicBoolean auth = new AtomicBoolean(false);
            Collection<GrantedAuthority> authorities = ((UsernamePasswordAuthenticationToken) principal).getAuthorities();
            authorities.forEach(authority -> {
                        if (authority.getAuthority().equals("ROLE_ADMIN")) {
                            auth.set(true);
                        }
                    }
            );
            if (auth.get()) {
                Iterable<Order> ordersIterable = this.orderRepo.findAllByCustomerId(customer.get().getId());
                ordersIterable.forEach(orders::add);
            }
        }
        model.addAttribute("orders", orders);
        model.addAttribute("module", "customers");
        return "detailed_customer";
    }
}
