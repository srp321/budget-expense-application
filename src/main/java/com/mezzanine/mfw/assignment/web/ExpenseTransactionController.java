package com.mezzanine.mfw.assignment.web;

import com.mezzanine.mfw.assignment.exception.ErrorResponse;
import com.mezzanine.mfw.assignment.model.ExpenseTransactionDTO;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Tag(name = "Expense Transactions", description = "REST Service for Expense Transaction Management")
@RestController
@RequestMapping(path = "/expense-transactions")
@Slf4j
@Validated
public class ExpenseTransactionController {

    private final ExpenseTransactionService expenseTransactionService;

    public ExpenseTransactionController(ExpenseTransactionService expenseTransactionService) {
        this.expenseTransactionService = expenseTransactionService;
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Retrieve the Posts based on Page request.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "successfully retrieved all posts",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)))),
            @ApiResponse(responseCode = "404", description = "posts not found",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "bad request",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    ResponseEntity<List<ExpenseTransactionDTO>> getAllBudgetCategories() {
        log.info("get all expense transactions request");

        return ResponseEntity.status(HttpStatus.OK).body(expenseTransactionService.getAllExpenses());
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Add one or more expense transactions with the given attributes.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "successfully added the expenses",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<Map<String, Boolean>> addExpenseTransactions(@Valid @RequestBody List<ExpenseTransactionDTO> expenseTransactionData) {
        log.info("add expense transaction request");
        expenseTransactionService.addExpenseTransactions(expenseTransactionData);
        return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("success", true));
    }
}
