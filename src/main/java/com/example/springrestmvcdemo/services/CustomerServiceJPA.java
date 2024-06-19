package com.example.springrestmvcdemo.services;

import com.example.springrestmvcdemo.entities.Customer;
import com.example.springrestmvcdemo.mappers.CustomerMapper;
import com.example.springrestmvcdemo.model.CustomerDTO;
import com.example.springrestmvcdemo.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> listCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDto)
                .toList();
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.ofNullable(
                customerMapper.customerToCustomerDto(
                        customerRepository.findById(id).orElse(null)
                )
        );
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customerDTO) {
        Customer savedCustomer = customerRepository.save(customerMapper.customerDtoToCustomer(customerDTO));
        return customerMapper.customerToCustomerDto(savedCustomer);
    }

    @Override
    public void updateCustomerById(UUID customerId, CustomerDTO customer) {

    }

    @Override
    public void deleteCustomerById(UUID customerId) {

    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customer) {

    }
}
