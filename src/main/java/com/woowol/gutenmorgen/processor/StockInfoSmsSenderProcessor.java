package com.woowol.gutenmorgen.processor;

import com.woowol.gutenmorgen.bo.SendSmsBO;
import com.woowol.gutenmorgen.bo.StockInfoBO;
import com.woowol.gutenmorgen.processor.StockInfoSmsSenderProcessor.Parameters;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StockInfoSmsSenderProcessor extends Processor<Parameters> {
    @Autowired
    private SendSmsBO sendSmsBO;
    @Autowired
    StockInfoBO stockInfoBO;

    @Override
    public String getName() {
        return "주식정보 SMS전송";
    }

    @Override
    public void process(Parameters parameter) throws Exception {
        for (String stock : parameter.getStockList()) {
            for (String mobile : parameter.getMobileList()) {
                String stockInfoText = stockInfoBO.getSimpleStockInfoText(stock);
                sendSmsBO.sendSms(mobile, stockInfoText);
            }
        }
    }

    @Data
    public static class Parameters {
        @TextParameter(name = "종목 이름")
        private List<String> stockList;
        @TextParameter(name = "수신인(휴대폰 번호)")
        private List<String> mobileList;
    }
}
