package com.udacity.jdnd.course3.critter.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "employee_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @ElementCollection(targetClass = EmployeeSkill.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
        name = "employees_skills",
        joinColumns = @JoinColumn(name = "employee_id")
    )
    @Column(name = "skill_name")
    private Set<EmployeeSkill> skills;

    @ElementCollection(targetClass = DayOfWeek.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
        name = "employees_availability",
        joinColumns = @JoinColumn(name = "employee_id")
    )
    @Column(name = "weekday")
    private Set<DayOfWeek> daysAvailable;

}
