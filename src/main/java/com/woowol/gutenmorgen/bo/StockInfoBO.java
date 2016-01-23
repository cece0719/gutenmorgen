package com.woowol.gutenmorgen.bo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowol.gutenmorgen.exception.ResultException;
import com.woowol.gutenmorgen.model.Result;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Service
public class StockInfoBO {
    @Autowired
    private ObjectMapper objectMapper;

    public String getSimpleStockInfoText(List<String> stockNameList) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (String stockName : stockNameList) {
            sb.append(getSimpleStockInfoText(stockName));
            sb.append("\n\n");
        }
        return sb.toString().substring(0, sb.length() - 2);
    }

    public String getSimpleStockInfoText(String stockName) throws IOException {
        String code = getStockCodeByStockName(stockName);

        Document doc = Jsoup.connect("http://finance.naver.com/search/searchList.nhn?query=" + code).get();
        Elements rows = doc.select("table.tbl_search>tbody>tr>td");

        //String stockName = rows.get(0).text();
        String currentPrice = rows.get(1).text();
        String vsYesterdayPrice = rows.get(2).text();
        String vsYesterdayPercent = rows.get(3).text();

        String sb = stockName + " : " +
                currentPrice + "\n" +
                (vsYesterdayPercent.startsWith("-") ? "↓" : "↑") +
                vsYesterdayPrice +
                ", " +
                vsYesterdayPercent;
        return sb;
    }

    public String getStockCodeByStockName(String stockName) {
        String body = null;
        List<List<List<List<String>>>> list;
        try {
            body = Jsoup.connect("http://ac.finance.naver.com:11002/ac?st=1&r_lt=1&q=" + URLEncoder.encode(stockName, "UTF-8")).ignoreContentType(true).execute().body();
            list = (List<List<List<List<String>>>>) ((Map<String, Object>) objectMapper.readValue(body, new TypeReference<Map<String, Object>>() {})).get("items");
        } catch (IOException e) {
            throw new ResultException(e);
        }

        for (List<List<List<String>>> list2 : list) {
            for (List<List<String>> list3 : list2) {
                if (stockName.equals(list3.get(1).get(0))) {
                    return list3.get(0).get(0);
                }
            }
        }

        throw new ResultException(Result.ReturnCode.PARAMETER_ERROR, "적합한 종목명이 아닙니다 : " + stockName);
    }
}
