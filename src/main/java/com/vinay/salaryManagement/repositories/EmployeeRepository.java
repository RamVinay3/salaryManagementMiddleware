package com.vinay.salaryManagement.repositories;

import com.vinay.salaryManagement.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Optional<Employee> findByEmployeeCode(String employeeCode);

    Optional<Employee> findByEmail(String email);

    Page<Employee> findByDepartmentId(Long departmentId, Pageable pageable);

    @Query("""
            SELECT e
            FROM Employee e
            WHERE (:departmentId IS NULL OR e.department.id = :departmentId)
              AND (:country IS NULL OR e.country = :country)
              AND (
                    :search IS NULL
                    OR LOWER(e.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
                  )
            """)
    Page<Employee> searchEmployees(
            @Param("departmentId") Long departmentId,
            @Param("country") String country,
            @Param("search") String search,
            Pageable pageable
    );
}
