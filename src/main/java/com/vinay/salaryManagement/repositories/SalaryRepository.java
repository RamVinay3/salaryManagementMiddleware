package com.vinay.salaryManagement.repositories;

import com.vinay.salaryManagement.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary,Long> {
    Optional<Salary> findTopByEmployeeIdOrderByEffectiveDateDesc(
            Long employeeId
    );
   Optional< List<Salary> >findByEmployeeId(Long employeeId);

    @Query("""
        SELECT s.currency, AVG(s.amount)
        FROM Salary s
        GROUP BY s.currency
        ORDER BY s.currency
        """)
    List<Object[]> findAverageSalaryByCurrency();

    @Query("""
        SELECT s.currency, AVG(s.amount)
        FROM Salary s
        WHERE s.effectiveDate = (
            SELECT MAX(s2.effectiveDate)
            FROM Salary s2
            WHERE s2.employee.id = s.employee.id
        )
        GROUP BY s.currency
        ORDER BY s.currency
        """)
    List<Object[]> findAverageCurrentSalaryByCurrency();

}
