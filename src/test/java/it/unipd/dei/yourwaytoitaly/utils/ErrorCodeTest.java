package it.unipd.dei.yourwaytoitaly.utils;

import org.junit.Test;

import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;

/**
 * Unit test for ErrorCode enum
 * Tests error codes, HTTP codes, and error messages
 * 
 * @author YourWayToItaly Test Team
 * @version 1.0
 * @since 1.0
 */
public class ErrorCodeTest {

    @Test
    public void testWrongFormatError() {
        ErrorCode error = ErrorCode.WRONG_FORMAT;
        assertEquals("Error code should be -1", -1, error.getErrorCode());
        assertEquals("HTTP code should be BAD_REQUEST", 
            HttpServletResponse.SC_BAD_REQUEST, error.getHTTPCode());
        assertEquals("Error message should match", "Wrong format.", error.getErrorMessage());
    }

    @Test
    public void testAdvertisementNotFoundError() {
        ErrorCode error = ErrorCode.AD_NOT_FOUND;
        assertEquals("Error code should be -2", -2, error.getErrorCode());
        assertEquals("HTTP code should be NOT_FOUND", 
            HttpServletResponse.SC_NOT_FOUND, error.getHTTPCode());
        assertEquals("Error message should match", "Advertisement not found.", error.getErrorMessage());
    }

    @Test
    public void testEmptyInputFieldsError() {
        ErrorCode error = ErrorCode.EMPTY_INPUT_FIELDS;
        assertEquals("Error code should be -3", -3, error.getErrorCode());
        assertEquals("HTTP code should be BAD_REQUEST", 
            HttpServletResponse.SC_BAD_REQUEST, error.getHTTPCode());
        assertEquals("Error message should match", 
            "One or more input fields are empty.", error.getErrorMessage());
    }

    @Test
    public void testEmailMissingError() {
        ErrorCode error = ErrorCode.EMAIL_MISSING;
        assertEquals("Error code should be -4", -4, error.getErrorCode());
        assertEquals("HTTP code should be BAD_REQUEST", 
            HttpServletResponse.SC_BAD_REQUEST, error.getHTTPCode());
        assertEquals("Error message should match", "Email missing.", error.getErrorMessage());
    }

    @Test
    public void testPasswordMissingError() {
        ErrorCode error = ErrorCode.PASSWORD_MISSING;
        assertEquals("Error code should be -5", -5, error.getErrorCode());
        assertEquals("HTTP code should be BAD_REQUEST", 
            HttpServletResponse.SC_BAD_REQUEST, error.getHTTPCode());
        assertEquals("Error message should match", "Password missing.", error.getErrorMessage());
    }

    @Test
    public void testDifferentPasswordsError() {
        ErrorCode error = ErrorCode.DIFFERENT_PASSWORDS;
        assertEquals("Error code should be -6", -6, error.getErrorCode());
        assertEquals("HTTP code should be CONFLICT", 
            HttpServletResponse.SC_CONFLICT, error.getHTTPCode());
        assertEquals("Error message should match", "Different passwords.", error.getErrorMessage());
    }

    @Test
    public void testMailAlreadyUsedError() {
        ErrorCode error = ErrorCode.MAIL_ALREADY_USED;
        assertEquals("Error code should be -7", -7, error.getErrorCode());
        assertEquals("HTTP code should be CONFLICT", 
            HttpServletResponse.SC_CONFLICT, error.getHTTPCode());
        assertEquals("Error message should match", "Email already used.", error.getErrorMessage());
    }

    @Test
    public void testEmptyListError() {
        ErrorCode error = ErrorCode.EMPTY_LIST;
        assertEquals("Error code should be -8", -8, error.getErrorCode());
        assertEquals("HTTP code should be NOT_FOUND", 
            HttpServletResponse.SC_NOT_FOUND, error.getHTTPCode());
        assertEquals("Error message should match", "No results found.", error.getErrorMessage());
    }

    @Test
    public void testUserNotFoundError() {
        ErrorCode error = ErrorCode.USER_NOT_FOUND;
        assertEquals("Error code should be -9", -9, error.getErrorCode());
        assertEquals("HTTP code should be NOT_FOUND", 
            HttpServletResponse.SC_NOT_FOUND, error.getHTTPCode());
        assertEquals("Error message should match", "User not found.", error.getErrorMessage());
    }

