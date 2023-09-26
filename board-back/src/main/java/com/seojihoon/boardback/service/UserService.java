package com.seojihoon.boardback.service;

import org.springframework.http.ResponseEntity;

import com.seojihoon.boardback.dto.response.user.GetSignInUserResponseDto;
import com.seojihoon.boardback.dto.response.user.GetUserResponseDto;

public interface UserService {
    
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email);
    ResponseEntity<? super GetUserResponseDto> getUser(String email);

}
