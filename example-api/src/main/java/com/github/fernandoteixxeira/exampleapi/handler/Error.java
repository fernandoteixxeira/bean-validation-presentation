package com.github.fernandoteixxeira.exampleapi.handler;

public record Error(
        String scope,
        String field,
        String value,
        String message
) {
}