package com.mezzanine.mfw.assignment.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {

    @Schema(description = "Error status code")
    private final int status;

    @Schema(description = "Error message list with additional information")
    private final List<String> message;
}
