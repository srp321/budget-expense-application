package com.mezzanine.mfw.assignment.web;

import com.mezzanine.mfw.assignment.model.BudgetCategoryDTO;
import com.mezzanine.mfw.assignment.service.BudgetCategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BudgetCategoryControllerTest {

    @Mock
    private BudgetCategoryService mockBudgetCategoryService;

    @InjectMocks
    private BudgetCategoryController budgetCategoryControllerUnderTest;

    @Test
    void testGetAllBudgetCategories() {
        final BudgetCategoryDTO budgetCategoryDTO = new BudgetCategoryDTO();
        budgetCategoryDTO.setName("Travel");
        budgetCategoryDTO.setAmount(new BigDecimal("10.00"));
        final ResponseEntity<List<BudgetCategoryDTO>> expectedResult = new ResponseEntity<>(List.of(budgetCategoryDTO),
                HttpStatus.OK);

        final BudgetCategoryDTO budgetCategoryDTO1 = new BudgetCategoryDTO();
        budgetCategoryDTO1.setName("Travel");
        budgetCategoryDTO1.setAmount(new BigDecimal("10.00"));
        final List<BudgetCategoryDTO> budgetCategoryDTOS = List.of(budgetCategoryDTO1);
        when(mockBudgetCategoryService.getAllBudgetCategories()).thenReturn(budgetCategoryDTOS);

        final ResponseEntity<List<BudgetCategoryDTO>> result = budgetCategoryControllerUnderTest.getAllBudgetCategories();

        assertEquals(expectedResult, result);
    }

    @Test
    void testGetAllBudgetCategories_BudgetCategoryServiceReturnsNoItems() {
        when(mockBudgetCategoryService.getAllBudgetCategories()).thenReturn(Collections.emptyList());

        final ResponseEntity<List<BudgetCategoryDTO>> result = budgetCategoryControllerUnderTest.getAllBudgetCategories();

        assertEquals(ResponseEntity.ok(Collections.emptyList()), result);
    }

    @Test
    void testAddBudgetCategories() {
        final BudgetCategoryDTO budgetCategoryDTO = new BudgetCategoryDTO();
        budgetCategoryDTO.setName("Travel");
        budgetCategoryDTO.setAmount(new BigDecimal("10.00"));
        final List<BudgetCategoryDTO> budgetCategoryData = List.of(budgetCategoryDTO);
        final ResponseEntity<Map<String, Boolean>> expectedResult = new ResponseEntity<>(
                Map.ofEntries(Map.entry("success", true)), HttpStatus.OK);

        final ResponseEntity<Map<String, Boolean>> result = budgetCategoryControllerUnderTest.addBudgetCategories(
                budgetCategoryData);

        assertEquals(expectedResult, result);
        verify(mockBudgetCategoryService).addBudgetCategories(budgetCategoryData);
    }
}
