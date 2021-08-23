package com.kinsight.controller;


import java.util.UUID;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kinsight.bean.LoginUserBean;
import com.kinsight.bean.LoginUserBean2;
import com.kinsight.bean.UserSMSAdressReturn;
import com.kinsight.domain.User;
import com.kinsight.exception.BaseException;
import com.kinsight.exception.BaseResponseCode;
import com.kinsight.http.BaseResponse;
import com.kinsight.jwt.JwtFilter;
import com.kinsight.jwt.TokenProvider;
import com.kinsight.param.UserSaveParam;
import com.kinsight.param.AddressSaveParam;
import com.kinsight.param.UserLoginParam;
import com.kinsight.repository.UserRepository;
import com.kinsight.service.SMSService;
import com.kinsight.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class RestUserController {

	private final UserService userService;
	private final UserRepository userRepository;
	private final TokenProvider tokenProvider;
	private final SMSService smsService;


	private final BCryptPasswordEncoder endcoder = new BCryptPasswordEncoder();

	// 로그인
	@PostMapping("/login") 
	public BaseResponse<LoginUserBean2> login(@Valid UserLoginParam userLoginParam , BindingResult result) 
	{ 
	


	String userpassword = userLoginParam.getPassword(); 
	User loginUser =userService.login(userLoginParam);
	if(loginUser != null) {

		if(endcoder.matches(userpassword, loginUser.getPassword())) {
			System.out.println("111111111111111111");
			LoginUserBean user1 = new LoginUserBean(loginUser.getEmail() , loginUser.getPassword()); 
			String jwt =tokenProvider.createToken(user1); log.info(jwt); 
			HttpHeaders httpHeaders =new HttpHeaders(); 
			httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer "+ jwt);
			return new BaseResponse<>(new LoginUserBean2(loginUser.getId(),loginUser.getEmail(),loginUser.getNickname(),loginUser.isMailyn(),jwt,loginUser.getRole()),"로그인 되었습니다."); 

		}else {
			throw new BaseException(BaseResponseCode.ERROR);
		}
	}else {
		throw new BaseException(BaseResponseCode.ERROR);
	} 
	}





	// 회원가입
	@PostMapping(value ="/insert", produces = "application/json; charset=utf8")
	public BaseResponse<String> insert( @Valid UserSaveParam userSaveParam , BindingResult result) {
		log.info("user  insert");


//		// 값 필수 체크
//		if(StringUtils.isEmpty(userSaveParam.getEmail())) {
//			throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] {"email","이메일"});
//		}
//		if(StringUtils.isEmpty(userSaveParam.getPassword())) {
//			throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] {"password","비밀번호"});
//		}
//		if(StringUtils.isEmpty(userSaveParam.getNickname())) {
//			throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] {"nickname","닉네임"});
//		}


		int insertchk=0;

		insertchk =userService.insert(userSaveParam);
		if(insertchk==1) {
			return  new BaseResponse<String>(BaseResponseCode.SUCCESS,"회원 가입이 완료되었습니다.\r 메일 인증 해주시기 바랍니다."); 
		}else if(insertchk==99) {
			throw new BaseException(BaseResponseCode.USERCHK);
		}else {
			throw new BaseException(BaseResponseCode.ERROR);
		}

	}
	//메일 재전송
	@GetMapping("/sendmail")
	public BaseResponse<String> sendmail(@RequestParam String email) {
		log.info("user  sendmail");
		// 값 필수 체크
		if(StringUtils.isEmpty(email)) {
			throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] {"email","이메일"});
		}



		userService.sendmail(email,"x","");
		return  new BaseResponse<>(BaseResponseCode.SUCCESS,"메일이 재전송 되었습니다.\r 메일 인증 해주시기 바랍니다."); 
	}
	//메일 인증
	@PostMapping("/mailchk")
	public BaseResponse<String> mailchk(@RequestParam String mailkey) {
		log.info("user  mailchk");
		// 값 필수 체크
		if(StringUtils.isEmpty(mailkey)) {
			throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] {"mailkey","이메일 인증키"});
		}
		int mailchk = 0;
		mailchk = userService.mailchk(mailkey);
		if(mailchk==1) {
			return  new BaseResponse<>(BaseResponseCode.SUCCESS,"메일이 인증되었습니다. \r 로그인을 진행해주시기 바랍니다."); 
		}else {
			throw new BaseException(BaseResponseCode.MAILCHKERROR);
		}
	}


	//비밀번호 인증메일 전송
	@GetMapping("/sendpwmail")
	public BaseResponse<String> sendpwmail(@RequestParam String email) {
		log.info("user  sendpwmail");
		// 값 필수 체크
		if(StringUtils.isEmpty(email)) {
			throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] {"email","이메일"});
		}
		User user =userRepository.finduser(email);
		if(user!=null) {
			String mailkey = UUID.randomUUID().toString();
			System.out.println(email+ "   "+ mailkey);
			userService.sendpwmail(email,mailkey);
			return  new BaseResponse<>(BaseResponseCode.SUCCESS,"비밀번호 인증 메일이 되었습니다.\r 메일 인증 해주시기 바랍니다."); 
		}else {
			throw new BaseException(BaseResponseCode.NOUSER);
		}
	}

	//비밀번호 메일키 인증
	@PostMapping("/newpwchk")
	public BaseResponse<String> newpwchk(@RequestParam String newpwkey) {
		log.info("user  newpwchk");
		System.out.println("11111111111111");
		// 값 필수 체크
		if(StringUtils.isEmpty(newpwkey)) {
			throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] {"newpwkey","비밀번호 인증키"});
		}
		int mailchk = 0;
		mailchk = userService.newpwchk(newpwkey);
		if(mailchk==1) {
			return  new BaseResponse<>(BaseResponseCode.SUCCESS,"메일이 인증되었습니다. \r 새로운 비밀번호를 작성 하시기 바랍니다."); 
		}else {
			throw new BaseException(BaseResponseCode.MAILCHKERROR);
		}
	}
	//비밀번호 변경 
	@PostMapping("/newpwchange")
	public BaseResponse<String> newpwchange(@RequestParam String email , @RequestParam String password) {
		log.info("user  newpwchange");
		// 값 필수 체크
		if(StringUtils.isEmpty(email)) {
			throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] {"email","이메일"});
		}
		if(StringUtils.isEmpty(password)) {
			throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] {"password","비밀번호"});
		}

		int pwchangechk = 0;
		pwchangechk = userService.newpwchange(email,password,"notlogin");
		if(pwchangechk==1) {
			return  new BaseResponse<>(BaseResponseCode.SUCCESS,"비밀번호가 변경되었습니다. \r 로그인 하시기 바랍니다."); 
		}else {
			throw new BaseException(BaseResponseCode.ERROR);
		}
	}


	//로그인 비밀번호 변경 
	@PostMapping("/newpwchangelogin")
	public BaseResponse<String> newpwchange_login(@RequestParam String email , @RequestParam String password) {
		log.info("user  newpwchangelogin");
		// 값 필수 체크
		if(StringUtils.isEmpty(email)) {
			throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] {"email","이메일"});
		}
		if(StringUtils.isEmpty(password)) {
			throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] {"password","비밀번호"});
		}
		
		int pwchangechk = 0;
		pwchangechk = userService.newpwchange(email,password,"login");
		if(pwchangechk==1) {
			return  new BaseResponse<>(BaseResponseCode.SUCCESS,"비밀번호가 변경되었습니다. \r 로그인 하시기 바랍니다."); 
		}else {
			throw new BaseException(BaseResponseCode.ERROR);
		}
	}
	//닉네임 변경
	@PostMapping("/newnickname")
	public BaseResponse<String> newnickname(@RequestParam String email ,@RequestParam String nickname){
		log.info("user  newnickname");
		// 값 필수 체크
		if(StringUtils.isEmpty(email)) {
			throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] {"email","이메일"});
		}
		if(StringUtils.isEmpty(nickname)) {
			throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] {"nickname","닉네임"});
		}
		
		int nicknamechangechk =0;

		nicknamechangechk = userRepository.nicknamechange(email, nickname)	;


		if(nicknamechangechk==1) {
			return  new BaseResponse<>(BaseResponseCode.SUCCESS,"닉네임이 변경되었습니다."); 
		}else {
			throw new BaseException(BaseResponseCode.ERROR);
		}

	}
	
	@PostMapping("/smschksend")
	public BaseResponse<?> smschksend(@RequestParam Long userid , @RequestParam String phonenumber){
		
		smsService.smschksend(userid,phonenumber);
		
		return  new BaseResponse<>(BaseResponseCode.SUCCESS,"인증번호가 발송되었습니다"); 
	}
	
	
	@PostMapping("/smschk")
	public BaseResponse<?> smschk(@RequestParam Long userid , @RequestParam String phonekey){
		
		smsService.smschk(userid,phonekey);
		
		return  new BaseResponse<>(BaseResponseCode.SUCCESS,"인증되었습니다."); 
	}
	
	@GetMapping("/smsaddresschk")
	public BaseResponse<?> smsaddresschk(@RequestParam Long userid  ){
		
		UserSMSAdressReturn userSMSAdressReturn= userService.smsaddresschk(userid);
		
		if(userSMSAdressReturn==null) {
			throw new BaseException(BaseResponseCode.ERROR);
		}
		
		return  new BaseResponse<>(userSMSAdressReturn); 
	}
	
	@PostMapping("/addresssave")
	public BaseResponse<?> addresssave(@Valid AddressSaveParam addressSaveParam ,BindingResult result){
		
		userService.addresssave(addressSaveParam);
		
		
		return  new BaseResponse<>(BaseResponseCode.SUCCESS,"저장되었습니다."); 
	}
	
	
	@PostMapping("/primaryaddresssave")
	public BaseResponse<?> primaryaddresssave(@RequestParam Long userid,@RequestParam String gubun){
		
		userService.primaryaddresssave(userid,gubun);
		
		
		return  new BaseResponse<>(BaseResponseCode.SUCCESS,"저장되었습니다."); 
	}
	




}
