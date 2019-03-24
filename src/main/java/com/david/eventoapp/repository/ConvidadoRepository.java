package com.david.eventoapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.david.eventoapp.model.Convidado;
import com.david.eventoapp.model.Evento;

public interface ConvidadoRepository extends JpaRepository<Convidado, Integer>{

	List<Convidado> findByEvento(Evento evento);
	
	Convidado findByRg(String rg);
}
