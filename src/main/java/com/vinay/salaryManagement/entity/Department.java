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
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="DEPARTMENT_SEQ")
    @SequenceGenerator(name = "DEPARTMENT_SEQ",
                        allocationSize = 1,
                        sequenceName ="DEPARTMENT_SEQ" )
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;
    private String description;

}
