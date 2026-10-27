package com.faunex.faunex.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.faunex.faunex.model.VisitRecord;
import com.faunex.faunex.model.Visitor;

@Repository
public interface VisitRecordRepository extends JpaRepository<VisitRecord, Long> {

    public Object findByVisitor(Visitor visitor);
}
