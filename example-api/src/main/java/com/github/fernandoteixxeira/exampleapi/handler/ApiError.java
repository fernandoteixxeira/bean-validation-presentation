package com.github.fernandoteixxeira.exampleapi.handler;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public record ApiError(
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<Error> errors
) {
}
