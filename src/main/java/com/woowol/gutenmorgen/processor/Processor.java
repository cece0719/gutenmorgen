package com.woowol.gutenmorgen.processor;

import org.springframework.core.GenericTypeResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Processor<T> {
	@SuppressWarnings("unchecked")
	private Class<T> genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), Processor.class);
	
	public static ObjectMapper om = new ObjectMapper();
	
	public void processByStringParameter(String parameter) throws Exception {
		T convertedParameter = om.readValue(parameter, genericType);
		process(convertedParameter);
	}
	
	public abstract void process(T parameter) throws Exception;
}