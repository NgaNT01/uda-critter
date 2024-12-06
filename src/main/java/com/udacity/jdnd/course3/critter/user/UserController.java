package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        Customer savedCustomer = customerService.create(customer);

        List<Long> petIds = new ArrayList<>();
        if (savedCustomer.getPets() != null && !savedCustomer.getPets().isEmpty()) {
            petIds = savedCustomer.getPets().stream().map(Pet::getId).toList();
        }
        CustomerDTO savedCustomerDTO = modelMapper.map(savedCustomer, CustomerDTO.class);
        savedCustomerDTO.setPetIds(petIds);

        return savedCustomerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return customerService.list().stream().map(customer -> {
            CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
            List<Long> petIds = new ArrayList<>();
            if (customer.getPets() != null && !customer.getPets().isEmpty()) {
                petIds = customer.getPets().stream().map(Pet::getId).toList();
            }
            customerDTO.setPetIds(petIds);

            return customerDTO;
        }).toList();
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = customerService.findByPetId(petId);
        List<Long> petIds = customer.getPets().stream().map(Pet::getId).toList();

        CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
        customerDTO.setPetIds(petIds);

        return customerDTO;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employeeEntity = modelMapper.map(employeeDTO, Employee.class);

        Employee savedEntity = employeeService.create(employeeEntity);
        return modelMapper.map(savedEntity, EmployeeDTO.class);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee entity = employeeService.findById(employeeId);

        return modelMapper.map(entity, EmployeeDTO.class);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = employeeService.findById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeService.create(employee);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employeeEntities = employeeService.findByAvailability(employeeDTO.getSkills(), employeeDTO.getDate());

        List<EmployeeDTO> asList = new ArrayList<>();
        if (employeeEntities == null || employeeEntities.isEmpty())
            return asList;

        for (Employee entity : employeeEntities)
            asList.add(modelMapper.map(entity, EmployeeDTO.class));

        return asList;
    }

}
