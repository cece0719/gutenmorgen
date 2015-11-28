package com.woowol.gutenmorgen.processor;

import com.woowol.gutenmorgen.processor.ExampleProcessor.Parameter;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExampleProcessor extends Processor<Parameter> {
    @Override
    public void process(Parameter parameter) throws Exception {
        parameter.getParameter();
        parameter.getListParameter();
    }

    @Data
    public static class Parameter {
        private String parameter;
        private List<String> listParameter;
    }
}
