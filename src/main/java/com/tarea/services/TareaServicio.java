package com.tarea.services;

import com.tarea.entities.Tarea;
import com.tarea.repositories.TareaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TareaServicio {
    private final TareaRepository tareaRepository;

    public TareaServicio(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    public List<Tarea> getAllTareas() {
        return tareaRepository.findAll();
    }

    public Optional<Tarea> obtenerTareaPorId(Long id){
        return tareaRepository.findById(id);
    }

    public Tarea guardarTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    public Optional<Tarea> actualizarTarea(Long id, Tarea tarea) {
        return tareaRepository.findById(id).map(t -> {
            t.setTitulo(tarea.getTitulo());
            t.setDescripcion(tarea.getDescripcion());
            t.setFecha(tarea.getFecha());
            t.setHecho(tarea.getHecho());
            return tareaRepository.save(t);
        });
    }

    public boolean eliminarTarea(Long id) {
        if (tareaRepository.existsById(id)) {
            tareaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
