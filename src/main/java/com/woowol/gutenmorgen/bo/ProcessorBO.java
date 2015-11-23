package com.woowol.gutenmorgen.bo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woowol.gutenmorgen.processor.Processor;

@Service
public class ProcessorBO {
	@Autowired private Map<String, Processor<?>> processorMap;
	
	public Map<String, Processor<?>> getProcessorMap() {
		return processorMap;
	}

	public void process(String processorName, String parameter) {
		try {
			Processor<?> processor = processorMap.get(processorName);
			processor.processByStringParameter(parameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}