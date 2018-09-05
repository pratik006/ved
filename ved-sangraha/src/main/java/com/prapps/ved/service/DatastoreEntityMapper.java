package com.prapps.ved.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.PropertyContainer;
import com.prapps.ved.exception.VedRuntimeException;
import org.springframework.stereotype.Component;

import com.prapps.ved.VedException;
import com.prapps.ved.dto.DatastoreObject;

@Component
public class DatastoreEntityMapper {
    public <T extends DatastoreObject, U extends PropertyContainer> U toEmbeddedEntity(final T object, Class<U> clazz) throws VedRuntimeException {
		final U embeddedEntity;
		try {
			embeddedEntity = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new VedRuntimeException("Error in instanatiation: "+e.getMessage(), e.getCause());
		}
		object.properties().stream().forEach(prop -> {
            Object val = null;
        	String cap = prop.substring(0, 1).toUpperCase() + prop.substring(1);
            try {
                val = Arrays.stream(object.getClass().getMethods())
                        .filter(field -> field.getName().equals("get"+cap))
                        .findFirst().get()
                        .invoke(object);

				if (val instanceof Collection) {
					embeddedEntity.setProperty(prop, toEmbeddedEntity((List<DatastoreObject>)val, EmbeddedEntity.class));
				} else {
					if (val instanceof DatastoreObject) {
						val = toEmbeddedEntity((DatastoreObject) val, EmbeddedEntity.class);
					}

					embeddedEntity.setProperty(prop, val);
				}
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
                e.printStackTrace();
				throw new VedRuntimeException("Error in mapping props: "+e.getMessage(), e.getCause());
            }
        });

        return embeddedEntity;
    }

    public <T extends DatastoreObject, U extends PropertyContainer> List<U> toEmbeddedEntity(Collection<T> list, Class<U> clazz) {
        return list.stream().map(t -> toEmbeddedEntity(t, clazz)).collect(Collectors.toList());
    }
    
    public <T extends DatastoreObject, U extends PropertyContainer> T fromEntity(U entity, Class<T> clazz) throws VedException {
    	T instance;
		try {
			instance = clazz.newInstance();
			for (Entry<String, Object> entry : entity.getProperties().entrySet()) {
				if (!entity.hasProperty(entry.getKey())) {
					continue;
				}

				String propName = entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1);
				Object value = entity.getProperty(entry.getKey());

				for (Method m : clazz.getMethods()) {
					if (m.getName().equals("set"+propName)) {
						if (value instanceof Collection) {
							ParameterizedType parameterizedType = (ParameterizedType) m.getGenericParameterTypes()[0];
							Class<T> targetClass = (Class<T>) Class.forName(parameterizedType.getActualTypeArguments()[0].getTypeName());
							value = fromEntity((List<EmbeddedEntity>)value, targetClass);
						}
						m.invoke(instance, value);
					}
				}
			}
	        	

		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new VedException();
		}
    	return instance;
    }
    
    public <T extends DatastoreObject, U extends PropertyContainer> List<T> fromEntity(Collection<U> entities, Class<T> clazz) throws VedException {
    	return entities.stream().map(t -> {
			try {
				return fromEntity(t, clazz);
			} catch (VedException e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
    }
    
    public <T extends DatastoreObject, U extends PropertyContainer> List<T> fromEntity(U parent, String key, Class<T> clazz) throws VedException {
		return parent.hasProperty(key) ? fromEntity((List<U>) parent.getProperty(key), clazz) : Collections.emptyList();
    }
}
