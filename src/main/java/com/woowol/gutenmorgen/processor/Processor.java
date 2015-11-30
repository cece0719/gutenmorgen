package com.woowol.gutenmorgen.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Service
@Slf4j
public abstract class Processor<T> {
    @Autowired
    private ObjectMapper objectMapper;

    @SuppressWarnings("unchecked")
    private Class<T> genericClass = (Class<T>)GenericTypeResolver.resolveTypeArgument(getClass(), Processor.class);

    @SuppressWarnings("unchecked")
    public void process(String jsonString) throws Exception {
        process(objectMapper.readValue(jsonString, genericClass));
    }

    public List<ParameterInfo> getParameterInfoList() {
        for (Field field : genericClass.getDeclaredFields()) {
            log.info(field.toString());
        }
        return null;
    }

    public abstract String getName();
    public abstract void process(T parameter) throws Exception;

    private class ParameterInfo {
    }
}