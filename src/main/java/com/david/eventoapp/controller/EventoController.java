package com.david.eventoapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.david.eventoapp.model.Convidado;
import com.david.eventoapp.model.Evento;
import com.david.eventoapp.repository.ConvidadoRepository;
import com.david.eventoapp.repository.EventoRepository;

@Controller
public class EventoController {

	@Autowired
	private EventoRepository eventoRepository;

	@Autowired
	private ConvidadoRepository convidadoRepository;

	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
	public String form() {
		return "eventos/formEvento";
	}

	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST)
	public String form(Evento evento) {

		eventoRepository.save(evento);
		return "redirect:/cadastrarEvento";
	}

	@RequestMapping(value = "/eventos")
	public ModelAndView listaEventos() {
		ModelAndView modelAndView = new ModelAndView("index");
		List<Evento> eventos = eventoRepository.findAll();
		modelAndView.addObject("eventos", eventos);

		return modelAndView;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("id") Integer id) {
		Evento evento = eventoRepository.findById(id);
		ModelAndView modelAndView = new ModelAndView("eventos/detalhesEvento");
		modelAndView.addObject("evento", evento);
		
		List<Convidado> convidados = convidadoRepository.findByEvento(evento);
		modelAndView.addObject("convidados", convidados);
		return modelAndView;
	}
	
	@RequestMapping("/deletarEvento")
	public String deletarEvento(Integer id) {
		Evento evento = eventoRepository.findById(id);
		eventoRepository.delete(evento);
	
		return "redirect:/eventos";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("id") Integer id, Convidado convidado) {

		Evento evento = eventoRepository.findById(id);
		convidado.setEvento(evento);
		convidadoRepository.save(convidado);
		
		return "redirect:/{id}";

	}
	
	@RequestMapping("/deletarConvidado")
	public String deletarConvidado(String rg) {
		Convidado convidado = convidadoRepository.findByRg(rg);
		
		convidadoRepository.delete(convidado);
		
		Evento evento = convidado.getEvento();
		Integer idEvento = evento.getId();
		String id = " " + idEvento;
		
		return "redirect:/" + id;
	}
}