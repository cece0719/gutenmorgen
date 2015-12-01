package com.woowol.gutenmorgen.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.*;

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

    public List<Map<String, Object>> getParameterInfoList() {
        List<Map<String, Object>> parameterInfoList = new ArrayList<>();
        for (Field field : genericClass.getDeclaredFields()) {
            TextParameter textParameterAnnotation;
            SelectParameter selectParameterAnnotation;
            Map<String, Object> parameterInfo = null;
            if ((textParameterAnnotation = field.getAnnotation(TextParameter.class)) != null) {
                parameterInfo = new HashMap<String, Object>(){{
                    put("name", textParameterAnnotation.name());
                    put("inputType", "text");
                }};
            } else if ((selectParameterAnnotation = field.getAnnotation(SelectParameter.class)) != null) {
                parameterInfo = new HashMap<String, Object>(){{
                    put("name", selectParameterAnnotation.name());
                    put("inputType", "select");
                    put("selectList", Arrays.asList(selectParameterAnnotation.selectList()));
                }};
            }
            if (parameterInfo != null) {
                parameterInfo.put("id", field.getName());
                if (List.class.isAssignableFrom(field.getType())) {
                    parameterInfo.put("type", "list");
                } else {
                    parameterInfo.put("type", "text");
                }
                parameterInfoList.add(parameterInfo);
            }
        }
        log.info("abc88");
        log.info(String.valueOf(parameterInfoList));
        return parameterInfoList;
    }

    public abstract String getName();
    public abstract void process(T parameter) throws Exception;

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TextParameter {
        String name();
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SelectParameter {
        String name();
        String[] selectList();
    }
}