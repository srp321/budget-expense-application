package com.mezzanine.mfw.assignment.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@Validated
public class ExpenseTransactionDTO {

    @Schema(description = "Expense name")
    @NotNull(message = "expense name is required")
    @NotEmpty(message = "expense name shouldn't be blank")
    @Size(min = 3, max = 100, message = "The length of expense name should be at least 3 and max 100.")
    private String name;

    @Schema(description = "Transaction amount for the expense")
    @DecimalMin(value = "0.00", inclusive = false, message = "expense shouldn't be be less than 0")
    @Digits(integer = 5, fraction = 2)
    private BigDecimal amount;

    @Schema(description = "Category associated with expense")
    @NotNull(message = "expense category name is required")
    @NotEmpty(message = "expense category name shouldn't be blank")
    @Size(min = 3, max = 50, message = "The length of budget category name should be at least 2 and max 50.")
    private String categoryName;
}
