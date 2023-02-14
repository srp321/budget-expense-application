package com.mezzanine.mfw.assignment.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ExpenseSummaryDTO {

    @Schema(description = "Budget category name")
    private String categoryName;

    @Schema(description = "Budgeted amount for the category")
    private BigDecimal categoryBudgetAmount;

    @Schema(description = "Percentage of budgeted amount used in expenses")
    private Double usedBudgetPercentage;

    @Schema(description = "List of all the expenses for the category")
    private List<ExpenseTransactionDTO> expenses;
}
