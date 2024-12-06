package com.udacity.jdnd.course3.critter.converter;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Converter(autoApply = true)
public class EmployeeSkillConverter implements AttributeConverter<Set<EmployeeSkill>, String> {

    @Override
    public String convertToDatabaseColumn(Set<EmployeeSkill> skills) {
        if (skills == null || skills.isEmpty())
            return "";

        Set<String> asStrings = skills
            .stream()
            .map(EmployeeSkill::name)
            .collect(Collectors.toSet());

        return String.join(", ", asStrings);
    }

    @Override
    public Set<EmployeeSkill> convertToEntityAttribute(String skills) {
        if (skills == null || skills.isEmpty())
            return null;

        Set<String> asStrings = Arrays.stream(skills.split("\\s*,\\s*")).collect(Collectors.toSet());
        Set<EmployeeSkill> asEnum = new HashSet<>();
        for (String code : asStrings)
            asEnum.add(EmployeeSkill.valueOf(code.trim()));

        return asEnum;
    }

}
