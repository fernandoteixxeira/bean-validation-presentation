package com.github.fernandoteixxeira.exampleapi.controller;

import com.github.fernandoteixxeira.exampleapi.validation.period.ValidPeriod;

public record RequestObject(
        String name,
        @ValidPeriod
        Period period
) {
}
