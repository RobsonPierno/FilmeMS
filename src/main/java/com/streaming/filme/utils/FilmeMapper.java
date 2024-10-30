package com.streaming.filme.utils;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import com.streaming.filme.dto.FilmeDTO;
import com.streaming.filme.model.Filme;
import com.streaming.filme.model.Gender;

public class FilmeMapper {
	
	public static void mapFields(ModelMapper mapper) {
		EntityToDTO(mapper);
		DtoToEntity(mapper);
	}
	
	private static void EntityToDTO(ModelMapper mapper) {
		TypeMap<Filme, FilmeDTO> propertyMapper = mapper.createTypeMap(Filme.class, FilmeDTO.class);
		
		propertyMapper.addMapping(Filme::getDtInsert, FilmeDTO::setDtInsertTmp);
		
		propertyMapper.addMapping(Filme::getDtRelease, FilmeDTO::setDtReleaseTmp);
		
		Converter<Gender, String> genderConverter = new AbstractConverter<Gender, String>() {
		    @Override
		    protected String convert(Gender gender) {
		        return gender != null ? gender.getName() : null;
		    }
		};
		
		propertyMapper.addMappings(m -> m.using(genderConverter)
				.map(Filme::getGender, FilmeDTO::setGender));
		
	}
	
	private static void DtoToEntity(ModelMapper mapper) {
		TypeMap<FilmeDTO, Filme> propertyMapper = mapper.createTypeMap(FilmeDTO.class, Filme.class);
		
		propertyMapper.addMapping(FilmeDTO::getDtInsertTmp, Filme::setDtInsert);
		
		propertyMapper.addMapping(FilmeDTO::getDtReleaseTmp, Filme::setDtRelease);
		
		propertyMapper.addMappings(m -> m.skip(Filme::setGender));
	}
}
