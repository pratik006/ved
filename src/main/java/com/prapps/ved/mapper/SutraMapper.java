package com.prapps.ved.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.prapps.ved.dto.Sutra;
import com.prapps.ved.persistence.SutraEntity;

@Component
public class SutraMapper {

	public Sutra map(SutraEntity entity) {
		Sutra sutra = new Sutra();
		BeanUtils.copyProperties(entity, sutra);
		return sutra;
	}
}
