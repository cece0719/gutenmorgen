package com.woowol.gutenmorgen.processor;

import com.woowol.gutenmorgen.bo.SendSmsBO;
import com.woowol.gutenmorgen.processor.StockInfoSmsSenderProcessor.Parameters;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class StockInfoSmsSenderProcessor extends Processor<Parameters> {
    public static final String LOSE = "패!배!의!";
    public static final String WIN = "승!리!의!";

    @Autowired
    private SendSmsBO sendSmsBO;

    @Override
    public String getName() {
        return "주식정보 SMS전송";
    }

    @Override
    public void process(Parameters parameter) throws Exception {
        for (String stock : parameter.getStockList()) {
            for (String mobile : parameter.getMobileList()) {
                sendSmsBO.sendSms(mobile, printPrice(stock));
            }
        }
    }

    private static String printPrice(String code) {
        String result = "";
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet("http://finance.naver.com/search/searchList.nhn?query=" + code);
        try {
            result = httpClient.execute(httpget, new BasicResponseHandler() {
                @Override
                public String handleResponse(HttpResponse response) throws IOException {
                    String res = new String(super.handleResponse(response).getBytes());
                    Document doc = Jsoup.parse(res);
                    Elements rows = doc.select("table.tbl_search tbody tr");
                    String[] items = new String[]{"종목명", "현재가", "전일대비", "등락율"};
                    String[] values = new String[4];

                    for (Element row : rows) {
                        Iterator<Element> iterElem = row.getElementsByTag("td").iterator();
                        for (int i = 0; i < items.length; i++) {
                            if (items[i].equals("종목명")) {
                                String name = iterElem.next().text();
                                String decoName = "";
                                for (int j = 0; j < name.length(); j++) {
                                    decoName += name.charAt(j) + "!";
                                }
                                values[i] = decoName;
                                continue;
                            }

                            values[i] = iterElem.next().text();
                        }

                        String name = (values[3].contains("-") ? LOSE : WIN) + values[0];
                        String price = values[1];
                        String def = (values[3].contains("-") ? "↓" : "↑") + values[2];
                        String per = values[3];

                        return name + " " + price + " " + def + " " + per;
                    }

                    return res;
                }
            });
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Data
    public static class Parameters {
        @TextParameter(name="종목 번호")
        private List<String> stockList;
        @TextParameter(name="수신인(휴대폰 번호)")
        private List<String> mobileList;
    }
}
