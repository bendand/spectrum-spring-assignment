package com.example.spectrum_webapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;

@RestController
@RequestMapping("/{userId}/rewards")
public class RewardsController {

    @GetMapping(value="", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calculateRewards(@PathVariable(value="userId") int userId) {
        if (userId <= 0) {
            String response = "Invalid user ID.";
            return new ResponseEntity<>(Collections.singletonMap("response", response), HttpStatus.BAD_REQUEST);
        }

        // query for user transactions from database here using userId, filter by last 3 months
        // dummy data array of transactions in amount, date format
        String[][] transactionsArr = {
                {"173.20", "2025-06-25"},
                {"57.29", "2025-07-03"},
                {"120.00", "2025-07-15"},
                {"45.00", "2025-07-20"},
                {"200.00", "2025-08-05"},
                {"75.50", "2025-08-10"},
                {"99.99", "2025-08-15"},
                {"150.00", "2025-08-20"},
                {"49.99", "2025-09-01"},
                {"250.00", "2025-09-10"},
                {"80.00", "2025-09-15"},
                {"130.00", "2025-09-17"},
        };

        // get current date and start dates for second and third months
        LocalDate currentDate = LocalDate.now();
        LocalDate secondMonthStart = currentDate.minusMonths(2);
        LocalDate thirdMonthStart = currentDate.minusMonths(1);

        // initialize points for each month
        int firstMonthPoints = 0;
        int secondMonthPoints = 0;
        int thirdMonthPoints = 0;

        for (String[] transaction : transactionsArr) {
            double amount = Math.floor(Double.parseDouble(transaction[0]));
            LocalDate transactionDate = LocalDate.parse(transaction[1]);

            int points = 0;
            if (amount > 100) {
                // 2 points for every dollar over 100 + 50 points for 50-$100
                points += (int)((amount - 100) * 2) + 50;
            } else if (amount > 50) {
                // 1 point for every dollar over 50
                points += (int)(amount - 50);
            }

            if (transactionDate.isBefore(secondMonthStart)) {
                firstMonthPoints += points;
            } else if (transactionDate.isBefore(thirdMonthStart)) {
                secondMonthPoints += points;
            } else {
                thirdMonthPoints += points;
            }
        }

        HashMap<String, Integer> pointTotals = new HashMap<>();
        pointTotals.put("First month points", firstMonthPoints);
        pointTotals.put("Second month points", secondMonthPoints);
        pointTotals.put("Third month points", thirdMonthPoints);
        pointTotals.put("Total points", firstMonthPoints + secondMonthPoints + thirdMonthPoints);

        return new ResponseEntity<>(pointTotals, HttpStatus.OK);
    }
}
