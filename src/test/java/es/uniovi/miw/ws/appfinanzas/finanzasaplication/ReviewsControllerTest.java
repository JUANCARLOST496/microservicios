package es.uniovi.miw.ws.appfinanzas.finanzasaplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.uniovi.miw.ws.appfinanzas.finanzasaplication.controller.ExpenseResource;
import es.uniovi.miw.ws.appfinanzas.finanzasaplication.controller.ExpenseSummaryResource;
import es.uniovi.miw.ws.appfinanzas.finanzasaplication.model.Expense;
import es.uniovi.miw.ws.appfinanzas.finanzasaplication.model.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest({ExpenseResource.class, ExpenseSummaryResource.class})
public class ReviewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseRepository expenseRepository;

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos a JSON

    @Test
    void testGetExpenses() throws Exception {
        Expense expense1 = new Expense(1, "Compra", 50.0, "Personal");
        Expense expense2 = new Expense(2, "Transporte", 20.0, "Trabajo");

        Mockito.when(expenseRepository.findAll()).thenReturn(Arrays.asList(expense1, expense2));

        mockMvc.perform(get("/gastos"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].description").value("Compra"))
                .andExpect(jsonPath("$[1].amount").value(20.0));
    }

    @Test
    void testAddExpense() throws Exception {
        Expense expense = new Expense(0, "Cena", 30.0, "Personal");

        Mockito.when(expenseRepository.save(Mockito.any(Expense.class))).thenReturn(expense);

        mockMvc.perform(post("/gastos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expense)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Gasto agregado correctamente"));
    }

    @Test
    void testUpdateExpense() throws Exception {
        Expense existingExpense = new Expense(1, "Cena", 30.0, "Personal");
        Expense updatedExpense = new Expense(1, "Almuerzo", 40.0, null);

        Mockito.when(expenseRepository.findById(1)).thenReturn(Optional.of(existingExpense));
        Mockito.when(expenseRepository.save(Mockito.any(Expense.class))).thenReturn(updatedExpense);

        mockMvc.perform(patch("/gastos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedExpense)))
                .andExpect(status().isOk())
                .andExpect(content().string("Gasto actualizado correctamente"));
    }

    @Test
    void testUpdateExpenseNotFound() throws Exception {
        Expense updatedExpense = new Expense(1, "Almuerzo", 40.0, null);

        Mockito.when(expenseRepository.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(patch("/gastos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedExpense)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Gasto no encontrado"));
    }

    @Test
    void testDeleteExpense() throws Exception {
        Expense expense = new Expense(1, "Cena", 30.0, "Personal");

        Mockito.when(expenseRepository.findById(1)).thenReturn(Optional.of(expense));
        Mockito.doNothing().when(expenseRepository).delete(expense);

        mockMvc.perform(delete("/gastos/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Gasto eliminado correctamente"));
    }

    @Test
    void testDeleteExpenseNotFound() throws Exception {
        Mockito.when(expenseRepository.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/gastos/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Gasto no encontrado"));
    }

    // Aca van controladores de ExpenseSummary

    @Test
    void testGetTotalExpenses() throws Exception {
        Expense expense1 = new Expense(1, "Compra", 50.0, "Personal");
        Expense expense2 = new Expense(2, "Transporte", 20.0, "Trabajo");

        Mockito.when(expenseRepository.findAll()).thenReturn(Arrays.asList(expense1, expense2));

        mockMvc.perform(get("/gastos/total"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("70.0"));
    }

    @Test
    void testGetTotalByExpenseType() throws Exception {
        Expense expense1 = new Expense(1, "Compra", 50.0, "Personal");
        Expense expense2 = new Expense(2, "Transporte", 20.0, "Trabajo");

        Mockito.when(expenseRepository.findAll()).thenReturn(Arrays.asList(expense1, expense2));

        mockMvc.perform(get("/gastos/tipo/Personal"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("50.0")); // El total debe ser 50
    }

    @Test
    void testGetMicroExpenses() throws Exception {
        Expense expense1 = new Expense(1, "Compra", 4.0, "Personal");
        Expense expense2 = new Expense(2, "Transporte", 6.0, "Trabajo");

        Mockito.when(expenseRepository.findAll()).thenReturn(Arrays.asList(expense1, expense2));

        mockMvc.perform(get("/gastos/micro"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].description").value("Compra"));
    }

    @Test
    void testGetHighExpenses() throws Exception {
        Expense expense1 = new Expense(1, "Compra", 150.0, "Personal");
        Expense expense2 = new Expense(2, "Transporte", 20.0, "Trabajo");

        Mockito.when(expenseRepository.findAll()).thenReturn(Arrays.asList(expense1, expense2));

        mockMvc.perform(get("/gastos/high"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].description").value("Compra"));
    }



    @Test
    void testGetExpensesByType() throws Exception {
        Expense expense1 = new Expense(1, "Compra", 50.0, "Personal");
        Expense expense2 = new Expense(2, "Transporte", 20.0, "Trabajo");

        Mockito.when(expenseRepository.findAll()).thenReturn(Arrays.asList(expense1, expense2));

        mockMvc.perform(get("/gastos/agrupados/Personal"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].description").value("Compra"));
    }
}
