package com.tarea.controllers;

import com.tarea.entities.Tarea;
import com.tarea.services.TareaServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tareas")
public class TareaControlador {
    private final TareaServicio tareaServicio;

    public TareaControlador(TareaServicio tareaServicio) {
        this.tareaServicio = tareaServicio;
    }

    @GetMapping
    public ResponseEntity<List<Tarea>> obtenerTareas() {
        List<Tarea> tareas = tareaServicio.getAllTareas();
        if (tareas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tareas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> obtenerTareaPorId(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("El ID proporcionado no es válido. Debe ser un número positivo.");
        }
        Optional<Tarea> tarea = tareaServicio.obtenerTareaPorId(id);
        return tarea.map(t -> ResponseEntity.ok("Tarea encontrada: " + t.toString()))
                .orElseGet(() -> ResponseEntity.status(404).body("No se encontró la tarea con ID " + id));
    }

    @PostMapping
    public ResponseEntity<String> crearTarea(@RequestBody Tarea tarea) {
        LocalDate localDate = tarea.getFecha();

        if (tarea.getTitulo().length() < 1 || tarea.getTitulo().length() > 20) {
            return ResponseEntity.badRequest().body("El título de la tarea no puede estar vacío o no puede ser tan largo.");
        }
        if (tarea.getDescripcion().length() < 1) {
            return ResponseEntity.badRequest().body("La descripción de la tarea es obligatoria y no puede estar vacía.");
        }
        if (tarea.getFecha() == null) {
            return ResponseEntity.badRequest().body("La fecha de la tarea es obligatoria y debe ser una fecha válida en formato YYYY-MM-DD.");
        }
        try{
            tareaServicio.guardarTarea(tarea);
        }catch (DateTimeParseException e){
            return ResponseEntity.badRequest().body("La fecha de la tarea es obligatoria y debe ser una fecha válida en formato YYYY-MM-DD.");
        }

        return ResponseEntity.status(201).body(tarea.toString());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarTarea(@PathVariable Long id, @RequestBody Tarea tarea) {
        if (tarea.getTitulo() == null || tarea.getTitulo().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El título es obligatorio y no puede estar vacío.");
        }
        if (tarea.getDescripcion() == null || tarea.getDescripcion().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("La descripción es obligatoria y no puede estar vacía.");
        }
        if (tarea.getFecha() == null) {
            return ResponseEntity.badRequest().body("La fecha es obligatoria y debe ser proporcionada.");
        }
        Optional<Tarea> tareaActualizada = tareaServicio.actualizarTarea(id, tarea);
        return tareaActualizada.map(t -> ResponseEntity.ok(tarea.toString()))
                .orElseGet(() -> ResponseEntity.status(404).body("No se encontró la tarea con ID " + id + " para actualizar."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTarea(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("El ID proporcionado no es válido. Debe ser un número positivo.");
        }
        if (tareaServicio.eliminarTarea(id)) {
            return ResponseEntity.ok("Tarea con ID " + id + " eliminada exitosamente.");
        } else {
            return ResponseEntity.status(404).body("No se encontró la tarea con ID " + id + " para eliminar.");
        }
    }
}
