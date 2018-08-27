package com.prapps.ved.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;
import com.prapps.ved.VedException;
import com.prapps.ved.dto.DatastoreObject;

@Component
public class DatastoreEntityMapper {
    public <T extends DatastoreObject> EmbeddedEntity toEmbeddedEntity(final T object) {
        EmbeddedEntity embeddedEntity = new EmbeddedEntity();
        object.properties().stream().forEach(prop -> {
            Object val = null;
        	String cap = prop.substring(0, 1).toUpperCase() + prop.substring(1);
            try {
                val = Arrays.stream(object.getClass().getMethods())
                        .filter(field -> field.getName().equals("get"+cap))
                        .findFirst().get()
                        .invoke(object);
                embeddedEntity.setProperty(prop, val);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
                e.printStackTrace();
            }
        });

        return embeddedEntity;
    }

    public <T extends DatastoreObject> List<EmbeddedEntity> toEmbeddedEntity(Collection<T> list) {
        return list.stream().map(t -> toEmbeddedEntity(t)).collect(Collectors.toList());
    }
    
    public <T extends DatastoreObject> T fromEntity(EmbeddedEntity entity, Class<T> clazz) throws VedException {
    	T instance;
		try {
			instance = clazz.newInstance();
			entity.getProperties().entrySet().forEach(prop -> {
	        	String propName = prop.getKey().substring(0, 1).toUpperCase() + prop.getKey().substring(1);

				try {
					Arrays.stream(clazz.getMethods())
						.filter(m -> m.getName().equals("set"+propName))
						.findFirst().get()
						.invoke(instance, prop.getValue());
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
						| SecurityException e) {
					e.printStackTrace();
				}
			});
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new VedException();
		}
    	return instance;
    }
    
    public <T extends DatastoreObject> List<T> fromEntity(Collection<EmbeddedEntity> entities, Class<T> clazz) throws VedException {
    	return entities.stream().map(t -> {
			try {
				return fromEntity(t, clazz);
			} catch (VedException e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
    }
    
    public <T extends DatastoreObject> List<T> fromEntity(Entity parent, String key, Class<T> clazz) throws VedException {
		return parent.hasProperty(key) ? fromEntity((List<EmbeddedEntity>) parent.getProperty(key), clazz) : Collections.emptyList();
    }
}
