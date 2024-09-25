package com.xxx.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    public int getAge() {
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }
}
