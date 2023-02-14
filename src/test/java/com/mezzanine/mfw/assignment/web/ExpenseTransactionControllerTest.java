package com.mezzanine.mfw.assignment.web;

import com.mezzanine.mfw.assignment.model.ExpenseTransactionDTO;
import com.mezzanine.mfw.assignment.service.ExpenseTransactionService;
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
class ExpenseTransactionControllerTest {

    @Mock
    private ExpenseTransactionService mockExpenseTransactionService;

    @InjectMocks
    private ExpenseTransactionController expenseTransactionControllerUnderTest;

    @Test
    void testGetAllBudgetCategories() {
        final ExpenseTransactionDTO expenseTransactionDTO = new ExpenseTransactionDTO();
        expenseTransactionDTO.setName("Tickets");
        expenseTransactionDTO.setAmount(new BigDecimal("10.00"));
        expenseTransactionDTO.setCategoryName("Travel");
        final ResponseEntity<List<ExpenseTransactionDTO>> expectedResult = new ResponseEntity<>(
                List.of(expenseTransactionDTO), HttpStatus.OK);

        final ExpenseTransactionDTO expenseTransactionDTO1 = new ExpenseTransactionDTO();
        expenseTransactionDTO1.setName("Tickets");
        expenseTransactionDTO1.setAmount(new BigDecimal("10.00"));
        expenseTransactionDTO1.setCategoryName("Travel");
        final List<ExpenseTransactionDTO> expenseTransactionDTOS = List.of(expenseTransactionDTO1);
        when(mockExpenseTransactionService.getAllExpenses()).thenReturn(expenseTransactionDTOS);

        final ResponseEntity<List<ExpenseTransactionDTO>> result = expenseTransactionControllerUnderTest.getAllBudgetCategories();

        assertEquals(expectedResult, result);
    }

    @Test
    void testGetAllBudgetCategories_ExpenseTransactionServiceReturnsNoItems() {
        when(mockExpenseTransactionService.getAllExpenses()).thenReturn(Collections.emptyList());

        final ResponseEntity<List<ExpenseTransactionDTO>> result = expenseTransactionControllerUnderTest.getAllBudgetCategories();

        assertEquals(ResponseEntity.ok(Collections.emptyList()), result);
    }

    @Test
    void testAddExpenseTransactions() {
        final ExpenseTransactionDTO expenseTransactionDTO = new ExpenseTransactionDTO();
        expenseTransactionDTO.setName("Tickets");
        expenseTransactionDTO.setAmount(new BigDecimal("10.00"));
        expenseTransactionDTO.setCategoryName("Travel");
        final List<ExpenseTransactionDTO> expenseTransactionData = List.of(expenseTransactionDTO);
        final ResponseEntity<Map<String, Boolean>> expectedResult = new ResponseEntity<>(
                Map.ofEntries(Map.entry("success", true)), HttpStatus.OK);

        final ResponseEntity<Map<String, Boolean>> result = expenseTransactionControllerUnderTest.addExpenseTransactions(
                expenseTransactionData);

        assertEquals(expectedResult, result);
        verify(mockExpenseTransactionService).addExpenseTransactions(expenseTransactionData);
    }
}
