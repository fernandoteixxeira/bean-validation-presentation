package com.github.fernandoteixxeira.exampleapi.controller;

import com.github.fernandoteixxeira.exampleapi.validation.odd.Odd;

public record NumberObject(
        @Odd
        Integer number
) {
}
