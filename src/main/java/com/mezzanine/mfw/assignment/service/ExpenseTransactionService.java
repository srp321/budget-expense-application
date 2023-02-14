package com.mezzanine.mfw.assignment.service;

import com.mezzanine.mfw.assignment.entity.Expense;
import com.mezzanine.mfw.assignment.model.ExpenseSummaryDTO;
import com.mezzanine.mfw.assignment.model.ExpenseTransactionDTO;
import com.mezzanine.mfw.assignment.repository.ExpenseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ExpenseTransactionService {

    private final BudgetCategoryService budgetCategoryService;

    private final ExpenseRepository expenseRepository;

    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    public ExpenseTransactionService(BudgetCategoryService budgetCategoryService, ExpenseRepository expenseRepository) {
        this.budgetCategoryService = budgetCategoryService;
        this.expenseRepository = expenseRepository;
    }

    public List<ExpenseTransactionDTO> getAllExpenses() {
        log.info("get all expense transactions service");

        var expenses = expenseRepository.findByIdNotNull();
        return mapToExpenseDTO(expenses);
    }

    public void addExpenseTransactions(List<ExpenseTransactionDTO> expenseTransactionData) {
        log.info("in service to add {} expense transactions with data", expenseTransactionData.size());

        List<Expense> expensesToSave = new ArrayList<>();

        var categoryNames = budgetCategoryService.getDistinctCategories();
        for (var incomingExpense : expenseTransactionData) {
            if (categoryNames.contains(incomingExpense.getCategoryName())) {
                Expense newExpense = new Expense();
                newExpense.setName(incomingExpense.getName());
                newExpense.setAmount(incomingExpense.getAmount());
                newExpense.setCategoryName(incomingExpense.getCategoryName());
                newExpense.setInsertDateTime(LocalDateTime.now());
                expensesToSave.add(newExpense);
            } else {
                log.warn("Invalid Budget Category: {} for expense", incomingExpense.getCategoryName());
            }
        }
        log.info("saving {} valid expense transactions", expensesToSave.size());
        expenseRepository.saveAll(expensesToSave);
    }

    public List<ExpenseSummaryDTO> getBudgetExpenseSummary() {
        log.info("in get budget expense summary service");

        List<ExpenseSummaryDTO> expensesSummary = new ArrayList<>();

        var allBudgetCategories = budgetCategoryService.getAllBudgetCategories();
        for (var category : allBudgetCategories) {
            var categoryExpenses = expenseRepository.findByCategoryName(category.getName());
            if (!CollectionUtils.isEmpty(categoryExpenses)) {
                log.info("expense present for category {}", category.getName());

                BigDecimal totalExpense = new BigDecimal("0.00");
                for (var currExpense : categoryExpenses) {
                    totalExpense = totalExpense.add(currExpense.getAmount());
                }
                ExpenseSummaryDTO expenseData = new ExpenseSummaryDTO();
                expenseData.setCategoryName(category.getName());
                expenseData.setCategoryBudgetAmount(category.getAmount());
                expenseData.setUsedBudgetPercentage(totalExpense.multiply(ONE_HUNDRED).divide(category.getAmount()).doubleValue());
                expenseData.setExpenses(mapToExpenseDTO(categoryExpenses));
                expensesSummary.add(expenseData);
            }
        }

        log.info("budget summary mapped for {} categories", expensesSummary.size());
        return expensesSummary;
    }

    private List<ExpenseTransactionDTO> mapToExpenseDTO(List<Expense> categoryExpenses) {
        List<ExpenseTransactionDTO> expenseTransactionList = new ArrayList<>();
        for (var expense : categoryExpenses) {
            ExpenseTransactionDTO expenseTransactionDTO = new ExpenseTransactionDTO();
            expenseTransactionDTO.setName(expense.getName());
            expenseTransactionDTO.setAmount(expense.getAmount());
            expenseTransactionDTO.setCategoryName(expense.getCategoryName());
            expenseTransactionList.add(expenseTransactionDTO);
        }
        return expenseTransactionList;
    }
}
