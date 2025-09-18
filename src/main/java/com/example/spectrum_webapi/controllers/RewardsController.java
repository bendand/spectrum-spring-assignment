package com.example.spectrum_webapi.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/{userId}/rewards")
public class RewardsController {

    @GetMapping(value="", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calculateRewards(@PathVariable(value="userId") int userId) {
        if (userId <= 0) {
            String response = "Invalid user ID.";
            return new ResponseEntity<>(Collections.singletonMap("response", response), HttpStatus.BAD_REQUEST);
        }

//        A customer receives 2 points for every dollar spent over $100 in each transaction,
//        plus 1 point for every dollar spent between $50 and $100 in each transaction.
//        (e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).
//        Given a record of every transaction during a three month period, calculate
//        the reward points earned for each customer per month and total.
        return new ResponseEntity<>(List.of(), HttpStatus.OK);
    }
}
