package com.kinsight.service;

import org.springframework.stereotype.Service;

import com.kinsight.domain.Likes;
import com.kinsight.param.LikesParam;
import com.kinsight.repository.LikesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikesService {
	
	private final LikesRepository likesRepository;

	public void like(LikesParam likeParam) {
		Likes likeschk = likesRepository.likechk(likeParam);
		
		if(likeschk ==null) {
		Likes likes = likeParam.saveEntity(likeParam);
		int result = likesRepository.like(likes);
		}
	}

	public void unlike(LikesParam likeParam) {
		int result = likesRepository.unlike(likeParam);
		
	}

}
