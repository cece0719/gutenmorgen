package com.woowol.gutenmorgen.processor;

import com.woowol.gutenmorgen.processor.ExampleProcessor.Parameter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ExampleProcessor extends Processor<Parameter> {
    @Override
    public void process(Parameter parameter) throws Exception {
        log.info(parameter.toString());
    }

    @Data
    public static class Parameter {
        private String parameter;
        private List<String> listParameter;
    }
}
