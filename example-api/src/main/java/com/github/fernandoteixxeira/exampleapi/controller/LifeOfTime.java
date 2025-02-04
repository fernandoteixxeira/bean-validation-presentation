package com.github.fernandoteixxeira.exampleapi.controller;

import java.time.LocalDate;

public record LifeOfTime(
        LocalDate birthDate,
        LocalDate deathDate
) {
}
