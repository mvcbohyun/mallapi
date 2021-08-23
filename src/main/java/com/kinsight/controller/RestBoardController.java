package com.kinsight.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kinsight.bean.BoardDetailReturnBean;
import com.kinsight.bean.BoardReturnBean;
import com.kinsight.bean.CategoryReturnBean;
import com.kinsight.domain.MySQLPageRequest;
import com.kinsight.domain.PageRequestParameter;
import com.kinsight.exception.BaseException;
import com.kinsight.exception.BaseResponseCode;
import com.kinsight.http.BaseResponse;
import com.kinsight.param.BoardSaveParam;
import com.kinsight.param.BoardSearchParam;
import com.kinsight.repository.BoardRepository;
import com.kinsight.service.BoardService;
import com.kinsight.service.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/board")
@Slf4j
@RequiredArgsConstructor
public class RestBoardController {

	private final BoardService boardService;
	private final BoardRepository boardRepository;
	private final FileService fileService;
	
	
	
	@PostMapping("/search")
	public BaseResponse<List<BoardReturnBean>>  search(BoardSearchParam parameter,
														 MySQLPageRequest pageRequest ){
		
		
		log.info("pageRequest : {}",pageRequest);
		PageRequestParameter<BoardSearchParam> pageRequestParameter = new PageRequestParameter<BoardSearchParam>(pageRequest, parameter);
//		HttpHeaders httpHeaders =new HttpHeaders(); 
//		return new ResponseEntity<>( boardService.search(pageRequestParameter),httpHeaders,HttpStatus.OK); 
		return new BaseResponse<List<BoardReturnBean>>( boardService.search(pageRequestParameter)); 
	
	}
	

	@PostMapping("/detail")
	public BaseResponse<BoardDetailReturnBean>  detail(@RequestParam Long id  ,Long saveid ){
	
			BoardDetailReturnBean detailbean  = boardService.detail(id,saveid);
			if(detailbean == null) {
				throw new BaseException(BaseResponseCode.DATA_IS_NULL);
			}
			return new BaseResponse<>(detailbean); 
	
	}
	
	@PostMapping("/save")
	public BaseResponse<?> save(@Valid BoardSaveParam boardSaveParam, BindingResult result) {
		
		
		Long returnid =boardService.save(boardSaveParam);
		
		return  new BaseResponse<Long>(returnid,"저장되었습니다.");
		//return  new BaseResponse<>(BaseResponseCode.SUCCESS);
	}
	
	@PostMapping("/delete")
	public BaseResponse<BoardDetailReturnBean>  delete(@RequestParam Long id ,  @RequestParam Long saveid ){
		if( id == null || id < 0 ) {
			  throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] {"id","게시판 아이디"});
		}
		if( saveid == null || saveid < 0 ) {
			  throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] {"saveid","작성자 아이디"});
		}
			int returnid  = boardService.delete(id,saveid);
			if(returnid == 0) {
				throw new BaseException(BaseResponseCode.DATA_IS_NULL);
			}
			return  new BaseResponse<>(BaseResponseCode.SUCCESS,"게시물이 삭제되었습니다."); 
	
	}
		
		
			

	@GetMapping("/category")
	public BaseResponse<CategoryReturnBean> category() {
		
		return  new BaseResponse<CategoryReturnBean>(boardRepository.category());
	}
	
	
	@GetMapping("/allcount")
	public BaseResponse<Integer>  allcount(){
		
		
		return  new BaseResponse<>(boardRepository.allcount());
	
	}
	
	
	
	
	
	
	
	
}
