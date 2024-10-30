package com.streaming.filme.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.streaming.filme.dto.FilmeDTO;
import com.streaming.filme.model.Filme;
import com.streaming.filme.model.Gender;
import com.streaming.filme.producer.PessoaProducer;
import com.streaming.filme.repository.FilmeRepository;
import com.streaming.pessoaclient.client.PessoaClient;
import com.streaming.pessoaclient.dto.PessoaDTO;

@Service
public class FilmeService {
	
	private final FilmeRepository filmeRepository;
	private final PessoaProducer pessoaProducer;
	private final GenderService genderService;
	private final PessoaClient pessoaServiceFeign;
	private final RestTemplate restTemplate;
	private final ModelMapper mapper;
	
	@Value("${service.url.pessoa.getbyid}")
	private String getPessoaByIdUrl;
	
	private final LocalDate endSales = LocalDate.of(2005, 01, 01);
	
	@Autowired
	public FilmeService(FilmeRepository filmeRepository, GenderService genderService, ModelMapper mapper, RestTemplate restTemplate,
			PessoaClient pessoaServiceFeign, PessoaProducer pessoaProducer) {
		super();
		this.pessoaServiceFeign = pessoaServiceFeign;
		this.filmeRepository = filmeRepository;
		this.pessoaProducer = pessoaProducer;
		this.genderService = genderService;
		this.restTemplate = restTemplate;
		this.mapper = mapper;
	}

	public List<FilmeDTO> getAll() {
		List<FilmeDTO> filmeDtoList = new ArrayList<>();
		
		List<Filme> filmes = this.filmeRepository.findAll();
		if (filmes != null && !filmes.isEmpty()) {
			filmes.forEach(f -> {
				FilmeDTO filmeDto = this.entityToDto(f);
				filmeDtoList.add(filmeDto);
			});			
		}
		
		return filmeDtoList;
	}
	
	public FilmeDTO getById(Integer id) {
		Optional<Filme> filmeOpt = this.filmeRepository.findById(id);
		if (filmeOpt.isPresent()) {
			FilmeDTO filmeDto = this.entityToDto(filmeOpt.get());
			
			this.callServiceUsingRestTemplate(filmeDto);
			this.callServiceUsingFeign(filmeDto);
			
			return filmeDto;
		}
		return null;
	}

	public void save(FilmeDTO filmeDto) {
		if (filmeDto != null && filmeDto.isMadatoryFieldsNotNull()) {
			Filme filme = this.dtoToEntity(filmeDto);
			
			Optional<Gender> genderOpt = this.genderService.getEntityByName(filmeDto.getGender());
			if (genderOpt.isPresent()) {
				filme.setGender(genderOpt.get());
				filme.setDtInsert(new Date());
				this.filmeRepository.save(filme);
				this.pessoaProducer.sendMessage(String.format("Notify person ID (%s) that a movie was added!", filmeDto.getIdPessoa()));
			}
		}
	}

	public FilmeDTO updateById(Integer id, FilmeDTO filmeDto) {
		if (filmeDto != null && id != null) {
			Optional<Filme> filmeOpt = this.filmeRepository.findById(id);
			if (filmeOpt.isPresent()) {
				Filme filme = filmeOpt.get();
				
				filme.setName(filmeDto.getName() != null ? filmeDto.getName() : filme.getName());
				filme.setActive(filmeDto.getActive() != null ? filmeDto.getActive() : filme.getActive());
				filme.setDirector(filmeDto.getDirector() != null ? filmeDto.getDirector() : filme.getDirector());
				filme.setIdPessoa(filmeDto.getIdPessoa() != null ? filmeDto.getIdPessoa() : filme.getIdPessoa());
				filme.setDtRelease(filmeDto.getDtRelease() != null ? Date.from(filmeDto.getDtRelease().atStartOfDay(ZoneId.systemDefault()).toInstant()) : filme.getDtRelease());
				
				Optional<Gender> genderOpt = this.genderService.getEntityByName(filmeDto.getGender());
				if (genderOpt.isPresent()) {
					Gender gender = genderOpt.get();
					filme.setGender(gender);
				}
				
				Filme filmeUpd = this.filmeRepository.save(filme);
				
				return this.entityToDto(filmeUpd);
			}
		}
		return null;
	}
	
	public List<FilmeDTO> getOnSales() {
		List<FilmeDTO> filmeDtoList = new ArrayList<>();
		
		Date dateEndSales = Date.from(this.endSales.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Sort sort = Sort.by(Sort.Direction.DESC, "dtRelease");
		List<Filme> filmes = this.filmeRepository.findByActiveTrueAndDtReleaseBefore(dateEndSales, sort);
		
		if (filmes != null && !filmes.isEmpty()) {
			filmes.forEach(f -> {
				FilmeDTO filmeDto = this.entityToDto(f);
				filmeDtoList.add(filmeDto);
			});
		}
		return filmeDtoList;
	}
	
	public List<FilmeDTO> getRegisteredBySex(String sex) {
		List<FilmeDTO> filmeDtoList = new ArrayList<>();
		
		List<Filme> filmes = this.filmeRepository.findBySexOfWhoRegistered(sex.toUpperCase());
		if (filmes != null && !filmes.isEmpty()) {
			filmes.forEach(f -> {
				FilmeDTO filmeDto = this.entityToDto(f);
				filmeDtoList.add(filmeDto);
			});
		}
		return filmeDtoList;
	}
	
	private FilmeDTO entityToDto(final Filme filme) {
		return this.mapper.map(filme, FilmeDTO.class);
	}
	
	private Filme dtoToEntity(final FilmeDTO filmeDto) {
		return this.mapper.map(filmeDto, Filme.class);
	}
	
	private void callServiceUsingRestTemplate(FilmeDTO filmeDto) {
		String localGetPessoaByIdUrl = String.format(this.getPessoaByIdUrl, filmeDto.getIdPessoa());
		ResponseEntity<String> resp = this.restTemplate.getForEntity(localGetPessoaByIdUrl, String.class);
		System.out.println("RestTemplate resp: " + resp.getBody());
	}

	private void callServiceUsingFeign(FilmeDTO filmeDto) {
		PessoaDTO pessoaDto = this.pessoaServiceFeign.getById(filmeDto.getIdPessoa());
		System.out.println("Feign resp: " + pessoaDto);
	}
}
