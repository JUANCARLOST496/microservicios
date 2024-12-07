package es.uniovi.miw.ws.appfinanzas.finanzasaplication.controller;

import es.uniovi.miw.ws.appfinanzas.finanzasaplication.model.Expense;
import es.uniovi.miw.ws.appfinanzas.finanzasaplication.model.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/gastos")
public class ExpenseResoruce {
    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping
    public List<Expense> getExpenses() {
        return expenseRepository.findAll(); // Recupera todos los gastos desde la base de datos
    }

    @PostMapping
    public ResponseEntity<String> addExpense(@RequestBody Expense expense) {
        expenseRepository.save(expense); // Guarda el gasto en la base de datos
        return ResponseEntity.status(201).body("Gasto agregado correctamente");
    }
}
