package es.uniovi.miw.ws.appfinanzas.finanzasaplication.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
}
