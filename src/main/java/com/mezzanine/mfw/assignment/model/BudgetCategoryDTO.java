package com.mezzanine.mfw.assignment.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class BudgetCategoryDTO {

    @Schema(description = "Budget category name")
    @NotEmpty(message = "Budget category name is required")
    @NotNull(message = "budget category name shouldn't be blank")
    @Size(min = 3, max = 50, message = "The length of budget category name should be at least 2 and max 50")
    private String name;

    @Schema(description = "Budgeted amount for the category")
    @DecimalMin(value = "0.00", inclusive = false, message = "Budgeted amount shouldn't be less than 0")
    @Digits(integer = 5, fraction = 2)
    private BigDecimal amount;
}
