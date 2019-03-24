package com.david.eventoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.david.eventoapp.model.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, String> {

	Evento findById(Integer id);
}
