package com.kinsight.param;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.kinsight.domain.Board;

import lombok.Data;

@Data
public class BoardSaveParam {
	@NotNull
	private Long saveid;
	@NotEmpty
	@Size(max = 100)
	private String title;
	@NotEmpty
	@Size(max = 3000)
	private String content;
	@NotNull
	private Long categoryid;
	private List<MultipartFile> uploadfile;
	//private String uploadfile;
	private Long boardid;
	@NotNull
	private boolean secret;
	
	public Board saveEntity(BoardSaveParam boardSaveParam) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strNowDate = simpleDateFormat.format(new Date());
		return Board.builder().title(boardSaveParam.getTitle())
									 .content(boardSaveParam.getContent())
									 .enabled(true)
									 .indate(strNowDate)
									 .inid(boardSaveParam.getSaveid())
									 .upddate(strNowDate)
									 .updid(boardSaveParam.getSaveid())
									 .categoryid(boardSaveParam.getCategoryid())
									 .secret(boardSaveParam.isSecret())
									 .build();	
	}
	
	public Board updateEntity(BoardSaveParam boardSaveParam) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strNowDate = simpleDateFormat.format(new Date());
		return Board.builder().id( boardSaveParam.getBoardid())
							.title(boardSaveParam.getTitle())
									 .content(boardSaveParam.getContent())
									 .enabled(true)
									 .upddate(strNowDate)
									 .updid(boardSaveParam.getSaveid())
									 .categoryid(boardSaveParam.getCategoryid())
									 .secret(boardSaveParam.isSecret())
									 .build();	
	}
}
