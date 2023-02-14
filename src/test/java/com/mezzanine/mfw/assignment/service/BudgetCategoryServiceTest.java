package com.mezzanine.mfw.assignment.service;

import com.mezzanine.mfw.assignment.entity.BudgetCategory;
import com.mezzanine.mfw.assignment.model.BudgetCategoryDTO;
import com.mezzanine.mfw.assignment.repository.BudgetCategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BudgetCategoryServiceTest {

    @Mock
    private BudgetCategoryRepository budgetCategoryRepository;

    @InjectMocks
    private BudgetCategoryService budgetCategoryService;

    @Test
    public void getAllBudgetCategories_returnsBudgetCategoryDTOs() {
        BudgetCategory budgetCategory1 = new BudgetCategory();
        budgetCategory1.setName("Travel");
        budgetCategory1.setAmount(BigDecimal.valueOf(100));

        BudgetCategory budgetCategory2 = new BudgetCategory();
        budgetCategory2.setName("Study");
        budgetCategory2.setAmount(BigDecimal.valueOf(200));

        List<BudgetCategory> budgetCategories = Arrays.asList(budgetCategory1, budgetCategory2);
        when(budgetCategoryRepository.findByIdNotNull()).thenReturn(budgetCategories);

        List<BudgetCategoryDTO> budgetCategoryDTOS = budgetCategoryService.getAllBudgetCategories();

        assertEquals(2, budgetCategoryDTOS.size());
        assertEquals("Travel", budgetCategoryDTOS.get(0).getName());
        assertEquals("Study", budgetCategoryDTOS.get(1).getName());
    }


    @Test
    void addBudgetCategories_withNewData_createsNewBudgetCategory() {
        List<BudgetCategoryDTO> budgetCategoryData = new ArrayList<>();
        BudgetCategoryDTO budgetCategoryDTO = new BudgetCategoryDTO();
        budgetCategoryDTO.setName("Travel");
        budgetCategoryDTO.setAmount(BigDecimal.valueOf(1000.0));
        budgetCategoryData.add(budgetCategoryDTO);

        when(budgetCategoryRepository.findByName("Travel")).thenReturn(null);
        when(budgetCategoryRepository.saveAll(any())).thenReturn(null);

        budgetCategoryService.addBudgetCategories(budgetCategoryData);

        verify(budgetCategoryRepository).findByName("Travel");
        verify(budgetCategoryRepository).saveAll(any());
    }

    @Test
    void addBudgetCategories_withExistingData_updatesExistingBudgetCategory() {
        List<BudgetCategoryDTO> budgetCategoryData = new ArrayList<>();
        BudgetCategoryDTO budgetCategoryDTO = new BudgetCategoryDTO();
        budgetCategoryDTO.setName("Travel");
        budgetCategoryDTO.setAmount(BigDecimal.valueOf(1000.0));
        budgetCategoryData.add(budgetCategoryDTO);

        BudgetCategory budgetCategory = new BudgetCategory();
        budgetCategory.setName("Travel");
        budgetCategory.setAmount(BigDecimal.valueOf(100.0));
        budgetCategory.setInsertDateTime(LocalDateTime.now());
        budgetCategory.setUpdateDateTime(LocalDateTime.now());

        when(budgetCategoryRepository.findByName("Travel")).thenReturn(budgetCategory);
        when(budgetCategoryRepository.saveAll(any())).thenReturn(null);

        budgetCategoryService.addBudgetCategories(budgetCategoryData);

        verify(budgetCategoryRepository).findByName("Travel");
        verify(budgetCategoryRepository).saveAll(any());
        assertEquals(BigDecimal.valueOf(1000.0), budgetCategory.getAmount());
    }

    @Test
    public void testGetDistinctCategories() {
        List<String> expectedDistinctCategories = Arrays.asList("Travel", "Party", "Study");
        when(budgetCategoryRepository.getDistinctNames()).thenReturn(expectedDistinctCategories);

        List<String> distinctCategories = budgetCategoryService.getDistinctCategories();
        assertEquals(expectedDistinctCategories, distinctCategories);
    }
}
