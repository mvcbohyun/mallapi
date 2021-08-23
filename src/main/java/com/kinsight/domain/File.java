package com.kinsight.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File {
	private Long id;
	private Long typeid;
	private String uploadfile;
	private String path;
	private String fullpath;
	private String filekey;
	private int seq;
	private String filetype;
	private String tabletype;

}
