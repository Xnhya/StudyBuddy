package com.config;

import com.model.Facultad;
import com.model.Carrera;
import com.model.Materia;
import com.repository.FacultadRepository;
import com.repository.CarreraRepository;
import com.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private FacultadRepository facultadRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Override
    public void run(String... args) {
        // Facultades
        if (facultadRepository.count() == 0) {
            Facultad ing = new Facultad("Ingeniería y Ciencias");
            Facultad hum = new Facultad("Humanidades y Artes");
            Facultad eco = new Facultad("Ciencias Económicas");
            Facultad sal = new Facultad("Ciencias de la Salud");
            facultadRepository.saveAll(List.of(ing, hum, eco, sal));

            // Carreras
            Carrera sw = new Carrera("Ingeniería de Software", ing);
            Carrera civil = new Carrera("Ingeniería Civil", ing);
            Carrera lit = new Carrera("Literatura", hum);
            Carrera mus = new Carrera("Música", hum);
            Carrera adm = new Carrera("Administración de Empresas", eco);
            Carrera cont = new Carrera("Contabilidad y Finanzas", eco);
            Carrera med = new Carrera("Medicina Humana", sal);
            Carrera enf = new Carrera("Enfermería", sal);
            carreraRepository.saveAll(List.of(sw, civil, lit, mus, adm, cont, med, enf));

            // Materias base
            materiaRepository.saveAll(List.of(
                new Materia("Bases de Datos"),
                new Materia("Programación Web"),
                new Materia("Algoritmos y Estructuras de Datos"),
                new Materia("Literatura Medieval"),
                new Materia("Microeconomía"),
                new Materia("Matemática Financiera"),
                new Materia("Anatomía Humana"),
                new Materia("Cuidados Básicos del Paciente")
            ));
        } else if (materiaRepository.count() == 0) {
            // Si existen facultades pero no materias, poblar materias al menos
            materiaRepository.saveAll(List.of(
                new Materia("Bases de Datos"),
                new Materia("Programación Web"),
                new Materia("Algoritmos y Estructuras de Datos")
            ));
        }
    }
}


