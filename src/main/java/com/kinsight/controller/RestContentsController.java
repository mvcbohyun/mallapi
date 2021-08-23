package com.kinsight.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kinsight.bean.BoardReturnBean;
import com.kinsight.bean.CategoryReturnBean;
import com.kinsight.bean.ContentsDetailReturnBean;
import com.kinsight.bean.ContentsReturnBean;
import com.kinsight.domain.MySQLPageRequest;
import com.kinsight.domain.PageRequestParameter;
import com.kinsight.exception.BaseException;
import com.kinsight.exception.BaseResponseCode;
import com.kinsight.http.BaseResponse;
import com.kinsight.param.ContentsSaveParam;
import com.kinsight.param.ContentsSearchParam;
import com.kinsight.param.LikesParam;
import com.kinsight.repository.ContentsRepository;
import com.kinsight.service.ContentsService;
import com.kinsight.service.LikesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/contents")
@RequiredArgsConstructor
public class RestContentsController {

	private final ContentsService contentsService; 
	private final LikesService likesService;
	private final ContentsRepository contentsRepository;
	@GetMapping("/search")
	public BaseResponse<?>  search(ContentsSearchParam parameter,
														 MySQLPageRequest pageRequest ){
		System.out.println("parameter =" +parameter);
		System.out.println("pageRequest"+pageRequest);
		PageRequestParameter<ContentsSearchParam> pageRequestParameter = new PageRequestParameter<ContentsSearchParam>(pageRequest, parameter);
		return new BaseResponse<List<ContentsReturnBean>>( contentsService.search(pageRequestParameter)); 
	
	}
	
	@GetMapping("/detail")
	public BaseResponse<?>  detail(Long id){
		
	
		
		return new BaseResponse<ContentsDetailReturnBean>( contentsService.detail(id)); 
	
	}
	
	@PostMapping("/save")
	public BaseResponse<?>  save(@Valid ContentsSaveParam contentsSaveParam, BindingResult result) {
		System.out.println("contentsSaveParam"+contentsSaveParam); 
		
			
		
		
		Long returnchk =contentsService.save(contentsSaveParam);
		if(returnchk == 0) {
			throw new BaseException(BaseResponseCode.DATA_IS_NULL);
	
		}
		
		return  new BaseResponse<Long>(returnchk,"저장되었습니다.");
		
		
	}
	
	@PostMapping("/delete")
	public BaseResponse<?>  delete(Long id,Long saveid) {
		if( id == null || id < 0 ) {
			  throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] {"id","컨텐츠 아이디"});
		}
		if( saveid == null || saveid < 0 ) {
			  throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] {"saveid","작성자 아이디"});
		}
		
		contentsService.delete(id , saveid);
		
		
		return  new BaseResponse<>("삭제되었습니다.");
		
		
	}
	
	@PostMapping("/like")
	public BaseResponse<?>  like(LikesParam likeParam){
		likesService.like(likeParam);
		
		return  new BaseResponse<>("성공");
	}
	
	@PostMapping("/unlike")
	public BaseResponse<?>  unlike(LikesParam likeParam){
		likesService.unlike(likeParam);
		
		return  new BaseResponse<>("성공");
	}
	@GetMapping("/category")
	public BaseResponse<?> category() {
		
		return  new BaseResponse<List<CategoryReturnBean>>(contentsRepository.category());
	}
	

	
	
}
