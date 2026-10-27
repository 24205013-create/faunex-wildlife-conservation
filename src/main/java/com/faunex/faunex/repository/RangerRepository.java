package com.faunex.faunex.repository;
import com.faunex.faunex.model.Ranger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RangerRepository extends JpaRepository<Ranger, Long> {
    java.util.Optional<Ranger> findByUser(com.faunex.faunex.model.User user);
    java.util.Optional<Ranger> findByUserUsername(String username);
}
