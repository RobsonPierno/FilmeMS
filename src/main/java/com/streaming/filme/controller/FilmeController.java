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

import com.streaming.filme.dto.FilmeDTO;
import com.streaming.filme.service.FilmeService;

@RestController
@RequestMapping("/filme")
public class FilmeController {
	
	@Autowired
	private FilmeService filmeService;
	
	@GetMapping
	public List<FilmeDTO> getAll() {
		return this.filmeService.getAll();
	}
	
	@GetMapping("/{id}")
	public FilmeDTO getById(@PathVariable Integer id) {
		return this.filmeService.getById(id);
	}

	@PostMapping
	public void save(@RequestBody FilmeDTO filme) {
		this.filmeService.save(filme);
	}
	
	@PatchMapping("/{id}")
	public FilmeDTO updateById(@PathVariable Integer id, @RequestBody FilmeDTO filme) {
		return this.filmeService.updateById(id, filme);
	}
	
	@GetMapping("/sale")
	public List<FilmeDTO> getOnSales() {
		return this.filmeService.getOnSales();
	}
	
	@GetMapping("/registered/sex/{sex}")
	public List<FilmeDTO> getRegisteredBy(@PathVariable String sex) {
		return this.filmeService.getRegisteredBySex(sex);
	}
}
