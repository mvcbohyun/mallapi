package com.kinsight.param;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.kinsight.domain.Board;
import com.kinsight.domain.Contents;

import lombok.Data;

@Data
public class ContentsSaveParam {

	private Long id;
	@NotEmpty
	@Size(max = 200)
	private String title;
	@NotEmpty
	@Size(max = 2000)
	private String content;
	@NotNull
	private Integer price;
	@NotNull
	private MultipartFile uploadfile;
	@NotNull
	private Long saveid;
	@NotNull
	private Long categorylid;
	@NotNull
	private Long categorymid;
	@NotNull
	private boolean coninout;
	
	
	
	public Contents saveEntity(ContentsSaveParam contentsSaveParam) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strNowDate = simpleDateFormat.format(new Date());
		return Contents.builder().title(contentsSaveParam.getTitle())
		  .content(contentsSaveParam.getContent())
		  .price(contentsSaveParam.getPrice())
		  .categorylid(contentsSaveParam.getCategorylid())
		  .categorymid(contentsSaveParam.getCategorymid())
		  .coninout(contentsSaveParam.isConinout())
		  .inid(contentsSaveParam.getSaveid())
		  .indate(strNowDate)
		  .updid(contentsSaveParam.getSaveid())
		  .upddate(strNowDate)
		  .build();
	}
	
	public Contents updateEntity(ContentsSaveParam contentsSaveParam) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strNowDate = simpleDateFormat.format(new Date());
		return Contents.builder()
		  .id(contentsSaveParam.getId())
		  .title(contentsSaveParam.getTitle())
		  .content(contentsSaveParam.getContent())
		  .price(contentsSaveParam.getPrice())
		  .categorylid(contentsSaveParam.getCategorylid())
		  .categorymid(contentsSaveParam.getCategorymid())
		  .coninout(contentsSaveParam.isConinout())
		  .updid(contentsSaveParam.getSaveid())
		  .upddate(strNowDate)
		  .build();
	}
}
