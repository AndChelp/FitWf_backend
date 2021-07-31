package info.andchelp.fitwf.controller;

import info.andchelp.fitwf.Requests;
import info.andchelp.fitwf.TestConfiguration;
import info.andchelp.fitwf.URIs;
import info.andchelp.fitwf.dictionary.ExceptionCode;
import info.andchelp.fitwf.dto.request.RegisterDto;
import info.andchelp.fitwf.dto.response.ResponseDto;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;

class AuthControllerTest extends TestConfiguration {

    @Test
    void registerNewUser_success() {
        Requests.post(URIs.REGISTER, new RegisterDto("t@t.t", "test", "123456"));
    }

    @Test
    void registerNewUserWithIncorrectPassword_fail() {
        Requests.post(URIs.REGISTER, HttpStatus.SC_BAD_REQUEST, new RegisterDto("t@t.", "st", "123"))
                .body("type", equalTo(ResponseDto.ResponseType.ERROR.toString()))
                .body("result.exceptionCode", equalTo(ExceptionCode.VALIDATION_ERROR));
    }

    @Test
    void registerTwoUsersWithSameEmailAndUsername_fail() {
        String username = "SameEmailUsername";
        String email = "t@es.t";
        RegisterDto firstUser = new RegisterDto(email, username, "333123");
        RegisterDto secondUser = new RegisterDto(email, username, "afwafawf");

        Requests.post(URIs.REGISTER, firstUser);
        Requests.post(URIs.REGISTER, secondUser)
                .body("type", equalTo(ResponseDto.ResponseType.ERROR.toString()))
                .body("result.exceptionCode", equalTo(ExceptionCode.EMAIL_AND_USERNAME_EXISTS));

    }
}