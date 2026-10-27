package com.faunex.faunex.repository;
import com.faunex.faunex.model.Incident;
import com.faunex.faunex.model.Ranger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
    List<Incident> findByRanger(Ranger ranger);
}
