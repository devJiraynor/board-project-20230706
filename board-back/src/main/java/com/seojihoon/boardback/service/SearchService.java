package com.seojihoon.boardback.service;

import org.springframework.http.ResponseEntity;

import com.seojihoon.boardback.dto.response.search.GetPopularListResponseDto;

public interface SearchService {
    ResponseEntity<? super GetPopularListResponseDto> getPopularList();
}
