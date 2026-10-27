package com.faunex.faunex.repository;
import com.faunex.faunex.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    java.util.Optional<Visitor> findByUser(com.faunex.faunex.model.User user);
}
