package com.streaming.filme.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.streaming.filme.dto.GenderDTO;
import com.streaming.filme.service.GenderService;

@RestController
@RequestMapping("/filme/genero")
public class GenderController {

	@Autowired
	private GenderService genderService;
	
	@GetMapping
	public List<GenderDTO> getAll() {
		return this.genderService.getAll();
	}
	
	@GetMapping("/{id}")
	public GenderDTO getById(@PathVariable Integer id) {
		return this.genderService.getById(id);
	}

	@PostMapping
	public void save(@RequestBody GenderDTO gender) {
		this.genderService.save(gender);
	}
	
	@PatchMapping("/{id}")
	public GenderDTO updateById(@PathVariable Integer id, @RequestBody GenderDTO gender) {
		return this.genderService.updateById(id, gender);
	}
}
