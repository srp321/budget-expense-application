package com.mezzanine.mfw.assignment.service;

import com.mezzanine.mfw.assignment.entity.Expense;
import com.mezzanine.mfw.assignment.model.BudgetCategoryDTO;
import com.mezzanine.mfw.assignment.model.ExpenseSummaryDTO;
import com.mezzanine.mfw.assignment.model.ExpenseTransactionDTO;
import com.mezzanine.mfw.assignment.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ExpenseTransactionServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private BudgetCategoryService budgetCategoryService;

    @InjectMocks
    private ExpenseTransactionService expenseTransactionService;

    @Test
    void testGetAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        Expense expense1 = new Expense();
        expense1.setId(1L);
        expense1.setName("Expense 1");
        expense1.setAmount(new BigDecimal("50.00"));
        expense1.setCategoryName("Travel");
        expense1.setInsertDateTime(LocalDateTime.now().minusDays(1));
        expenses.add(expense1);

        Expense expense2 = new Expense();
        expense2.setId(2L);
        expense2.setName("Expense 2");
        expense2.setAmount(new BigDecimal("25.00"));
        expense2.setCategoryName("Study");
        expense2.setInsertDateTime(LocalDateTime.now().minusDays(2));
        expenses.add(expense2);

        when(expenseRepository.findByIdNotNull()).thenReturn(expenses);

        List<ExpenseTransactionDTO> expected = new ArrayList<>();
        ExpenseTransactionDTO expenseTransactionDTO1 = new ExpenseTransactionDTO();
        expenseTransactionDTO1.setName("Expense 1");
        expenseTransactionDTO1.setAmount(new BigDecimal("50.00"));
        expenseTransactionDTO1.setCategoryName("Travel");
        expected.add(expenseTransactionDTO1);

        ExpenseTransactionDTO expenseTransactionDTO2 = new ExpenseTransactionDTO();
        expenseTransactionDTO2.setName("Expense 2");
        expenseTransactionDTO2.setAmount(new BigDecimal("25.00"));
        expenseTransactionDTO2.setCategoryName("Study");
        expected.add(expenseTransactionDTO2);

        List<ExpenseTransactionDTO> actual = expenseTransactionService.getAllExpenses();

        assertEquals(expected, actual);
    }

    @Test
    void testAddExpenseTransactionsWithValidData() {
        ExpenseTransactionDTO expenseTransactionDTO = new ExpenseTransactionDTO();
        expenseTransactionDTO.setName("Test expense");
        expenseTransactionDTO.setAmount(BigDecimal.valueOf(100));
        expenseTransactionDTO.setCategoryName("Test category");

        List<ExpenseTransactionDTO> expenseTransactionData = Collections.singletonList(expenseTransactionDTO);

        when(budgetCategoryService.getDistinctCategories()).thenReturn(Collections.singletonList("Test category"));

        expenseTransactionService.addExpenseTransactions(expenseTransactionData);

        verify(expenseRepository).saveAll(any());
    }

    @Test
    void testAddExpenseTransactionsWithInvalidData() {
        ExpenseTransactionDTO expenseTransactionDTO = new ExpenseTransactionDTO();
        expenseTransactionDTO.setName("Test expense");
        expenseTransactionDTO.setAmount(BigDecimal.valueOf(100));
        expenseTransactionDTO.setCategoryName("Invalid category");

        List<ExpenseTransactionDTO> expenseTransactionData = Collections.singletonList(expenseTransactionDTO);

        when(budgetCategoryService.getDistinctCategories()).thenReturn(Collections.singletonList("Test category"));

        expenseTransactionService.addExpenseTransactions(expenseTransactionData);

        verify(expenseRepository).saveAll(Collections.emptyList());
    }

    @Test
    void testAddExpenseTransactionsWithMultipleValidAndInvalidData() {
        ExpenseTransactionDTO validExpenseTransactionDTO = new ExpenseTransactionDTO();
        validExpenseTransactionDTO.setName("Test expense");
        validExpenseTransactionDTO.setAmount(BigDecimal.valueOf(100));
        validExpenseTransactionDTO.setCategoryName("Test category");

        ExpenseTransactionDTO invalidExpenseTransactionDTO = new ExpenseTransactionDTO();
        invalidExpenseTransactionDTO.setName("Test expense");
        invalidExpenseTransactionDTO.setAmount(BigDecimal.valueOf(100));
        invalidExpenseTransactionDTO.setCategoryName("Invalid category");

        List<ExpenseTransactionDTO> expenseTransactionData = Arrays.asList(validExpenseTransactionDTO, invalidExpenseTransactionDTO);

        when(budgetCategoryService.getDistinctCategories()).thenReturn(Collections.singletonList("Test category"));

        expenseTransactionService.addExpenseTransactions(expenseTransactionData);

        //verify(expenseRepository).saveAll(Collections.singletonList(createExpense(validExpenseTransactionDTO)));
    }

    @Test
    public void testGetBudgetExpenseSummaryWithEmptyExpenses() {
        BudgetCategoryDTO category1 = new BudgetCategoryDTO();
        category1.setName("Travel");
        category1.setAmount(new BigDecimal("1000.00"));
        when(budgetCategoryService.getAllBudgetCategories()).thenReturn(List.of(category1));
        when(expenseRepository.findByCategoryName(category1.getName())).thenReturn(new ArrayList<>());

        List<ExpenseSummaryDTO> expenseSummaryList = expenseTransactionService.getBudgetExpenseSummary();

        assertTrue(expenseSummaryList.isEmpty());
    }

    @Test
    public void testGetBudgetExpenseSummaryWithExpenses() {
        BudgetCategoryDTO category1 = new BudgetCategoryDTO();
        category1.setName("Travel");
        category1.setAmount(new BigDecimal("1000.00"));
        Expense expense1 = new Expense();
        expense1.setCategoryName(category1.getName());
        expense1.setName("Study");
        expense1.setAmount(new BigDecimal("50.00"));
        expense1.setInsertDateTime(LocalDateTime.now().minusDays(2));
        Expense expense2 = new Expense();
        expense2.setCategoryName(category1.getName());
        expense2.setName("Groceries");
        expense2.setAmount(new BigDecimal("200.00"));
        expense2.setInsertDateTime(LocalDateTime.now().minusDays(1));
        when(budgetCategoryService.getAllBudgetCategories()).thenReturn(List.of(category1));
        when(expenseRepository.findByCategoryName(category1.getName())).thenReturn(Arrays.asList(expense1, expense2));

        List<ExpenseSummaryDTO> expenseSummaryList = expenseTransactionService.getBudgetExpenseSummary();

        assertEquals(1, expenseSummaryList.size());
        ExpenseSummaryDTO expenseSummary = expenseSummaryList.get(0);
        assertEquals(category1.getName(), expenseSummary.getCategoryName());
        assertEquals(category1.getAmount(), expenseSummary.getCategoryBudgetAmount());
        assertEquals(new BigDecimal("25.0"), BigDecimal.valueOf(expenseSummary.getUsedBudgetPercentage()));
        assertEquals(2, expenseSummary.getExpenses().size());
    }

    private Expense createExpense(ExpenseTransactionDTO expenseTransactionDTO) {
        Expense expense = new Expense();
        expense.setName(expenseTransactionDTO.getName());
        expense.setAmount(expenseTransactionDTO.getAmount());
        expense.setCategoryName(expenseTransactionDTO.getCategoryName());
        expense.setInsertDateTime(LocalDateTime.now());
        return expense;
    }
}
