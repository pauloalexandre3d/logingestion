package com.creditsuisse.logingestion;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class EventLog implements Serializable {

    private String id;
    private String state;
    private String type;
    private String host;
    private LocalDateTime timestamp;

}
