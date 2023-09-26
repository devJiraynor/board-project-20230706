package com.seojihoon.boardback.service.implement;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.seojihoon.boardback.dto.request.board.PostBoardRequestDto;
import com.seojihoon.boardback.dto.response.ResponseDto;
import com.seojihoon.boardback.dto.response.board.PostBoardResponseDto;
import com.seojihoon.boardback.entity.BoardEntity;
import com.seojihoon.boardback.repository.BoardRepository;
import com.seojihoon.boardback.repository.UserRepository;
import com.seojihoon.boardback.service.BoardSerivce;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardSerivce {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Override
    public ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email) {
        
        try {

            boolean existedUser = userRepository.existsByEmail(email);
            if (!existedUser) return PostBoardResponseDto.notExistUser();

            BoardEntity boardEntity = new BoardEntity(dto, email);
            boardRepository.save(boardEntity);

            List<String> boardImageList = dto.getBoardImageList();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PostBoardResponseDto.success();

    }

    
    
}
