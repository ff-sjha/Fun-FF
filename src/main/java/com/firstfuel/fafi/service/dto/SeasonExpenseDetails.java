package com.firstfuel.fafi.service.dto;

import java.util.List;

import com.firstfuel.fafi.domain.SeasonExpense;

public class SeasonExpenseDetails {
    private Double totalExpense;
    private List<SeasonExpense> expenses;
    public SeasonExpenseDetails(Double totalExpense, List<SeasonExpense> expenses) {
        super();
        this.totalExpense = totalExpense;
        this.expenses = expenses;
    }
    public Double getTotalExpense() {
        return totalExpense;
    }
    public void setTotalExpense(Double totalExpense) {
        this.totalExpense = totalExpense;
    }
    public List<SeasonExpense> getExpenses() {
        return expenses;
    }
    public void setExpenses(List<SeasonExpense> expenses) {
        this.expenses = expenses;
    }
    
}
