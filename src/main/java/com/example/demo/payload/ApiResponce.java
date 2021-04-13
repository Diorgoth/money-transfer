package com.example.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponce {

    private String message;
    private boolean success;
    private Object object;
    private Object income;
    private Object outcome;
    public ApiResponce(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ApiResponce(String message, boolean success, Object object) {
        this.message = message;
        this.success = success;
        this.object = object;
    }

    public ApiResponce(String message, boolean success, Object income, Object outcome) {
        this.message = message;
        this.success = success;
        this.income = income;
        this.outcome = outcome;
    }
}
