package com.mezzanine.mfw.assignment.service;

import com.mezzanine.mfw.assignment.entity.BudgetCategory;
import com.mezzanine.mfw.assignment.model.BudgetCategoryDTO;
import com.mezzanine.mfw.assignment.repository.BudgetCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BudgetCategoryService {

    private final BudgetCategoryRepository budgetCategoryRepository;

    public BudgetCategoryService(BudgetCategoryRepository budgetCategoryRepository) {
        this.budgetCategoryRepository = budgetCategoryRepository;
    }

    public List<BudgetCategoryDTO> getAllBudgetCategories() {
        log.info("get all budget categories");
        return budgetCategoryRepository.findByIdNotNull()
                .stream()
                .map(budgetCategory -> {
                    BudgetCategoryDTO budgetCategoryDTO = new BudgetCategoryDTO();
                    budgetCategoryDTO.setName(budgetCategory.getName());
                    budgetCategoryDTO.setAmount(budgetCategory.getAmount());
                    return budgetCategoryDTO;
                })
                .collect(Collectors.toList());
    }

    public void addBudgetCategories(List<BudgetCategoryDTO> budgetCategoryData) {
        log.info("in service to add {} budget categories with data", budgetCategoryData.size());

        List<BudgetCategory> budgetCategoryListToSave = new ArrayList<>();
        for (var budgetCategoryDTO : budgetCategoryData) {
            var budgetCategory = budgetCategoryRepository.findByName(budgetCategoryDTO.getName());
            if (Objects.isNull(budgetCategory)) {
                log.info("new budget category: {}", budgetCategoryDTO.getName());
                budgetCategory = new BudgetCategory();
                budgetCategory.setName(budgetCategoryDTO.getName());
                budgetCategory.setInsertDateTime(LocalDateTime.now());
            }
            budgetCategory.setAmount(budgetCategoryDTO.getAmount());
            budgetCategory.setUpdateDateTime(LocalDateTime.now());
            budgetCategoryListToSave.add(budgetCategory);
        }

        log.info("saving {} budget categories", budgetCategoryListToSave.size());
        budgetCategoryRepository.saveAll(budgetCategoryListToSave);
    }

    public List<String> getDistinctCategories() {
        log.info("get distinct categories");
        return budgetCategoryRepository.getDistinctNames();
    }
}
