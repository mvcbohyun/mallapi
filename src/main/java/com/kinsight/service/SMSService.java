package com.kinsight.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.validation.Valid;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinsight.bean.UserPhonenumberUpdateBean;
import com.kinsight.bean.UserSMSAdressReturn;
import com.kinsight.exception.BaseException;
import com.kinsight.exception.BaseResponseCode;
import com.kinsight.param.AddressSaveParam;
import com.kinsight.properties.SMSProperties;
import com.kinsight.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;

@Service
@RequiredArgsConstructor
public class SMSService {

	private final UserRepository userRepository;
	
	private final SMSProperties smsProperties;
	 /**
     * 6자리 인증키 생성, int 반환
     * @return
     */
    public static int generateAuthNo2() {
        java.util.Random generator = new java.util.Random();
        generator.setSeed(System.currentTimeMillis());
        return generator.nextInt(1000000) % 1000000;
    }
	// 인증 문자 보내기 
	public void smschksend(Long userid, String phonenumber) {
		
		String usernickname  =userRepository.findusernickname(userid);
		String phonekey =Integer.toString( generateAuthNo2());
		if(usernickname==null || usernickname.isEmpty()) {
			throw new BaseException(BaseResponseCode.DATA_IS_NULL);
		}
		
		UserPhonenumberUpdateBean userPhonenumberUpdateBean = new UserPhonenumberUpdateBean();
		userPhonenumberUpdateBean.setUserid(userid);
		userPhonenumberUpdateBean.setPhonenumber(phonenumber);
		userPhonenumberUpdateBean.setPhonekey(phonekey);
		userPhonenumberUpdateBean.setPhoneyn(false);
		
		
	    userRepository.userphonenumberupdate(userPhonenumberUpdateBean);
		
		try{
			
		System.out.println("1111111111111111111111111111111");
		final String encodingType = "utf-8";
		final String boundary = "____boundary____";
	
		/**************** 문자전송하기 예제 ******************/
		/* "result_code":결과코드,"message":결과문구, */
		/* "msg_id":메세지ID,"error_cnt":에러갯수,"success_cnt":성공갯수 */
		/* 동일내용 > 전송용 입니다.  
		/******************** 인증정보 ********************/
		String sms_url = "https://apis.aligo.in/send/"; // 전송요청 URL
		
		Map<String, String> sms = new HashMap<String, String>();
		
		sms.put("user_id", smsProperties.getAligo_id()); // SMS 아이디
		sms.put("key", smsProperties.getAligo_key()); //인증키
		
		/******************** 인증정보 ********************/
		
		/******************** 전송정보 ********************/
		sms.put("msg", "[킨사이트] 인증번호 ["+phonekey+ "]"); // 메세지 내용
		sms.put("receiver", phonenumber); // 수신번호
		sms.put("destination", phonenumber+"|"+usernickname); // 수신인 %고객명% 치환
		sms.put("sender", smsProperties.getAligo_calloutnumber()); // 발신번호
		sms.put("rdate", ""); // 예약일자 - 20161004 : 2016-10-04일기준
		sms.put("rtime", ""); // 예약시간 - 1930 : 오후 7시30분
		sms.put("testmode_yn", ""); // Y 인경우 실제문자 전송X , 자동취소(환불) 처리
		sms.put("title", "인증문자"); //  LMS, MMS 제목 (미입력시 본문중 44Byte 또는 엔터 구분자 첫라인)
		
		String image = "";
		//image = "/tmp/pic_57f358af08cf7_sms_.jpg"; // MMS 이미지 파일 위치
		
		/******************** 전송정보 ********************/
		
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		
		builder.setBoundary(boundary);
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.setCharset(Charset.forName(encodingType));
		
		
		for(Iterator<String> i = sms.keySet().iterator(); i.hasNext();){
			String key = i.next();
			builder.addTextBody(key, sms.get(key)
					, ContentType.create("Multipart/related", encodingType));
		}
		
		File imageFile = new File(image);
		if(image!=null && image.length()>0 && imageFile.exists()){
	
			builder.addPart("image",
					new FileBody(imageFile, ContentType.create("application/octet-stream"),
							URLEncoder.encode(imageFile.getName(), encodingType)));
		}
		
		HttpEntity entity = builder.build();
		
		HttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(sms_url);
		post.setEntity(entity);
		
		HttpResponse res = client.execute(post);
		
		String result = "";
		
		if(res != null){
			BufferedReader in = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), encodingType));
			String buffer = null;
		
//			JSONParser parser = new JSONParser(); 
//			Object obj = parser.parse( in.readLine() ); 
//			JSONObject jsonObj = (JSONObject) obj;
//
//			
//			System.out.println(getMapFromJsonObject(jsonObj).getClass().getFields().);
//	       
			while((buffer = in.readLine())!=null){
				
				result += buffer;
			}
			in.close();
		}
		System.out.println("result :"+result);
		
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw new BaseException(BaseResponseCode.SEND_MESSAGE_ERROR);
		}
		
	}
	public void smschk(Long userid,String phonekey) {
		
		UserPhonenumberUpdateBean userPhonenumberUpdateBean = new UserPhonenumberUpdateBean();
		
		userPhonenumberUpdateBean.setUserid(userid);
		userPhonenumberUpdateBean.setPhonekey(phonekey);
		userPhonenumberUpdateBean.setPhoneyn(true);
		
	    int result = userRepository.smschk(userPhonenumberUpdateBean);
	    
	    if(result<1) {
	    	throw new BaseException(BaseResponseCode.CHK_MESSAGE_ERROR);
	    }
		
		
	}
	
	
//	 /**
//     * JsonObject를 Map<String, String>으로 변환한다.
//     *
//     * @param jsonObj JSONObject.
//     * @return Map<String, Object>.
//     */
//    @SuppressWarnings("unchecked")
//    public static Map<String, Object> getMapFromJsonObject( JSONObject jsonObj )
//    {
//        Map<String, Object> map = null;
//        
//        try {
//            
//            map = new ObjectMapper().readValue(jsonObj.toJSONString(), Map.class) ;
//            
//        } catch (JsonParseException e) {
//            e.printStackTrace();
//        } catch (JsonMappingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
// 
//        return map;
//    }
 
}

