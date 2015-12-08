package com.woowol.gutenmorgen.util;

import com.woowol.gutenmorgen.exception.ResultException;
import com.woowol.gutenmorgen.model.Result;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
public class Validate {
    private static Environment env;

    @Autowired
    public Validate(Environment env) {
        Validate.env = env;
    }

    public static void checkNotLocal() {
        for (String profile : env.getActiveProfiles()) {
            if ("local".equals(profile)) {
                throw new ResultException(Result.ReturnCode.ENVIRONMENT_ERROR);
            }
        }
    }

    public static void checkTimeRegex(String timeRegex) {
        try {
            //noinspection ResultOfMethodCallIgnored
            (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEE", new Locale("ko", "KR")).format(new Date())).matches(timeRegex);
        } catch (Exception e) {
            throw new ResultException(Result.ReturnCode.TIMEREGEX_ERROR);
        }
    }

    public static void checkNotEmpty(Object object, String parameterName) {
        if (object == null) {
            throw new ResultException(Result.ReturnCode.PARAMETER_ERROR, parameterName);
        }
        if (object instanceof Collection && ((Collection) object).size() == 0) {
            throw new ResultException(Result.ReturnCode.PARAMETER_ERROR, parameterName);
        }
        if (object instanceof String && ((String) object).length() == 0) {
            throw new ResultException(Result.ReturnCode.PARAMETER_ERROR, parameterName);
        }
    }

    public static void checkStockName(List<String> stockNameList) {
        checkStockName(stockNameList.toArray(new String[stockNameList.size()]));
    }

    public static void checkStockName(String... stockNameArray) {
        for (String stockName : stockNameArray) {
            checkStockName(stockName);
        }
    }

    public static void checkStockName(String stockName) {
        try {
            Document doc1 = Jsoup.connect("http://www.krx.co.kr/por_kor/popup/JHPKOR13008_12.jsp?market_gubun=allVal&mkt_typ=S&word=" + URLEncoder.encode(stockName, "UTF-8")).get();
            Elements rows2 = doc1.select("table#tbl1>tbody>tr");

            String[] stockArray = rows2.get(0).text().split(" ");

            if (stockArray.length < 3) {
                throw new ResultException(Result.ReturnCode.PARAMETER_ERROR, "적합한 종목명이 아닙니다 : " + stockName);
            }

            for (Element row : rows2) {
                String[] stockArray2 = row.text().split(" ");
                if (stockName.equals(stockArray2[1])) {
                    return;
                }
            }
            throw new ResultException(Result.ReturnCode.PARAMETER_ERROR, "적합한 종목명이 아닙니다 : " + stockName);
        } catch (IOException e) {
            throw new ResultException(e);
        }
    }

    public static void checkEmailAddr(List<String> emailList) {
        checkEmailAddr(emailList.toArray(new String[emailList.size()]));
    }

    public static void checkEmailAddr(String... emailArray) {
        for (String email : emailArray) {
            checkEmailAddr(email);
        }
    }

    public static void checkEmailAddr(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            throw new ResultException(Result.ReturnCode.PARAMETER_ERROR, "잘못된 메일주소 입니다 : " + email);
        }
    }

    public static void checkMobileNo(List<String> mobileList) {
        checkMobileNo(mobileList.toArray(new String[mobileList.size()]));
    }

    public static void checkMobileNo(String... mobileArray) {
        for (String mobileNo : mobileArray) {
            checkMobileNo(mobileNo);
        }
    }

    private static void checkMobileNo(String mobile) {
        if (StringUtils.isEmpty(mobile) || mobile.matches("^01(?:0|1[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$")) {
            throw new ResultException(Result.ReturnCode.PARAMETER_ERROR, "잘못된 휴대폰번호 입니다 : " + mobile);
        }
    }

    public static void checkSmsMessageLength(String message) {
        try {
            if (message.getBytes("EUC-KR").length > 2000) {
                throw new ResultException(Result.ReturnCode.SMS_MESSAGE_LENGTH_ERROR);
            }
        } catch (UnsupportedEncodingException e) {
            throw new ResultException(e);
        }
    }

    public static void checkSmsSubjetLength(String subject) {
        try {
            if (subject.getBytes("EUC-KR").length > 40) {
                throw new ResultException(Result.ReturnCode.SMS_MESSAGE_LENGTH_ERROR);
            }
        } catch (UnsupportedEncodingException e) {
            throw new ResultException(e);
        }
    }
}