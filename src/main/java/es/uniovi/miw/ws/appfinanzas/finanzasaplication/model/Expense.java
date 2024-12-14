package es.uniovi.miw.ws.appfinanzas.finanzasaplication.model;

import jakarta.persistence.*;

@Entity
@Table(name = "expenses") // Nombre de la tabla en la base de datos
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    private int idExpense;

    @Column(nullable = false) // Indica que no puede ser nulo
    private String description;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String expenseType;

    @Version
    private int version; // Control de concurrencia optimista

    // Constructor vacío (necesario para JPA)
    public Expense() {}

    // Constructor completo
    public Expense(int idExpense, String description, double amount, String expenseType) {
        this.idExpense = idExpense;
        this.description = description;
        this.amount = amount;
        this.expenseType = expenseType;
    }

    // Getters y Setters
    public int getIdExpense() {
        return idExpense;
    }

    public void setIdExpense(int idExpense) {
        this.idExpense = idExpense;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
