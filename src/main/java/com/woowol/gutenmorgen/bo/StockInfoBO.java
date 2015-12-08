package com.woowol.gutenmorgen.bo;

import com.woowol.gutenmorgen.exception.ResultException;
import com.woowol.gutenmorgen.model.Result;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Service
public class StockInfoBO {
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

    private String getStockCodeByStockName(String stockName) throws IOException {
        Document doc1 = Jsoup.connect("http://www.krx.co.kr/por_kor/popup/JHPKOR13008_12.jsp?market_gubun=allVal&mkt_typ=S&word=" + URLEncoder.encode(stockName, "UTF-8")).get();
        Elements rows2 = doc1.select("table#tbl1>tbody>tr");

        String[] stockArray = rows2.get(0).text().split(" ");

        if (stockArray.length < 3) {
            throw new ResultException(Result.ReturnCode.PARAMETER_ERROR, "적합한 종목명이 아닙니다 : " + stockName);
        }

        for (Element row : rows2) {
            String[] stockArray2 = row.text().split(" ");
            if (stockName.equals(stockArray2[1])) {
                return stockArray2[0].replace("A", "");
            }
        }

        throw new ResultException(Result.ReturnCode.PARAMETER_ERROR, "적합한 종목명이 아닙니다 : " + stockName);
    }
}
