import axios from 'axios';
import { SignUpRequestDto } from './dto/request/auth';
import { SignUpResponseDto } from './dto/response/auth';
import ResponseDto from './dto/response';

// description: API Domain 주소 //
const API_DOMAIN = 'http://localhost:4000/api/v1';

// description: sign up API end point //
const SIGN_UP_URL = () => `${API_DOMAIN}/auth/sign-up`;

// description: sign up request //
export const signUpRequest  = async (requestBody: SignUpRequestDto) => {
    const result = await axios.post(SIGN_UP_URL(), requestBody)
        .then(response => {
            const responseBody: SignUpResponseDto = response.data;
            const { code } = responseBody;
            return code;
        })
        .catch(error => {
            const responseBody: ResponseDto = error.response.data;
            const { code } = responseBody;
            return code;
        });
    return result;
};