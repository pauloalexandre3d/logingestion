package com.creditsuisse.logingestion.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "eventslog")
public class EventLog implements Serializable {

    @Id
    private String id;
    private String state;
    private String type;
    private String host;
    private LocalDateTime timestamp;
    private Boolean longDuration;

}
