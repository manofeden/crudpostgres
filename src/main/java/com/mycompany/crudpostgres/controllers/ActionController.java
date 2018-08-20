package com.mycompany.crudpostgres.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.mycompany.crudpostgres.LongProcess;
import com.mycompany.crudpostgres.model.Client;
import com.mycompany.crudpostgres.service.ClientService;

@Controller
public class ActionController {

	@Autowired
	ClientService service;

	@Autowired
	LongProcess longProcess;

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RedirectView addClient(@Valid Client c, BindingResult bindingResult, Model model) {
		if (!service.getClientsForUniqSnils(c.getSnils()).isEmpty()) {
			System.out.println("redirect: del-->wait");
			return new RedirectView("/snils_dupl", true);
		}

		if (longProcess.isLongProcessWork()) {
			System.out.println("redirect: add-->wait");
			return new RedirectView("/wait", true);
		}

		System.out.println("add client=" + c);

		this.service.addClient(c);

		return new RedirectView("/", true);
	}

	@RequestMapping(value = "search", method = RequestMethod.POST)
	public ModelAndView search(@RequestParam(value = "snils", required = false) String snils, Model model) {
		System.out.println("/search - POST snils=" + snils);

		if (snils.isEmpty()) {
			return new ModelAndView("redirect:/");
		}

		List<Client> clients = service.getClientsForSnils(snils);
		model.addAttribute("clients", clients);
		ModelMap map = new ModelMap();
		map.addAllAttributes(clients);

		ModelAndView mv = new ModelAndView("index", map);
		return mv;
	}

	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam("id") int id) {
		if (longProcess.isLongProcessWork()) {
			System.out.println("redirect: edit-->wait");
			return new ModelAndView("wait");
		}

		System.out.println("edit id=" + id);

		ModelAndView mv = new ModelAndView("edit_client");
		Client client = service.getClient(id);
		mv.addObject(client);

		return mv;
	}

	@RequestMapping(path = "/delete", produces = MediaType.TEXT_PLAIN_VALUE)
	public RedirectView delete(@RequestParam("id") int id, Model map) {
		if (longProcess.isLongProcessWork()) {
			System.out.println("redirect: del-->wait");
			return new RedirectView("/wait", true);
		}

		System.out.println("delete id=" + id);

		List<Client> deleteClient = service.deleteClient(id);
		map.addAttribute("clients", deleteClient);

		return new RedirectView("/", true);
	}

}
