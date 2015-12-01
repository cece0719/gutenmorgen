package com.woowol.gutenmorgen.bo;

import org.springframework.stereotype.Service;

@Service
public class SendSmsBO {
    public void sendSms(String mobile, String content) {
        String api_id = "cece0719";		// sms.gabia.com 이용 ID
        String api_key = "07f95a4675dfe956345f08033dcb6466";	// 환결설정에서 확인 가능한 SMS API KEY

        ApiClass api = new ApiClass(api_id, api_key);

        byte[] b = content.getBytes();
        content = new String(b, 0, Math.max(80, b.length-1));
        String arr[] = new String[7];
        arr[0] = "sms";								// 발송 타입 sms or lms
        arr[1] = String.valueOf(System.currentTimeMillis());// 결과 확인을 위한 KEY ( 중복되지 않도록 생성하여 전달해 주시기 바랍니다. )
        arr[2] = "_TITLE_";							//  LMS 발송시 제목으로 사용 SMS 발송시는 수신자에게 내용이 보이지 않음.
        arr[3] = content;					// 본문 (90byte 제한)
        arr[4] = "01045164656";			// 발신 번호
        arr[5] = mobile;				// 수신 번호
        arr[6] = "0";									//예약 일자 "2013-07-30 12:00:00" 또는 "0" 0또는 빈값(null)은 즉시 발송

        String responseXml = api.send(arr);
        ApiResult res = api.getResult( responseXml );

        if( !res.getCode().equals("0000")) {
            //실패
        }
    }
}
