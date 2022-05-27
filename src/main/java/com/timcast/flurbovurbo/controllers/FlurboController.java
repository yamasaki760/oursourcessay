package com.timcast.flurbovurbo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.timcast.flurbovurbo.model.Flurbo;
import com.timcast.flurbovurbo.model.Vurbo;
import com.timcast.flurbovurbo.service.CacheService;
import com.timcast.flurbovurbo.service.QueueService;


@Controller
public class FlurboController {

	@Autowired
	CacheService cacheService;
	
	@Autowired
	QueueService queueService;
	
	@GetMapping("/listAllFlurbos")
	public String listAll(Model model) {
		
		List<Flurbo> flurbos = cacheService.getAllFlurbos();
		
		model.addAttribute("allFlurbos", flurbos);

		return "listAllFlurbos";
	}

	@GetMapping("/reset/{id}")
	public String flushFlurbo(@PathVariable(value="id") int flurboId, Model model) {
		queueService.flushQueue(flurboId);
		cacheService.flushCurrentVurboDocument(flurboId);
		cacheService.appendVurboToFlurbo("", flurboId, "----reset----");
		return "redirect:/";
	}
	
	
	@GetMapping("/listVurbosForFlurbo/{id}")
	public String listAllVurbos(@PathVariable(value="id") int flurboId, Model model) {
		@SuppressWarnings("deprecation")
		List<Vurbo> vurbos = cacheService.getAllVurbosForFlurbo(flurboId);
		model.addAttribute("allVurbos", vurbos);
		return "listAllVurbos";

	}
	
	@GetMapping("/")
	public String loadWebSockets() {
		return "sourcesSay";
	}
	

}
