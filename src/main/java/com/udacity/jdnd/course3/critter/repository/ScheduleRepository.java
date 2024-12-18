package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Schedule findById(long id);
    List<Schedule> findByPetsId(long petId);
    List<Schedule> findByEmployeesId(Long employeeId);
    List<Schedule> findByPetsOwnerId(Long ownerId);
}
