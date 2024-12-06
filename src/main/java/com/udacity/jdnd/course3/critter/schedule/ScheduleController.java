package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule entity = modelMapper.map(scheduleDTO, Schedule.class);

        Schedule savedSchedule = scheduleService.create(entity, scheduleDTO.getPetIds(), scheduleDTO.getEmployeeIds());

        ScheduleDTO returnScheduleDTO = modelMapper.map(savedSchedule, ScheduleDTO.class);
        returnScheduleDTO.setPetIds(scheduleDTO.getPetIds());
        returnScheduleDTO.setEmployeeIds(scheduleDTO.getEmployeeIds());

        return returnScheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.list().stream().map(schedule -> {
            ScheduleDTO scheduleDTO = modelMapper.map(schedule, ScheduleDTO.class);
            List<Long> petIds = schedule.getPets().stream().map(Pet::getId).toList();
            List<Long> employeeIds = schedule.getEmployees().stream().map(Employee::getId).toList();
            scheduleDTO.setPetIds(petIds);
            scheduleDTO.setEmployeeIds(employeeIds);
            return scheduleDTO;
        }).toList();
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return scheduleService.findByPetId(petId).stream().map(schedule -> {
            ScheduleDTO scheduleDTO = modelMapper.map(schedule, ScheduleDTO.class);
            List<Long> petIds = schedule.getPets().stream().map(Pet::getId).toList();
            List<Long> employeeIds = schedule.getEmployees().stream().map(Employee::getId).toList();
            scheduleDTO.setPetIds(petIds);
            scheduleDTO.setEmployeeIds(employeeIds);
            return scheduleDTO;
        }).toList();
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.findByEmployee(employeeId).stream()
                .map(entity -> {
                    ScheduleDTO scheduleDTO = modelMapper.map(entity, ScheduleDTO.class);

                    List<Long> petIds = entity.getPets().stream()
                            .map(Pet::getId)
                            .collect(Collectors.toList());
                    List<Long> employeeIds = entity.getEmployees().stream()
                            .map(Employee::getId)
                            .collect(Collectors.toList());

                    scheduleDTO.setPetIds(petIds);
                    scheduleDTO.setEmployeeIds(employeeIds);

                    return scheduleDTO;
                })
                .toList();
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleService.findByCustomer(customerId).stream().map(schedule -> {
            ScheduleDTO scheduleDTO = modelMapper.map(schedule, ScheduleDTO.class);
            List<Long> petIds = schedule.getPets().stream().map(Pet::getId).toList();
            List<Long> employeeIds = schedule.getEmployees().stream().map(Employee::getId).toList();
            scheduleDTO.setPetIds(petIds);
            scheduleDTO.setEmployeeIds(employeeIds);
            return scheduleDTO;
        }).toList();
    }
}
