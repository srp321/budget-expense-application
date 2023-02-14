package com.mezzanine.mfw.assignment.web;

import com.mezzanine.mfw.assignment.model.ExpenseSummaryDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BudgetSummaryControllerTest {

    @Mock
    private ExpenseTransactionService mockExpenseTransactionService;

    @InjectMocks
    private BudgetSummaryController budgetSummaryControllerUnderTest;

    @Test
    void testGetBudgetExpenseSummary() {
        final ExpenseSummaryDTO expenseSummaryDTO = new ExpenseSummaryDTO();
        expenseSummaryDTO.setCategoryName("Travel");
        expenseSummaryDTO.setCategoryBudgetAmount(new BigDecimal("100.00"));
        expenseSummaryDTO.setUsedBudgetPercentage(0.0);
        final ExpenseTransactionDTO expenseTransactionDTO = new ExpenseTransactionDTO();
        expenseTransactionDTO.setName("Tickets");
        expenseTransactionDTO.setAmount(new BigDecimal("10.00"));
        expenseTransactionDTO.setCategoryName("Travel");
        expenseSummaryDTO.setExpenses(List.of(expenseTransactionDTO));
        final ResponseEntity<List<ExpenseSummaryDTO>> expectedResult = new ResponseEntity<>(List.of(expenseSummaryDTO),
                HttpStatus.OK);

        final ExpenseSummaryDTO expenseSummaryDTO1 = new ExpenseSummaryDTO();
        expenseSummaryDTO1.setCategoryName("Travel");
        expenseSummaryDTO1.setCategoryBudgetAmount(new BigDecimal("100.00"));
        expenseSummaryDTO1.setUsedBudgetPercentage(0.0);
        final ExpenseTransactionDTO expenseTransactionDTO1 = new ExpenseTransactionDTO();
        expenseTransactionDTO1.setName("Tickets");
        expenseTransactionDTO1.setAmount(new BigDecimal("10.00"));
        expenseTransactionDTO1.setCategoryName("Travel");
        expenseSummaryDTO1.setExpenses(List.of(expenseTransactionDTO1));
        final List<ExpenseSummaryDTO> expenseSummaryDTOS = List.of(expenseSummaryDTO1);
        when(mockExpenseTransactionService.getBudgetExpenseSummary()).thenReturn(expenseSummaryDTOS);

        final ResponseEntity<List<ExpenseSummaryDTO>> result = budgetSummaryControllerUnderTest.getBudgetExpenseSummary();

        assertEquals(expectedResult, result);
    }

    @Test
    void testGetBudgetExpenseSummary_ExpenseTransactionServiceReturnsNoItems() {
        when(mockExpenseTransactionService.getBudgetExpenseSummary()).thenReturn(Collections.emptyList());

        final ResponseEntity<List<ExpenseSummaryDTO>> result = budgetSummaryControllerUnderTest.getBudgetExpenseSummary();

        assertEquals(ResponseEntity.ok(Collections.emptyList()), result);
    }
}
