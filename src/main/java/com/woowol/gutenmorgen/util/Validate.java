package com.woowol.gutenmorgen.util;

import com.woowol.gutenmorgen.exception.ResultException;
import com.woowol.gutenmorgen.model.Result;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Component
public class Validate {
    private static Environment env;

    @Autowired
    public Validate(Environment env) {
        Validate.env = env;
    }

    public static void checkNotLocal() throws ResultException {
        for (String profile : env.getActiveProfiles()) {
            if ("local".equals(profile)) {
                throw new ResultException(Result.ReturnCode.ENVIRONMENT_ERROR);
            }
        }
    }

    public static void checkTimeRegex(String timeRegex) throws ResultException {
        try {
            (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEE", new Locale("ko", "KR")).format(new Date())).matches(timeRegex);
        } catch (Exception e) {
            throw new ResultException(Result.ReturnCode.TIMEREGEX_ERROR);
        }
    }

    private static void checkStockName(String stockName) throws ResultException, IOException {
        Document doc1 = Jsoup.connect("http://www.krx.co.kr/por_kor/popup/JHPKOR13008_12.jsp?market_gubun=allVal&mkt_typ=S&word=" + URLEncoder.encode(stockName, "UTF-8")).get();
        Elements rows2 = doc1.select("table#tbl1>tbody>tr");

        String[] stock = rows2.get(0).text().split(" ");

        if (stockName.equals(stock[1])) {
            return;
        }
        throw new ResultException(Result.ReturnCode.PARAMETER_ERROR, "적합한 종목명이 아닙니다 : " + stockName);
    }
}