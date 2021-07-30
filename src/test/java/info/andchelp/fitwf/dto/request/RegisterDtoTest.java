package info.andchelp.fitwf.dto.request;

import info.andchelp.fitwf.error.enums.ExceptionCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class RegisterDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void givenFullyInvalidRegisterDto_errorValidation() {
        RegisterDto registerDto = new RegisterDto("t@t.", "st", "123");

        Set<ConstraintViolation<RegisterDto>> violations = validator.validate(registerDto);

        assertThat(violations.size(), equalTo(3));

        Set<String> actualErrors = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
        Set<String> expectedErrors = Set.of(ExceptionCode.INVALID_EMAIL, ExceptionCode.INVALID_USERNAME, ExceptionCode.INVALID_PASSWORD);

        assertThat(actualErrors, equalTo(expectedErrors));
    }

    @Test
    public void givenValidRegisterDto_success() {
        RegisterDto registerDto = new RegisterDto("te@s.t", "test", "test123");

        Set<ConstraintViolation<RegisterDto>> violations = validator.validate(registerDto);

        assertThat(violations.size(), equalTo(0));
    }
}