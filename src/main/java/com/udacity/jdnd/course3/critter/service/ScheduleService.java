package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.user.Employee;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Schedule> list() {
        return scheduleRepository.findAll();
    }

    public Schedule create(Schedule entity, List<Long> petsIds, List<Long> employeesIds) {
        List<Pet> petList = petRepository.findByIdIn(petsIds);
        List<Employee> employeeList = employeeRepository.findByIdIn(employeesIds);

        entity.setPets(petList);
        entity.setEmployees(employeeList);

        return scheduleRepository.save(entity);
    }

    public List<Schedule> findByPetId(long id) {
        return scheduleRepository.findByPetsId(id);
    }

    public List<Schedule> findByEmployee(long id) {
        return scheduleRepository.findByEmployeesId(id);
    }

    public List<Schedule> findByCustomer(long id) {
        return scheduleRepository.findByPetsOwnerId(id);
    }

}
