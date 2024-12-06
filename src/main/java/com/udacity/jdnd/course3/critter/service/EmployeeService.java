package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee create(Employee entity) {
        return employeeRepository.save(entity);
    }

    public Employee findById(long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public List<Employee> findByAvailability(Set<EmployeeSkill> skills, LocalDate date) {
        return employeeRepository.getAllEmployeeByAvailability(toStringSkills(skills), date, getWeekday(date), skills.size());
    }

    private String getWeekday(LocalDate date) {
        DayOfWeek weekday = date.getDayOfWeek();
        return weekday.name();
    }

    private List<String> toStringSkills(Set<EmployeeSkill> skills) {
        if (skills == null || skills.isEmpty())
            return Collections.singletonList("");

        return skills.stream()
                .map(EmployeeSkill::name)
                .collect(Collectors.toList());
    }

}
