package com.zthan.spring_Security.repositories;

import com.zthan.spring_Security.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
