package es.uniovi.miw.ws.appfinanzas.finanzasaplication.controller;

import es.uniovi.miw.ws.appfinanzas.finanzasaplication.model.Expense;
import es.uniovi.miw.ws.appfinanzas.finanzasaplication.model.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gastos")
public class ExpenseResource {

    @Autowired
    private ExpenseRepository expenseRepository; // Repositorio de JPA

    @GetMapping
    public List<Expense> getExpenses() {
        System.out.println("getExpenses() llamado");
        return expenseRepository.findAll(); // Recupera todos los gastos desde la base de datos
    }

    @PostMapping
    public ResponseEntity<String> addExpense(@RequestBody Expense expense) {
        expenseRepository.save(expense); // Guarda el gasto en la base de datos
        return ResponseEntity.status(201).body("Gasto agregado correctamente");
    }

    @PatchMapping("/{idExpense}")
    public ResponseEntity<String> updateExpense(
            @PathVariable int idExpense,
            @RequestBody Expense updatedExpense) {

        Optional<Expense> optionalExpense = expenseRepository.findById(idExpense);

        if (optionalExpense.isPresent()) {
            Expense expense = optionalExpense.get();

            if (updatedExpense.getDescription() != null) {
                expense.setDescription(updatedExpense.getDescription());
            }
            if (updatedExpense.getAmount() != 0) {
                expense.setAmount(updatedExpense.getAmount());
            }
            if (updatedExpense.getExpenseType() != null) {
                expense.setExpenseType(updatedExpense.getExpenseType());
            }
            expenseRepository.save(expense);
            return ResponseEntity.ok("Gasto actualizado correctamente");
        }

        return ResponseEntity.status(404).body("Gasto no encontrado");
    }

    @DeleteMapping("/{idExpense}")
    public ResponseEntity<String> deleteExpense(@PathVariable int idExpense) {
        Optional<Expense> optionalExpense = expenseRepository.findById(idExpense);

        if (optionalExpense.isPresent()) {
            expenseRepository.delete(optionalExpense.get()); // Eliminar el gasto
            return ResponseEntity.ok("Gasto eliminado correctamente");
        }

        return ResponseEntity.status(404).body("Gasto no encontrado");
    }
}