    @Test
    public void testBadlyFormattedJsonError() {
        ErrorCode error = ErrorCode.BADLY_FORMATTED_JSON;
        assertEquals("Error code should be -10", -10, error.getErrorCode());
        assertEquals("HTTP code should be BAD_REQUEST", 
            HttpServletResponse.SC_BAD_REQUEST, error.getHTTPCode());
        assertEquals("Error message should match", 
            "The input json is in the wrong format.", error.getErrorMessage());
    }

    @Test
    public void testUserNotAllowedError() {
        ErrorCode error = ErrorCode.USER_NOT_ALLOWED;
        assertEquals("Error code should be -11", -11, error.getErrorCode());
        assertEquals("HTTP code should be UNAUTHORIZED", 
            HttpServletResponse.SC_UNAUTHORIZED, error.getHTTPCode());
        assertEquals("Error message should match", "User not allowed.", error.getErrorMessage());
    }

    @Test
    public void testBookingAlreadyDoneError() {
        ErrorCode error = ErrorCode.BOOKING_ALREADY_DONE;
        assertEquals("Error code should be -12", -12, error.getErrorCode());
        assertEquals("HTTP code should be CONFLICT", 
            HttpServletResponse.SC_CONFLICT, error.getHTTPCode());
        assertEquals("Error message should match", "Booking already done.", error.getErrorMessage());
    }

    @Test
    public void testFeedbackAlreadyDoneError() {
        ErrorCode error = ErrorCode.FEEDBACK_ALREADY_DONE;
        assertEquals("Error code should be -13", -13, error.getErrorCode());
        assertEquals("HTTP code should be CONFLICT", 
            HttpServletResponse.SC_CONFLICT, error.getHTTPCode());
        assertEquals("Error message should match", "Feedback already done.", error.getErrorMessage());
    }

    @Test
    public void testOperationUnknownError() {
        ErrorCode error = ErrorCode.OPERATION_UNKNOWN;
        assertEquals("Error code should be -20", -20, error.getErrorCode());
        assertEquals("HTTP code should be BAD_REQUEST", 
            HttpServletResponse.SC_BAD_REQUEST, error.getHTTPCode());
        assertEquals("Error message should match", "Operation unknown.", error.getErrorMessage());
    }

    @Test
    public void testMethodNotAllowedError() {
        ErrorCode error = ErrorCode.METHOD_NOT_ALLOWED;
        assertEquals("Error code should be -40", -40, error.getErrorCode());
        assertEquals("HTTP code should be METHOD_NOT_ALLOWED", 
            HttpServletResponse.SC_METHOD_NOT_ALLOWED, error.getHTTPCode());
        assertEquals("Error message should match", "The method is not allowed.", error.getErrorMessage());
    }

    @Test
    public void testInternalError() {
        ErrorCode error = ErrorCode.INTERNAL_ERROR;
        assertEquals("Error code should be -100", -100, error.getErrorCode());
        assertEquals("HTTP code should be INTERNAL_SERVER_ERROR", 
            HttpServletResponse.SC_INTERNAL_SERVER_ERROR, error.getHTTPCode());
        assertEquals("Error message should match", "Internal Error.", error.getErrorMessage());
    }

    @Test
    public void testAllErrorCodesAreNegative() {
        for (ErrorCode error : ErrorCode.values()) {
            assertTrue("All error codes should be negative: " + error.name(), 
                error.getErrorCode() < 0);
        }
    }

    @Test
    public void testAllHttpCodesAreValid() {
        for (ErrorCode error : ErrorCode.values()) {
            int httpCode = error.getHTTPCode();
            assertTrue("HTTP code should be valid (>= 400): " + error.name(), 
                httpCode >= 400 && httpCode < 600);
        }
    }

    @Test
    public void testAllErrorMessagesAreNotEmpty() {
        for (ErrorCode error : ErrorCode.values()) {
            assertNotNull("Error message should not be null: " + error.name(), 
                error.getErrorMessage());
            assertFalse("Error message should not be empty: " + error.name(), 
                error.getErrorMessage().isEmpty());
        }
    }

    @Test
    public void testEnumValuesCount() {
        ErrorCode[] allErrors = ErrorCode.values();
        assertEquals("Should have 16 error codes", 16, allErrors.length);
    }

    @Test
    public void testErrorCodeUniqueness() {
        ErrorCode[] allErrors = ErrorCode.values();
        for (int i = 0; i < allErrors.length; i++) {
            for (int j = i + 1; j < allErrors.length; j++) {
                assertNotEquals("Error codes should be unique", 
                    allErrors[i].getErrorCode(), allErrors[j].getErrorCode());
            }
        }
    }
}
