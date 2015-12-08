package com.woowol.gutenmorgen.processor;

import com.woowol.gutenmorgen.bo.SendSmsBO;
import com.woowol.gutenmorgen.bo.StockInfoBO;
import com.woowol.gutenmorgen.processor.StockInfoSmsSenderProcessor.Parameter;
import com.woowol.gutenmorgen.util.Validate;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StockInfoSmsSenderProcessor extends Processor<Parameter> {
    @Autowired
    private SendSmsBO sendSmsBO;
    @Autowired
    StockInfoBO stockInfoBO;

    @Override
    public String getName() {
        return "주식정보 SMS전송";
    }

    @Override
    public void validateParameter(Parameter parameter) {
        Validate.checkNotEmpty(parameter.getStockList(), "종목명");
        Validate.checkNotEmpty(parameter.getMobileList(), "수신인");
        Validate.checkStockName(parameter.getStockList());
        Validate.checkMobileNo(parameter.getMobileList());
    }

    @Override
    public void process(Parameter parameter) throws Exception {
        String stockInfoText = stockInfoBO.getSimpleStockInfoText(parameter.getStockList());
        sendSmsBO.sendSms(parameter.getMobileList(), stockInfoText);
    }

    @Data
    public static class Parameter {
        @TextParameter(name = "종목 이름")
        private List<String> stockList;
        @TextParameter(name = "수신인(휴대폰 번호)")
        private List<String> mobileList;
    }
}
