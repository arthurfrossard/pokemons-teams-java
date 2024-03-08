package com.example.pokemonsteams.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class IdGeneratorService {
    private AtomicLong sequence = new AtomicLong(1);

    public long generateId() {
        return sequence.getAndIncrement();
    }
}

