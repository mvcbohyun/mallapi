package com.kinsight.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kinsight.bean.BoardReturnBean;
import com.kinsight.bean.CommentsReturnBean;
import com.kinsight.domain.MySQLPageRequest;
import com.kinsight.domain.PageRequestParameter;
import com.kinsight.exception.BaseException;
import com.kinsight.exception.BaseResponseCode;
import com.kinsight.http.BaseResponse;
import com.kinsight.param.BoardSearchParam;
import com.kinsight.param.CommentsSaveParam;
import com.kinsight.param.CommentsSearchParam;
import com.kinsight.service.CommentsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/comments")
@Slf4j
@RequiredArgsConstructor
public class RestCommentController {

	private final CommentsService commentsService;
	
	@PostMapping("/save")
	public BaseResponse<String>  save(@Valid CommentsSaveParam commentsSaveParam, BindingResult result){
		log.info("comments save");
		log.info(commentsSaveParam.toString());
		
		commentsService.save(commentsSaveParam);
		
		return  new BaseResponse<String>(BaseResponseCode.SUCCESS,"저장"); 
	}
	
	@PostMapping("/commentsave")
	public BaseResponse<String>  commentsave(@Valid CommentsSaveParam commentsSaveParam , BindingResult result){
		System.out.println(commentsSaveParam.toString());
		
		
		int returnchk = commentsService.commentsave(commentsSaveParam);
		if(returnchk == 0) {
			throw new BaseException(BaseResponseCode.DATA_IS_NULL);
	
		}
		
		return  new BaseResponse<String>(BaseResponseCode.SUCCESS,"저장"); 
	}
	
	@GetMapping("/search")
	public BaseResponse<List<CommentsReturnBean>>  search(CommentsSearchParam parameter
														 ,MySQLPageRequest pageRequest ){
		PageRequestParameter<CommentsSearchParam> pageRequestParameter = new PageRequestParameter<CommentsSearchParam>(pageRequest, parameter);
		List<CommentsReturnBean> result =  commentsService.search(pageRequestParameter);
		
		return  new BaseResponse<List<CommentsReturnBean>> (result); 
	}
	
	@PostMapping("/delete")
	public  BaseResponse<?>  delete (@RequestParam Long id ,  @RequestParam Long saveid){
		
		if( id == null || id < 0 ) {
			  throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] {"id","댓글 아이디"});
		}
		if( saveid == null || saveid < 0 ) {
			  throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] {"saveid","작성자 아이디"});
		}
		int returnchk  = commentsService.delete(id,saveid);
		if(returnchk == 0) {
			throw new BaseException(BaseResponseCode.DATA_IS_NULL);
		}
		return  new BaseResponse<String>(BaseResponseCode.SUCCESS,"삭제 되었습니다."); 

	}
	
	
	
}
