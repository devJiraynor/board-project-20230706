package com.seojihoon.boardback.service;

import org.springframework.http.ResponseEntity;

import com.seojihoon.boardback.dto.request.board.PostBoardRequestDto;
import com.seojihoon.boardback.dto.response.board.PostBoardResponseDto;

public interface BoardSerivce {
    
    ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email);

}
