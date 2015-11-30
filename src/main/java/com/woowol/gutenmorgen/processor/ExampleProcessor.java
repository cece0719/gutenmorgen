package com.woowol.gutenmorgen.processor;

import com.woowol.gutenmorgen.processor.ExampleProcessor.Parameters;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ExampleProcessor extends Processor<Parameters> {
    @Override
    public String getName() {
        return "예제";
    }

    @Override
    public void process(Parameters parameter) throws Exception {
        log.info(parameter.toString());
    }

    @Data
    public static class Parameters {
        private String parameter;
        private List<String> listParameter;
    }
}
