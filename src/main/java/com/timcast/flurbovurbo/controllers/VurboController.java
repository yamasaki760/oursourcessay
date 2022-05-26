package com.timcast.flurbovurbo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.timcast.flurbovurbo.model.Vurbo;
import com.timcast.flurbovurbo.service.CacheService;

@Controller
public class VurboController {

	@Autowired
	private CacheService cacheService;
	
	@GetMapping("/listAllVurbos")
	public String listAll(Model model) {
		List<Vurbo> vurbos = cacheService.getAllVurbos();
		model.addAttribute("allVurbos", vurbos);
		return "listAllVurbos";
	}
}
