package com.woowol.gutenmorgen.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Service;

@Service
public abstract class Processor<T> {
    @Autowired
    private ObjectMapper objectMapper;

    public void process(String jsonString) throws Exception {
        process(objectMapper.readValue(jsonString, (Class<T>)GenericTypeResolver.resolveTypeArgument(getClass(), Processor.class)));
    }

    public abstract void process(T parameter) throws Exception;
}