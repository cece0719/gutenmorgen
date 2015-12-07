package com.woowol.gutenmorgen.processor;

import com.woowol.gutenmorgen.bo.SendMailBO;
import com.woowol.gutenmorgen.bo.StockInfoBO;
import com.woowol.gutenmorgen.processor.StockInfoEmailSenderProcessor.Parameter;
import com.woowol.gutenmorgen.util.Validate;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockInfoEmailSenderProcessor extends Processor<Parameter> {
    @Autowired
    private SendMailBO sendMailBO;
    @Autowired
    StockInfoBO stockInfoBO;

    @Override
    public String getName() {
        return "주식정보 메일전송";
    }

    @Override
    public void validateParameter(Parameter parameter) {
        Validate.checkNotEmpty(parameter.getStockList(), "종목명");
        Validate.checkNotEmpty(parameter.getEmailList(), "수신인");
        Validate.checkStockName(parameter.getStockList());
        Validate.checkEmailAddr(parameter.getEmailList());
    }

    @Override
    public void process(Parameter parameter) throws Exception {
        for (String stock : parameter.getStockList()) {
            for (String email : parameter.getEmailList()) {
                String stockInfoText = stockInfoBO.getSimpleStockInfoText(stock);
                sendMailBO.sendMail(email, stockInfoText, "");
            }
        }
    }

    @Data
    public static class Parameter {
        @TextParameter(name = "종목 이름")
        private List<String> stockList;
        @TextParameter(name = "수신인(메일 주소)")
        private List<String> emailList;
    }
}
