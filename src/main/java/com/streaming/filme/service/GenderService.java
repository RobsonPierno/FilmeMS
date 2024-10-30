package com.streaming.filme.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.streaming.filme.dto.GenderDTO;
import com.streaming.filme.model.Gender;
import com.streaming.filme.repository.GenderRepository;

@Service
public class GenderService {

	@Autowired
	private GenderRepository genderRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	public List<GenderDTO> getAll() {
		List<GenderDTO> genderDtoList = new ArrayList<>();
		
		List<Gender> genders = this.genderRepository.findAll();
		if (genders != null && !genders.isEmpty()) {
			genders.forEach(f -> {
				GenderDTO genderDto = this.entityToDto(f);
				genderDtoList.add(genderDto);
			});			
		}
		
		return genderDtoList;
	}
	
	public GenderDTO getById(Integer id) {
		Optional<Gender> genderOpt = this.genderRepository.findById(id);
		if (genderOpt.isPresent()) {
			GenderDTO genderDto = this.entityToDto(genderOpt.get());
			return genderDto;
		}
		return null;
	}
	
	public GenderDTO getByName(String name) {
		Optional<Gender> genderOpt = this.getEntityByName(name);
		if (genderOpt.isPresent()) {
			GenderDTO genderDto = this.entityToDto(genderOpt.get());
			return genderDto;
		}
		return null;
	}
	
	public Optional<Gender> getEntityByName(String name) {
		Optional<Gender> genderOpt = this.genderRepository.findByName(name);
		return genderOpt;
	}

	public void save(GenderDTO genderDto) {
		if (genderDto != null && genderDto.isMadatoryFieldsNotNull()) {
			Gender gender = this.dtoToEntity(genderDto);
			this.genderRepository.save(gender);
		}
	}

	public GenderDTO updateById(Integer id, GenderDTO genderDto) {
		if (genderDto != null && id != null) {
			Optional<Gender> genderOpt = this.genderRepository.findById(id);
			if (genderOpt.isPresent()) {
				Gender gender = genderOpt.get();
				
				gender.setName(genderDto.getName() != null ? genderDto.getName() : gender.getName());
				
				Gender genderUpd = this.genderRepository.save(gender);
				
				return this.entityToDto(genderUpd);
			}
		}
		return null;
	}
	
	private GenderDTO entityToDto(final Gender gender) {
		return this.mapper.map(gender, GenderDTO.class);
	}
	
	private Gender dtoToEntity(final GenderDTO genderDto) {
		return this.mapper.map(genderDto, Gender.class);
	}
}
