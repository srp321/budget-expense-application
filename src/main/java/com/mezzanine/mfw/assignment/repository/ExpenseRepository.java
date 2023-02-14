package com.mezzanine.mfw.assignment.repository;

import com.mezzanine.mfw.assignment.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByIdNotNull();

    List<Expense> findByCategoryName(String categoryName);
}
