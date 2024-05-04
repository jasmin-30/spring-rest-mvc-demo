package com.example.springrestmvcdemo.repositories;

import com.example.springrestmvcdemo.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
