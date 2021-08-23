package com.kinsight.param;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.kinsight.domain.Likes;

import lombok.Builder;
import lombok.Data;

@Data
public class LikesParam {
	@NotNull
	private Long typeid;
	@NotNull
	private Long userid;
	@NotEmpty
	private String tabletype;
	public Likes saveEntity(LikesParam likeParam) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strNowDate = simpleDateFormat.format(new Date());
		return Likes.builder().typeid(likeParam.getTypeid()).userid(likeParam.getUserid()).tabletype(likeParam.getTabletype()).indate(strNowDate).build();
	}
}
