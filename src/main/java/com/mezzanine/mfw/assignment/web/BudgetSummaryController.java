package com.mezzanine.mfw.assignment.web;

import com.mezzanine.mfw.assignment.exception.ErrorResponse;
import com.mezzanine.mfw.assignment.model.ExpenseSummaryDTO;
import com.mezzanine.mfw.assignment.service.ExpenseTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Budget Expense Summary", description = "REST Service for Budget expense summary")
@RestController
@RequestMapping(path = "/budget-summary")
@Slf4j
public class BudgetSummaryController {

    private final ExpenseTransactionService expenseTransactionService;

    public BudgetSummaryController(ExpenseTransactionService expenseTransactionService) {
        this.expenseTransactionService = expenseTransactionService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Get all expenses by budget category and usage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "successfully retrieved expense summaries",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExpenseSummaryDTO.class)))),
            @ApiResponse(responseCode = "404", description = "expenses not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<List<ExpenseSummaryDTO>> getBudgetExpenseSummary() {
        log.info("get budget expense summary request");

        return ResponseEntity.status(HttpStatus.OK).body(expenseTransactionService.getBudgetExpenseSummary());
    }
}
