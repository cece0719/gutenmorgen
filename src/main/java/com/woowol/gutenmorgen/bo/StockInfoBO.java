package com.woowol.gutenmorgen.bo;

import com.woowol.gutenmorgen.exception.ResultException;
import com.woowol.gutenmorgen.model.Result;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;

@Service
public class StockInfoBO {
    public String getSimpleStockInfoText(String stockName) throws IOException {
        String code = getStockCodeByStockName(stockName);

        Document doc = Jsoup.connect("http://finance.naver.com/search/searchList.nhn?query=" + code).get();
        Elements rows = doc.select("table.tbl_search>tbody>tr>td");

        //String stockName = rows.get(0).text();
        String currentPrice = rows.get(1).text();
        String vsYesterdayPrice = rows.get(2).text();
        String vsYesterdayPercent = rows.get(3).text();

        StringBuilder sb = new StringBuilder();
        sb.append(vsYesterdayPercent.startsWith("-") ? "패!배!의!" : "승!리!의!");
        for (int i = 0; i < stockName.length(); i++) {
            sb.append(stockName.substring(i, i + 1));
            sb.append("!");
        }
        sb.append(" ");
        sb.append(currentPrice);
        sb.append(" ");
        sb.append(vsYesterdayPercent.startsWith("-") ? "↓" : "↑");
        sb.append(vsYesterdayPrice);
        sb.append(" ");
        sb.append(vsYesterdayPercent);
        return sb.toString();
    }

    private String getStockCodeByStockName(String stockName) throws IOException {
        Document doc1 = Jsoup.connect("http://www.krx.co.kr/por_kor/popup/JHPKOR13008_12.jsp?market_gubun=allVal&mkt_typ=S&word=" + URLEncoder.encode(stockName, "UTF-8")).get();
        Elements rows2 = doc1.select("table#tbl1>tbody>tr");

        String[] stockArray = rows2.get(0).text().split(" ");

        if (stockArray.length < 3) {
            throw new ResultException(Result.ReturnCode.PARAMETER_ERROR, "적합한 종목명이 아닙니다 : " + stockName);
        }

        if (stockName.equals(stockArray[1])) {
            return stockArray[0].replace("A", "");
        }
        throw new ResultException(Result.ReturnCode.PARAMETER_ERROR, "적합한 종목명이 아닙니다 : " + stockName);
    }
}
