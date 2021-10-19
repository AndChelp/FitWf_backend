package info.andchelp.fitwf.controller;

import info.andchelp.fitwf.Requests;
import info.andchelp.fitwf.TestConfiguration;
import info.andchelp.fitwf.URIs;
import info.andchelp.fitwf.dictionary.ExceptionCode;
import info.andchelp.fitwf.dto.request.LoginDto;
import info.andchelp.fitwf.dto.request.RegisterDto;
import info.andchelp.fitwf.dto.response.ResponseDto;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

class AuthControllerTest extends TestConfiguration {

    @Autowired
    JdbcTemplate jdbcTemplate;


    private static LoginDto mapLoginDto(RegisterDto registerDto) {
        return new LoginDto(registerDto.getUsername(), registerDto.getPassword());
    }

    @BeforeEach
    void test() {
        clearDatabase();
    }

    @Test
    void registerNewUserAndLogin_success() {
        RegisterDto registerDto = Requests.registerNewUser();
        Requests.post(URIs.LOGIN, mapLoginDto(registerDto))
                .body("result.accessToken", notNullValue())
                .body("result.refreshToken", notNullValue());
    }

    @Test
    void registerNewUserAndLoginWithWrongPassword_fail() {
        RegisterDto registerDto = Requests.registerNewUser();
        Requests.post(URIs.LOGIN, HttpStatus.SC_FORBIDDEN, new LoginDto(registerDto.getUsername(), registerDto.getPassword() + "1"))
                .body("result.exceptionCode", equalTo(ExceptionCode.INVALID_USERNAME_OR_PASSWORD));
    }

    @Test
    void registerNewUserWithIncorrectPassword_fail() {
        Requests.post(URIs.REGISTER, HttpStatus.SC_BAD_REQUEST, new RegisterDto("t@t.", "st", "123"))
                .body("type", equalTo(ResponseDto.ResponseType.ERROR.toString()))
                .body("result.exceptionCode", equalTo(ExceptionCode.VALIDATION_ERROR));
    }

    @Test
    void registerTwoUsersWithSameData_fail() {
        RegisterDto registerDto = Requests.registerNewUser();
        Requests.post(URIs.REGISTER, HttpStatus.SC_CONFLICT, registerDto)
                .body("type", equalTo(ResponseDto.ResponseType.ERROR.toString()))
                .body("result.exceptionCode", equalTo(ExceptionCode.EMAIL_AND_USERNAME_EXISTS));

    }

    @Test
    void registerUserAndLogout_Success() {
        /*String accessToken = Requests.post(URIs.LOGIN, mapLoginDto(registeredUser))
                .extract().jsonPath().get("result.accessToken");
*/
    }
}