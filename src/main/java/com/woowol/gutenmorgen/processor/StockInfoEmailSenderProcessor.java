package com.woowol.gutenmorgen.processor;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class StockInfoEmailSenderProcessor implements Processor {

	ObjectMapper mapper = new ObjectMapper();
	
	public static final String LOSE = "패!배!의!";
	public static final String WIN = "승!리!의!";

	@SuppressWarnings("unchecked")
	@Override
	public void process(String parameter) throws Exception {
		Map<String, Object> map = mapper.readValue(parameter, new TypeReference<Map<String, Object>>() {});
		List<String> stockList = (List<String>) map.get("stockList");
		List<String> emailList = (List<String>) map.get("emailList");
		for (String stock : stockList) {
			for (String email : emailList) {
				Sender.sendMail(email, printPrice(stock), "");
			}
		}
	}
	
	private static String printPrice(String code) {
		String result = "";
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet("http://finance.naver.com/search/searchList.nhn?query=" + code);
		try {
			result = httpClient.execute(httpget, new BasicResponseHandler() {
				@Override
				public String handleResponse(HttpResponse response) throws HttpResponseException, IOException {
					String res = new String(super.handleResponse(response).getBytes());
					Document doc = Jsoup.parse(res);
					Elements rows = doc.select("table.tbl_search tbody tr");
					String[] items = new String[] {"종목명", "현재가", "전일대비", "등락율"};
					String[] values = new String[4];

					for (Element row : rows) {
						Iterator<Element> iterElem = row.getElementsByTag("td").iterator();
						for (int i = 0; i < items.length; i++) {
							if (items[i].equals("종목명")) {
								String name = iterElem.next().text();
								String decoName = "";
								for (int j=0; j<name.length(); j++) {
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
		
	private static class Sender {
		static Properties mailServerProperties;
		static Session getMailSession;
		static MimeMessage generateMailMessage;
		
		public static void sendMail(String email, String subject, String content) throws AddressException, MessagingException {
			// Step1
			mailServerProperties = System.getProperties();
			mailServerProperties.put("mail.smtp.port", "587");
			mailServerProperties.put("mail.smtp.auth", "true");
			mailServerProperties.put("mail.smtp.starttls.enable", "true");
	 
			// Step2
			getMailSession = Session.getDefaultInstance(mailServerProperties, null);
			generateMailMessage = new MimeMessage(getMailSession);
			generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			generateMailMessage.setSubject(subject);
			generateMailMessage.setContent(content, "text/html");
	 
			// Step3
			System.out.println("Get Session and Send mail");
			Transport transport = getMailSession.getTransport("smtp");
	 
			// Enter your correct gmail UserID and Password
			// if you have 2FA enabled then provide App Specific Password
			transport.connect("smtp.gmail.com", "smtptestking@gmail.com", "wjdqhfk898!");
			transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
			transport.close();
		}
	}
}
