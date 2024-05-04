package com.example.springrestmvcdemo.mappers;

import com.example.springrestmvcdemo.entities.Customer;
import com.example.springrestmvcdemo.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDto(Customer customer);
}
