package com.mezzanine.mfw.assignment.web;

import com.mezzanine.mfw.assignment.exception.ErrorResponse;
import com.mezzanine.mfw.assignment.model.BudgetCategoryDTO;
import com.mezzanine.mfw.assignment.service.BudgetCategoryService;
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

@Tag(name = "Budget Categories", description = "REST Service for Budget category Management")
@RestController
@RequestMapping(path = "/budget-categories")
@Slf4j
@Validated
public class BudgetCategoryController {

    private final BudgetCategoryService budgetCategoryService;

    public BudgetCategoryController(BudgetCategoryService budgetCategoryService) {
        this.budgetCategoryService = budgetCategoryService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Retrieve all the Budget Categories data.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "successfully retrieved all budget categories",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = BudgetCategoryDTO.class)))),
            @ApiResponse(responseCode = "404", description = "budget categories not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<List<BudgetCategoryDTO>> getAllBudgetCategories() {
        log.info("get all budget categories request");

        return ResponseEntity.status(HttpStatus.OK).body(budgetCategoryService.getAllBudgetCategories());
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Add one or more new Budget Category with the given attributes.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "successfully added the budget categories",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<Map<String, Boolean>> addBudgetCategories(@Valid @RequestBody List<BudgetCategoryDTO> budgetCategoryData) {
        log.info("add budget categories request");
        budgetCategoryService.addBudgetCategories(budgetCategoryData);
        return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("success", true));
    }
}
