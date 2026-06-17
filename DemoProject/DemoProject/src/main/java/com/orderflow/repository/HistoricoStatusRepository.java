package com.orderflow.repository;

import com.orderflow.model.HistoricoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoStatusRepository extends JpaRepository<HistoricoStatus, Integer> {

}
