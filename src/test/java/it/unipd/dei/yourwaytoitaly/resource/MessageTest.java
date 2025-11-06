package it.unipd.dei.yourwaytoitaly.resource;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Unit test for Message class
 * Tests constructor, getters, error handling, and JSON serialization
 * 
 * @author YourWayToItaly Test Team
 * @version 1.0
 * @since 1.0
 */
public class MessageTest {

    @Test
    public void testInformativeMessageConstructor() {
        String messageText = "Operation successful";
        Message message = new Message(messageText);
        
        assertNotNull("Message should not be null", message);
        assertEquals("Message text should match", messageText, message.getMessage());
        assertEquals("Error code should be 0", 0, message.getErrorCode());
        assertNull("Error details should be null", message.getErrorDetails());
        assertFalse("Should not be an error", message.isError());
    }

    @Test
    public void testErrorMessageConstructor() {
        String messageText = "Operation failed";
        int errorCode = 500;
        String errorDetails = "Internal server error";
        
        Message message = new Message(messageText, errorCode, errorDetails);
        
        assertNotNull("Message should not be null", message);
        assertEquals("Message text should match", messageText, message.getMessage());
        assertEquals("Error code should match", errorCode, message.getErrorCode());
        assertEquals("Error details should match", errorDetails, message.getErrorDetails());
        assertTrue("Should be an error", message.isError());
    }

    @Test
    public void testInformativeMessageToJSON() throws IOException {
        Message message = new Message("Success");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        message.toJSON(out);
        String json = out.toString();
        
        assertNotNull("JSON output should not be null", json);
        assertTrue("JSON should contain 'message' field", json.contains("message"));
        assertTrue("JSON should contain message text", json.contains("Success"));
        assertFalse("JSON should not contain errorCode", json.contains("errorCode"));
        assertFalse("JSON should not contain errorDetails", json.contains("errorDetails"));
    }

    @Test
    public void testErrorMessageToJSON() throws IOException {
        Message message = new Message("Error occurred", 404, "Resource not found");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        message.toJSON(out);
        String json = out.toString();
        
        assertNotNull("JSON output should not be null", json);
        assertTrue("JSON should contain 'message' field", json.contains("message"));
        assertTrue("JSON should contain message text", json.contains("Error occurred"));
        assertTrue("JSON should contain errorCode", json.contains("errorCode"));
        assertTrue("JSON should contain errorDetails", json.contains("Resource not found"));
    }

    @Test
    public void testMessageWithEmptyText() {
        Message message = new Message("");
        assertEquals("Message text should be empty", "", message.getMessage());
        assertFalse("Should not be an error", message.isError());
    }

    @Test
    public void testMessageWithNullErrorDetails() {
        Message message = new Message("Error", 400, null);
        assertEquals("Error code should be 400", 400, message.getErrorCode());
        assertNull("Error details should be null", message.getErrorDetails());
        assertTrue("Should be an error", message.isError());
    }

    @Test
    public void testMessageWithLongText() {
        String longMessage = "This is a very long message that contains a lot of information " +
            "about what went wrong during the operation and provides detailed " +
            "instructions on how to resolve the issue.";
        
        Message message = new Message(longMessage);
        assertEquals("Long message should match", longMessage, message.getMessage());
    }

    @Test
    public void testDifferentErrorCodes() {
        Message msg400 = new Message("Bad Request", 400, "Invalid input");
        Message msg401 = new Message("Unauthorized", 401, "Authentication required");
        Message msg403 = new Message("Forbidden", 403, "Access denied");
        Message msg404 = new Message("Not Found", 404, "Resource not found");
        Message msg500 = new Message("Server Error", 500, "Internal error");
        
        assertEquals("Error code 400 should match", 400, msg400.getErrorCode());
        assertEquals("Error code 401 should match", 401, msg401.getErrorCode());
        assertEquals("Error code 403 should match", 403, msg403.getErrorCode());
        assertEquals("Error code 404 should match", 404, msg404.getErrorCode());
        assertEquals("Error code 500 should match", 500, msg500.getErrorCode());
        
        assertTrue("All should be errors", msg400.isError() && msg401.isError() && 
            msg403.isError() && msg404.isError() && msg500.isError());
    }

    @Test
    public void testMessageWithSpecialCharacters() {
        Message message = new Message("L'operazione è stata completata con successo!");
        assertTrue("Message should contain special characters", 
            message.getMessage().contains("è"));
    }

    @Test
    public void testSuccessMessages() {
        Message create = new Message("User created successfully");
        Message update = new Message("Profile updated");
        Message delete = new Message("Booking cancelled");
        
        assertFalse("Create message should not be error", create.isError());
        assertFalse("Update message should not be error", update.isError());
        assertFalse("Delete message should not be error", delete.isError());
    }
}
