package com.prapps.ved.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.prapps.ved.dto.Sutra;
import com.prapps.ved.persistence.SutraEntity;

import java.util.*;

@Component
public class SutraMapper {
	public Sutra map(SutraEntity entity) {
		Sutra sutra = new Sutra();
		BeanUtils.copyProperties(entity, sutra);
		return sutra;
	}

	public List<Sutra> map(Collection<SutraEntity> entities) {
		List<Sutra> sutras = new ArrayList<>();
		for (SutraEntity entity : entities) {
			sutras.add(map(entity));
		}

		return sutras;
	}
}
