package com.mezzanine.mfw.assignment.repository;

import com.mezzanine.mfw.assignment.entity.BudgetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory, Long> {

    List<BudgetCategory> findByIdNotNull();

    BudgetCategory findByName(String name);

    @Query("SELECT DISTINCT name FROM BudgetCategory")
    List<String> getDistinctNames();
}

