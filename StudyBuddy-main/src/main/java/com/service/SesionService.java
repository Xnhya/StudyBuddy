package com.service;

import com.model.Sesion;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SesionService {
    private final List<Sesion> sesiones = new ArrayList<>();

    public synchronized void add(Sesion s) {
        sesiones.add(s);
    }

    public synchronized List<Sesion> listar() {
        return new ArrayList<>(sesiones);
    }
}
