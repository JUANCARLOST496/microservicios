package es.uniovi.miw.ws.appfinanzas.finanzasaplication.controller;

import es.uniovi.miw.ws.appfinanzas.finanzasaplication.model.Expense;
import es.uniovi.miw.ws.appfinanzas.finanzasaplication.model.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/gastos") // Path base para todos los endpoints de gastos
public class ExpenseSummaryResource {

    @Autowired
    private ExpenseRepository expenseRepository;

    // Total de todos los gastos
    @GetMapping("/total")
    public double getTotalExpenses() {
        return expenseRepository.findAll().stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    // Total de gastos por tipo
    @GetMapping("/tipo/{expenseType}")
    public double getTotalByExpenseType(@PathVariable String expenseType) {
        return expenseRepository.findAll().stream()
                .filter(expense -> expenseType.equalsIgnoreCase(expense.getExpenseType()))
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    // Microgastos (menores a 5)
    @GetMapping("/micro")
    public List<Expense> getMicroExpenses() {
        return expenseRepository.findAll().stream()
                .filter(expense -> expense.getAmount() < 5)
                .toList();
    }

    // Gastos altos (mayores a 100)
    @GetMapping("/high")
    public List<Expense> getHighExpenses() {
        return expenseRepository.findAll().stream()
                .filter(expense -> expense.getAmount() > 100)
                .toList();
    }


    // Filtrar gastos por un tipo específico
    @GetMapping("/agrupados/{expenseType}")
    public List<Expense> getExpensesByType(@PathVariable String expenseType) {
        return expenseRepository.findAll().stream()
                .filter(expense -> expenseType.equalsIgnoreCase(expense.getExpenseType()))
                .toList();
    }
}
