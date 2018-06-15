package com.mycompany.crudpostgres.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.mycompany.crudpostgres.service.ClientService;

/***
 * @author Leonid Ivanov
 */

@Controller
public class MyController {
	static boolean isTabClients = false;

	@Autowired
	ClientService service;

	@RequestMapping("/")
	public String index(Model map) {
		System.out.println("/index - GET");

		if (!isTabClients) {
			service.createClients();
			isTabClients = true;
		}

		map.addAttribute("clients", service.getClients());
		return "index";
	}

	@RequestMapping("/wait")
	public String wait(Model map) {
		System.out.println("/wait - GET");
		return "wait";
	}

	@RequestMapping("/snils_dupl")
	public String snils_dupl() {
		System.out.println("/snils_dupl - GET");
		return "snils_dupl";
	}

	@RequestMapping("/on_main")
	public RedirectView on_main(Model map) {
		System.out.println("/on_main - GET");
		return new RedirectView("/", true);
	}

	@RequestMapping(value = "/edit.png", method = GET)
	public ResponseEntity<byte[]> getImageEdit() throws IOException {
		ClassPathResource imgFile = new ClassPathResource("images/edit.png");
		byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
	}

	@RequestMapping(value = "/delete24.png", method = GET)
	public ResponseEntity<byte[]> getImageDelete() throws IOException {
		ClassPathResource imgFile = new ClassPathResource("images/delete24.png");
		byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
	}
}
