package com.woowol.gutenmorgen.bo;

import com.woowol.gutenmorgen.processor.Processor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProcessorBO {
    @Autowired
    @Getter
    private Map<String, Processor<?>> processorMap;

    public void process(String processorName, String parameter) throws Exception {
        processorMap.get(processorName).process(parameter);
    }
}