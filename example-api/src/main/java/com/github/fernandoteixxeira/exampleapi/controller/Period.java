package com.github.fernandoteixxeira.exampleapi.controller;

import java.time.LocalDate;

public record Period(
        LocalDate initialDate,
        LocalDate finalDate
) {
}

