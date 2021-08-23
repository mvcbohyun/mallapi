package com.kinsight.email;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.kinsight.exception.BaseException;
import com.kinsight.exception.BaseResponseCode;
import com.kinsight.repository.UserRepository;

@Component
public class InsertEmailUtillmpl implements EmailUtil {

	@Autowired
	private JavaMailSender sender;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public void sendEmail(String toAddress, String subject, String body,String gubun) {
		
		if(!mailtimechk(toAddress)&& gubun !="insert") {
			  throw new BaseException(BaseResponseCode.MAILTIME);
		}
		 MimeMessage message = sender.createMimeMessage();
		 MimeMessageHelper helper = new MimeMessageHelper(message);
		    try {
		      helper.setTo(toAddress);
		      helper.setSubject(subject);
		      helper.setText(body);
		    } catch (MessagingException e) {
		      e.printStackTrace();
		    }
		   
		    sender.send(message);
		    
		    userRepository.updatemailpwtime(toAddress);
	}


	private boolean mailtimechk(String email) {
		System.out.println("mailtimechk");
		String mailpwtimechk =userRepository.mailpwtime(email);
		
		mailpwtimechk =mailpwtimechk.substring(0,mailpwtimechk.length()-2);
		System.out.println(mailpwtimechk.substring(0,mailpwtimechk.length()-2));
		System.out.println("111111111"+ mailpwtimechk);
		if(mailpwtimechk!=null) {
		LocalDateTime time =LocalDateTime.parse(mailpwtimechk, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		return time.isBefore(LocalDateTime.now().minusHours(1));
		}else {
		return true;
		}
	}
}
