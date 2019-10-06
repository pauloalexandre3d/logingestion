package com.creditsuisse.logingestion.repository;

import com.creditsuisse.logingestion.domain.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsLog extends JpaRepository<EventLog, String> {
}
