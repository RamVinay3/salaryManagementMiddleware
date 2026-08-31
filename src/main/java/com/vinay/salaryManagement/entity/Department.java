package com.vinay.salaryManagement.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="department")

public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "department_seq")
    @SequenceGenerator(name = "department_seq",
                        allocationSize = 1,
                        sequenceName ="department_seq" )
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

}
