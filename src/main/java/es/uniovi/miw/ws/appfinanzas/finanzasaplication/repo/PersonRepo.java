package es.uniovi.miw.ws.appfinanzas.finanzasaplication.repo;

import es.uniovi.miw.ws.appfinanzas.finanzasaplication.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepo extends JpaRepository<Person,Long> {
}
