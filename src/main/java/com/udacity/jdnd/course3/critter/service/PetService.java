package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import jakarta.transaction.Transactional;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    private CustomerService customers;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public List<Pet> list() {
        return petRepository.findAll();
    }

    public Pet create(Pet entity) {
        Pet pet = petRepository.save(entity);
        Customer customer = customerRepository.findById(pet.getOwner().getId()).orElse(null);
        if (customer != null){
            List<Pet> petOfCustomer = customer.getPets();
            if (petOfCustomer != null){
                petOfCustomer.add(pet);
                customer.setPets(petOfCustomer);
            } else {
                customer.setPets(new ArrayList<>() {{add(pet);}});
            }
            customers.create(customer);
        }

        return pet;
    }

    public Pet findByPetId(long id) {
        return petRepository.findById(id).orElse(null);
    }

    public List<Pet> findByOwnerId(long id) {
        return petRepository.findByOwnerId(id);
    }

}
