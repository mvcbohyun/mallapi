package com.kinsight.repository;

import org.springframework.stereotype.Repository;

import com.kinsight.domain.Likes;
import com.kinsight.param.LikesParam;

@Repository
public interface LikesRepository {

	int like(Likes likes);

	int unlike(LikesParam likeParam);

	Likes likechk(LikesParam likeParam);

}
