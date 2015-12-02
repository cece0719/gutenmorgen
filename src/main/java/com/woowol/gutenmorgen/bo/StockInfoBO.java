package com.woowol.gutenmorgen.bo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class StockInfoBO {
    public String getSimpleStockInfoText(String code) throws IOException {
        Document doc = Jsoup.connect("http://finance.naver.com/search/searchList.nhn?query=" + code).get();
        Elements rows = doc.select("table.tbl_search>tbody>tr>td");

        String stockName = rows.get(0).text();
        String currentPrice = rows.get(1).text();
        String vsYesterdayPrice = rows.get(2).text();
        String vsYesterdayPercent = rows.get(3).text();

        StringBuilder sb = new StringBuilder();
        sb.append(vsYesterdayPercent.startsWith("-")?"패!배!의!":"승!리!의!");
        for (int i=0; i < stockName.length(); i++) {
            sb.append(stockName.substring(i, i+1));
            sb.append("!");
        }
        sb.append(" ");
        sb.append(currentPrice);
        sb.append(" ");
        sb.append(vsYesterdayPercent.startsWith("-")?"↓":"↑");
        sb.append(vsYesterdayPrice);
        sb.append(" ");
        sb.append(vsYesterdayPercent);
        return sb.toString();
    }
}
