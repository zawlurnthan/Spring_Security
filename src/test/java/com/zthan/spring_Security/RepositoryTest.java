package com.zthan.spring_Security;

import com.zthan.spring_Security.models.Customer;
import com.zthan.spring_Security.models.Order;
import com.zthan.spring_Security.repositories.CustomerRepository;
import com.zthan.spring_Security.repositories.OrderRepository;
import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Optional;

@SpringBootTest
public class RepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;


    @Test
    public void testGetAllOrders(){
        Iterable<Order> orders = orderRepository.findAll();
        assert(IterableUtils.size(orders)==3);
    }

    @Test
    public void testGetAllCustomers(){
        Iterable<Customer> customers = customerRepository.findAll();
        assert(IterableUtils.size(customers)==7);
    }


    @Test
    public void testGetUser() {
        long id = 7L;
        Optional<Customer> customer = customerRepository.findById(id);
//        Customer customer = customerRepository.getById(id);
        assert(IterableUtils.size(Collections.singleton(customer)) == 1);
    }
}
