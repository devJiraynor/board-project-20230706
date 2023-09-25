package com.seojihoon.boardback.service;

import org.springframework.http.ResponseEntity;

import com.seojihoon.boardback.dto.response.user.GetSignInUserResponseDto;

public interface UserService {
    
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email);

}
