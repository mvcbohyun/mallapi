package com.kinsight.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kinsight.bean.UserSMSAdressReturn;
import com.kinsight.domain.User;
import com.kinsight.email.EmailUtil;
import com.kinsight.exception.BaseException;
import com.kinsight.exception.BaseResponseCode;
import com.kinsight.param.UserSaveParam;
import com.kinsight.param.AddressSaveParam;
import com.kinsight.param.UserLoginParam;
import com.kinsight.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

	private final UserRepository userRepository;
	private final EmailUtil		emailUtil;
	private final PasswordEncoder passwordEncoder;
	
	
	public int  insert(UserSaveParam userSaveParam ) {
	
		User chkUser = userRepository.findemail(userSaveParam.getEmail());
		
		if(chkUser ==null) {
		
			String mailkey = UUID.randomUUID().toString();
			String encodedPassword= passwordEncoder.encode(userSaveParam.getPassword()) ;
			int insertchk = 0;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String strNowDate = simpleDateFormat.format(new Date());
			
			User user = User.builder().email(userSaveParam.getEmail())
									  .nickname(userSaveParam.getNickname())
									  .password(encodedPassword)
									  .enabled(true)
									  .joindate(strNowDate)
									  .mailkey(mailkey)
									  .mailyn(false)
									  .newpwkey("")
									  .newpwyn(false)
									  .mailpwtime(LocalDateTime.now())
									  .role("ROLE_USER")
									  .build();
			
			log.info(encodedPassword);
			insertchk =userRepository.insert(user);
			    if (insertchk==1) {
			    
			    	// 사용자 생성 및 인증 이메일 보냄
			    	sendmail(user.getEmail(),mailkey,"insert");
			    	return insertchk;
			    }else{
			    	// 사용자 생성이 안됨 
			    	return 0;
			    }
		}else {
			// 메일인증이 안된아이디가 있는경우 삭제후 다시 insert 
			System.out.println(chkUser.isMailyn());
			if(chkUser.isMailyn() ==false) {
				userRepository.userDelete(chkUser.getEmail());
				insert(userSaveParam);
				return 1;
			}
			
			// 이미 사용중인 아이디인 경우 
			return 99;
			
		}
	   
		
	}
	public User login(UserLoginParam userLoginParam) {
		
		User loginUser = userRepository.login(userLoginParam);
		return loginUser;
	}
	
	public void sendmail(String email, String mailkey , String gubun ) {
		if(mailkey == "x") {
		 mailkey =userRepository.findemailkey(email);
		}
		emailUtil.sendEmail(email, "메일 인증", "메일 인증키 = "+ mailkey,gubun);
	}
	
	public int mailchk(String mailkey) {
		
		return userRepository.mailchk(mailkey);
		
	}
	public int newpwchk(String newpwkey) {
		
		return userRepository.newpwchk(newpwkey);
	}
	public void sendpwmail(String email, String mailkey) {
		
		User chkuser = userRepository.findemail(email);
		System.out.println(chkuser.getEmail());
		if(chkuser != null ) {
		userRepository.sendpwmail(email,mailkey);
		emailUtil.sendEmail(email, "메일 인증", "메일 인증키 = "+ mailkey,"");
		}
	}
	public int newpwchange(String email, String password,String logingubun) {
		int pwchangechk = 0;
		String encodedPassword=""; 
		encodedPassword = passwordEncoder.encode(password);
		if(logingubun != "login") {
		
			pwchangechk = userRepository.newpwchange(email,encodedPassword);
			if(pwchangechk>0) {
				userRepository.newpwcolumnchange(email);
			}
		}else {
			pwchangechk = userRepository.newpwchangelogin(email,encodedPassword);
			
		}
		return pwchangechk;
	}
	public UserSMSAdressReturn smsaddresschk(Long userid) {
		
		return userRepository.smsaddresschk(userid);
		
	}
	public void addresssave(AddressSaveParam addressSaveParam) {
		int result = userRepository.addresssave(addressSaveParam);
		
		if(result<1) {
			throw new BaseException(BaseResponseCode.ERROR,"저장 에러");
		}
		
	}
	public void primaryaddresssave(Long userid, String gubun) {
		Map<String, Object> parammap  = new HashMap<>();
		parammap.put("userid", userid);
		parammap.put("gubun", gubun);
		int result = userRepository.primaryaddresssave(parammap);
		if(result<1) {
			throw new BaseException(BaseResponseCode.ERROR,"저장 에러");
		}
	}

	
	

}
