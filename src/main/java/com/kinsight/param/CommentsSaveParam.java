package com.kinsight.param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CommentsSaveParam {

	
	private Long id ;
	private Long commentid;
	@NotNull
	private Long boardid;
	@NotEmpty
	@Size(max = 3000)
	private String content;
	@NotNull
	private Long saveid;
	private String indate;
	private Long inid;
	private String upddate;
	private Long updid;
	private Long replyid;
	private int depth;
}
