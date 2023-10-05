package com.seojihoon.boardback.dto.response.board;

import java.util.List;

import com.seojihoon.boardback.common.object.FavoriteListItem;
import com.seojihoon.boardback.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class GetFavoriteListResponseDto extends ResponseDto {
    
    private List<FavoriteListItem> favoriteList;

    private GetFavoriteListResponseDto (String code, String message) {
        
    }

}
