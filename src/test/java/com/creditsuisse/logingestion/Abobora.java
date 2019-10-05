package com.creditsuisse.logingestion;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;

public class Abobora {

    @Test
    public void abobora() {
        System.out.println(System.currentTimeMillis());

        System.out.println("--> " + Instant.ofEpochMilli(System.currentTimeMillis()));

        long currentTimeMillis = System.currentTimeMillis();
        System.out.println(LocalDateTime.parse(String.valueOf(currentTimeMillis)));
        System.out.println(LocalDateTime.parse("1491377495212"));
    }
}
