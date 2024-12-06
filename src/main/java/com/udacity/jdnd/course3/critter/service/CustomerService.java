package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> list() {
        return customerRepository.findAll();
    }

    public Customer create(Customer entity) {
        return customerRepository.save(entity);
    }

    public Customer findByPetId(long id) {
        return customerRepository.findByPetsId(id);
    }

}
