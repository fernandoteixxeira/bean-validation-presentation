package com.github.fernandoteixxeira.exampleapi.controller;

import com.github.fernandoteixxeira.exampleapi.validation.odd.Odd;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class Controller {

    @PostMapping("/number")
    public void validateNumber(@Valid @RequestBody NumberObject numberObject) {
        System.out.println("Executing endpoint validateNumber");
    }

    @GetMapping("/number/{number}")
    public void validatePathNumber(@PathVariable @Valid @Odd Integer number) {
        System.out.println("Executing endpoint validatePathNumber");
    }

    @GetMapping("/number/{number1}/number/{number2}")
    @Odd(field1="number1" , field2="number2")
    public void validatePathTwoNumbers(@PathVariable Integer number1, @PathVariable Integer number2) {
        System.out.println("Executing endpoint validatePathTwoNumbers");
    }

    @PostMapping("/period")
    public void validatePeriod(@Valid @RequestBody RequestObject requestObject) {
        System.out.println("Executing endpoint validatePeriod");
    }

    @PostMapping("/life")
    public void validateLifeOfTime(@Valid @RequestBody LifeOfTime lifeOfTime) {
        System.out.println("Executing endpoint validateLifeOfTime");
    }

}

