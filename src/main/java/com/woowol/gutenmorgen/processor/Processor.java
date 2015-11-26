package com.woowol.gutenmorgen.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.GenericTypeResolver;

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