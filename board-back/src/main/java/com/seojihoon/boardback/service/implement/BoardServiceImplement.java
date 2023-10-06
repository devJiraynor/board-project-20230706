package com.seojihoon.boardback.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.seojihoon.boardback.dto.request.board.PostBoardRequestDto;
import com.seojihoon.boardback.dto.response.ResponseDto;
import com.seojihoon.boardback.dto.response.board.GetBoardResponseDto;
import com.seojihoon.boardback.dto.response.board.GetCommentListResponseDto;
import com.seojihoon.boardback.dto.response.board.GetFavoriteListResponseDto;
import com.seojihoon.boardback.dto.response.board.GetLatestBoardListResponseDto;
import com.seojihoon.boardback.dto.response.board.PostBoardResponseDto;
import com.seojihoon.boardback.dto.response.board.PutFavoriteResponseDto;
import com.seojihoon.boardback.entity.BoardEntity;
import com.seojihoon.boardback.entity.BoardImageEntity;
import com.seojihoon.boardback.entity.BoardViewEntity;
import com.seojihoon.boardback.entity.FavoriteEntity;
import com.seojihoon.boardback.entity.UserEntity;
import com.seojihoon.boardback.repository.BoardImageRepository;
import com.seojihoon.boardback.repository.BoardRepository;
import com.seojihoon.boardback.repository.BoardViewRepository;
import com.seojihoon.boardback.repository.CommentRespository;
import com.seojihoon.boardback.repository.FavoriteRepository;
import com.seojihoon.boardback.repository.UserRepository;
import com.seojihoon.boardback.repository.resultSet.CommentListResultSet;
import com.seojihoon.boardback.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRespository commentRespository;
    private final FavoriteRepository favoriteRepository; 
    private final BoardViewRepository boardViewRepository;
    private final BoardImageRepository boardImageRepository;

    @Override
    public ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email) {
        
        try {

            boolean existedUser = userRepository.existsByEmail(email);
            if (!existedUser) return PostBoardResponseDto.notExistUser();

            BoardEntity boardEntity = new BoardEntity(dto, email);
            boardRepository.save(boardEntity);

            List<String> boardImageList = dto.getBoardImageList();
            Integer boardNumber = boardEntity.getBoardNumber();

            List<BoardImageEntity> boardImageEntities = new ArrayList<>();
            for (String boardImage: boardImageList) {
                BoardImageEntity boardImageEntity = new BoardImageEntity(boardNumber, boardImage);
                boardImageEntities.add(boardImageEntity);
            }

            boardImageRepository.saveAll(boardImageEntities);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PostBoardResponseDto.success();

    }

	@Override
	public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber) {

        BoardViewEntity boardViewEntity = null;
        List<BoardImageEntity> boardImageEntities = new ArrayList<>();

        try {

            boardViewEntity = boardViewRepository.findByBoardNumber(boardNumber);
            if (boardViewEntity == null) return GetBoardResponseDto.notExistBoard();

            boardImageEntities = boardImageRepository.findByBoardNumber(boardNumber);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetBoardResponseDto.success(boardViewEntity, boardImageEntities);

	}

    @Override
    public ResponseEntity<? super GetFavoriteListResponseDto> getFavorietList(Integer boardNumber) {
        
        List<UserEntity> userEntities = new ArrayList<>();

        try {

            boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
            if (!existedBoard) return GetFavoriteListResponseDto.notExistBoard();

            userEntities = userRepository.findByBoardFavorite(boardNumber);

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetFavoriteListResponseDto.success(userEntities);

    }

    @Override
    public ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer boardNumber) {
        
        List<CommentListResultSet> resultSets = new ArrayList<>();

        try {

            boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
            if (!existedBoard) return GetCommentListResponseDto.notExistBoard();

            resultSets = commentRespository.findByCommentList(boardNumber);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetCommentListResponseDto.success(resultSets);

    }

    @Override
    public ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList() {

        List<BoardViewEntity> boardViewEntities = new ArrayList<>();
        
        try {

            boardViewEntities = boardViewRepository.findByOrderByWriteDatetimeDesc();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetLatestBoardListResponseDto.success(boardViewEntities);

    }

    @Override
    public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email) {
        
        try {

            boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
            if (!existedBoard) return PutFavoriteResponseDto.notExistBoard();

            boolean existedUser = userRepository.existsByEmail(email);
            if (!existedUser) return PutFavoriteResponseDto.notExistUser();

            boolean isFavorite = favoriteRepository.existsByUserEmailAndBoardNumber(email, boardNumber);
            
            FavoriteEntity favoriteEntity = new FavoriteEntity(email, boardNumber);

            if (isFavorite) favoriteRepository.delete(favoriteEntity);
            else favoriteRepository.save(favoriteEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PutFavoriteResponseDto.success();

    }
    
}
