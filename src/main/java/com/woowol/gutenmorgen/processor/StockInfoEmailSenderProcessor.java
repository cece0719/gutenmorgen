package com.woowol.gutenmorgen.processor;

import com.woowol.gutenmorgen.bo.SendMailBO;
import com.woowol.gutenmorgen.bo.StockInfoBO;
import com.woowol.gutenmorgen.processor.StockInfoEmailSenderProcessor.Parameters;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockInfoEmailSenderProcessor extends Processor<Parameters> {
    @Autowired
    private SendMailBO sendMailBO;
    @Autowired
    StockInfoBO stockInfoBO;

    @Override
    public String getName() {
        return "주식정보 메일전송";
    }

    @Override
    public void process(Parameters parameter) throws Exception {
        for (String stock : parameter.getStockList()) {
            for (String email : parameter.getEmailList()) {
                String stockInfoText = stockInfoBO.getSimpleStockInfoText(stock);
                sendMailBO.sendMail(email, stockInfoText, "");
            }
        }
    }

    @Data
    public static class Parameters {
        @TextParameter(name = "종목 이름")
        private List<String> stockList;
        @TextParameter(name = "수신인(메일 주소)")
        private List<String> emailList;
    }
}
