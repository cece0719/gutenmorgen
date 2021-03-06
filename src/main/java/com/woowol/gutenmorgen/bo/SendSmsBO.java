package com.woowol.gutenmorgen.bo;

import com.woowol.gutenmorgen.util.Validate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class SendSmsBO {
    private final static String apiUrl = "http://sms.gabia.com/api";
    private final static String methodName = "gabiasms";

    @Value("${sendsms.gabia.id}")
    private String smsId;
    @Value("${sendsms.gabia.callback}")
    private String callback;
    @Value("${sendsms.gabia.apikey}")
    private String apiKey;

    private XmlRpcClient xmlClient;
    private Random random = new Random();

    public SendSmsBO() throws MalformedURLException {
        XmlRpcClientConfigImpl xmlClientConfig = new XmlRpcClientConfigImpl();
        xmlClientConfig.setServerURL(new URL(apiUrl));
        xmlClient = new XmlRpcClient();
        xmlClient.setConfig(xmlClientConfig);
    }

    public void sendSms(List<String> mobileList, String subject, String message) throws Exception {
        for (String mobile : mobileList) {
            sendSms(mobile, subject, message);
        }
    }

    public void sendSms(List<String> mobileList, String message) throws Exception {
        for (String mobile : mobileList) {
            sendSms(mobile, message);
        }
    }

    public void sendSms(String mobile, String message) throws Exception {
        sendSms(mobile, "GUTEN MORGEN", message);
    }

    public void sendSms(String mobile, String subject, String message) throws Exception {
        Validate.checkSmsSubjectLength(subject);
        Validate.checkSmsMessageLength(message);
        Validate.checkMobileNo(mobile);

        String sendType = (message.getBytes("EUC-KR").length > 90)?"lms":"sms";                                // 발송 타입 sms or lms
        String refKey = String.valueOf(System.currentTimeMillis());// 결과 확인을 위한 KEY ( 중복되지 않도록 생성하여 전달해 주시기 바랍니다. )
        String reserve = "0";                                    //예약 일자 "2013-07-30 12:00:00" 또는 "0" 0또는 빈값(null)은 즉시 발송

        subject = StringEscapeUtils.escapeXml11(subject);
        message = StringEscapeUtils.escapeXml11(message);

        mobile = mobile.replace("-", "").replace(" ", "");
        String sb = "<request>" +
                "<sms-id>" + smsId + "</sms-id>" +
                "<access-token>" + getMd5AccessToken() + "</access-token>" +
                "<response-format>xml</response-format>" +
                "<method>SMS.send</method>" +
                "<params>" +
                "<send_type>" + sendType + "</send_type>" +
                "<ref_key>" + refKey + "</ref_key>" +
                "<subject>" + subject + "</subject>" +
                "<message>" + message + "</message>" +
                "<callback>" + callback + "</callback>" +
                "<phone>" + mobile + "</phone>" +
                "<reserve>" + reserve + "</reserve>" +
                "</params>" +
                "</request>";
        callMethod(sb);
    }

    private void callMethod(String s) throws Exception {
        Object[] params = new Object[]{s};
        String result = (String) xmlClient.execute(methodName, params);
        Element tree = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(result))).getDocumentElement();

        String code = tree.getElementsByTagName("code").item(0).getFirstChild().getNodeValue();
        String msg = tree.getElementsByTagName("mesg").item(0).getFirstChild().getNodeValue();

        if (!code.equals("0000")) {
            throw new Exception(String.format("gabia send sms fail[%s]%s", code, msg));
        }
    }

    private String getMd5AccessToken() throws NoSuchAlgorithmException {
        String base62CharacterArray[] = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,0,1,2,3,4,5,6,7,8,9".split(",");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(base62CharacterArray[random.nextInt(base62CharacterArray.length)]);
        }
        String nonce = sb.toString();

        return nonce + getMd5(nonce);
    }

    private String getMd5(String nonce) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update((nonce + apiKey).getBytes());
        byte[] md5Code = md.digest();

        StringBuilder sb = new StringBuilder();
        for (byte aByte : md5Code) {
            sb.append(String.format("%02x", 0xff & (char) aByte));
        }

        return sb.toString();
    }
}
