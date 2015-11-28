package com.woowol.gutenmorgen.bo;

import com.woowol.gutenmorgen.processor.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProcessorBO {
    @Autowired
    private Map<String, Processor<?>> processorMap;

    public Map<String, Processor<?>> getProcessorMap() {
        return processorMap;
    }

    public void process(String processorName, String parameter) throws Exception {
        processorMap.get(processorName).process(parameter);
    }
}