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
    public ExpenseRepository expenseRepository;

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
    public ResponseEntity<String> updateExpense(@PathVariable int idExpense,@RequestBody Expense updatedExpense){
   Optional<Expense> optionalExpenses =  expenseRepository.findById(idExpense);

   if(optionalExpenses.isPresent()){
       Expense expense = optionalExpenses.get();
       if(updatedExpense.getDescription()!=null && !updatedExpense.getDescription().isEmpty()){
          expense.setDescription(updatedExpense.getDescription());
       }
       if(updatedExpense.getAmount()!=0){
           expense.setAmount(updatedExpense.getAmount());
       }
       if(updatedExpense.getExpenseType()!=null && !updatedExpense.getExpenseType().isEmpty()){
           expense.setExpenseType(updatedExpense.getExpenseType());
       }
       expenseRepository.save(expense);
       return ResponseEntity.status(201).body("Gasto agregado correctamente");
      }else{
       return ResponseEntity.status(404).body("Gasto no encontrado");
       }

    }

    @DeleteMapping("/{idExpense}")
    public ResponseEntity<String> deleteExpense(@PathVariable int idExpense) {
        // Buscar el gasto por ID en la base de datos
        Optional<Expense> optionalExpense = expenseRepository.findById(idExpense);

        // Verificar si el gasto existe
        if (optionalExpense.isPresent()) {
            Expense expense = optionalExpense.get();
            expenseRepository.delete(expense); // Eliminar el gasto de la base de datos
            return ResponseEntity.ok("Gasto eliminado correctamente");
        }

        // Si no se encuentra el gasto
        return ResponseEntity.status(404).body("Gasto no encontrado");
    }





}
