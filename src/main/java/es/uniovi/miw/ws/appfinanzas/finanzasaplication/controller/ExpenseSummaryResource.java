package com.example.money.expenseservice.controller;

import com.example.money.expenseservice.model.Expense;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/gastos") // Path base para todos los endpoints de gastos
public class ExpenseSummaryResource {

    @GetMapping("/total")
    public double getTotalExpenses() {
        // Calcula el total de todos los gastos
        return ExpenseResource.expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    @GetMapping("/tipo/{expenseType}")
    public double getTotalByExpenseType(@PathVariable String expenseType) {
        // Calcula la suma de los montos por tipo de gasto
        return ExpenseResource.expenses.stream()
                .filter(expense -> expenseType.equalsIgnoreCase(expense.getExpenseType()))
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    @GetMapping("/micro")
    public List<Expense> getMicroExpenses() {
        // Filtra los gastos menores a 5
        return ExpenseResource.expenses.stream()
                .filter(expense -> expense.getAmount() < 5)
                .toList();
    }

    @GetMapping("/high")
    public List<Expense> getHighExpenses() {
        // Filtra los gastos mayores a 100
        return ExpenseResource.expenses.stream()
                .filter(expense -> expense.getAmount() > 100)
                .toList();
    }

    @GetMapping("/agrupados")
    public Map<String, List<Expense>> getExpensesGroupedByType() {
        // Agrupar gastos por tipo de gasto (expenseType)
        return ExpenseResource.expenses.stream()
                .collect(Collectors.groupingBy(Expense::getExpenseType));
    }
    @GetMapping("/agrupados/{expenseType}")
    public List<Expense> getExpensesByType(@PathVariable String expenseType) {
        // Filtra los gastos que coincidan con el tipo especificado
        return ExpenseResource.expenses.stream()
                .filter(expense -> expenseType.equalsIgnoreCase(expense.getExpenseType()))
                .toList();
    }
}
