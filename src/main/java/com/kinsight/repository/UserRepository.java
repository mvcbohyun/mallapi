package com.kinsight.repository;

import java.time.LocalDateTime;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kinsight.bean.UserPhonenumberUpdateBean;
import com.kinsight.bean.UserSMSAdressReturn;
import com.kinsight.domain.User;
import com.kinsight.param.AddressSaveParam;
import com.kinsight.param.UserLoginParam;

@Repository
public interface UserRepository {

	int insert(User user);
	
	User login(UserLoginParam userLoginParam);

	User findemail(String email);
	
	User finduser(String email);
	
	String findusernickname(Long userid);

	int mailchk(String mailkey);

	int userDelete(String email);

	int sendpwmail(@Param("email") String email, @Param("newpwkey")String newpwkey);

	int newpwchk(String newpwkey);

	int newpwchange(@Param("email")String email, @Param("password") String password);

	void newpwcolumnchange(String email);

	String findemailkey(String email);

	String mailpwtime(String email);

	void updatemailpwtime(String email  );
	
	int nicknamechange(@Param("email") String email, @Param("nickname")String nickname);

	int newpwchangelogin(@Param("email")String email, @Param("password") String password);

	void userphonenumberupdate(UserPhonenumberUpdateBean userPhonenumberUpdateBean);

	int userphonenumberchk(UserPhonenumberUpdateBean userPhonenumberUpdateBean);

	int smschk(UserPhonenumberUpdateBean userPhonenumberUpdateBean);

	UserSMSAdressReturn smsaddresschk(Long userid);

	int addresssave(AddressSaveParam addressSaveParam);

	int primaryaddresssave(Map<String, Object> parammap);
	
}
