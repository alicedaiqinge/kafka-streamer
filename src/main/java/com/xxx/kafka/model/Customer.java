package com.xxx.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Setter
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
