package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByIdIn(List<Long> ids);

    @Query(value = "SELECT DISTINCT e.*\n" +
            "FROM employees e\n" +
            "JOIN employees_skills es ON e.employee_id = es.employee_id\n" +
            "JOIN employees_availability ea ON e.employee_id = ea.employee_id\n" +
            "WHERE es.skill_name IN :skills \n" +
            "  AND ea.weekday = :dayOfWeek \n" +
            "  AND e.employee_id NOT IN (\n" +
            "    SELECT se.employee_id\n" +
            "    FROM schedules s\n" +
            "    JOIN schedules_employees se ON s.schedule_id = se.schedule_id\n" +
            "    WHERE s.delivery_date = :date \n" +
            "  )\n" +
            "GROUP BY e.employee_id\n" +
            "HAVING COUNT(DISTINCT es.skill_name) = :skillCount;", nativeQuery = true)
    List<Employee> getAllEmployeeByAvailability(List<String> skills, LocalDate date, String dayOfWeek, int skillCount);
}
