package it.unipd.dei.yourwaytoitaly.resource;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;

import static org.junit.Assert.*;

/**
 * Unit test for Feedback class
 * Tests constructor, getters, and JSON serialization/deserialization
 * 
 * @author YourWayToItaly Test Team
 * @version 1.0
 * @since 1.0
 */
public class FeedbackTest {

    private Feedback feedback;
    private static final String EMAIL_TOURIST = "tourist@test.com";
    private static final int ID_ADVERTISEMENT = 1;
    private static final int RATE = 5;
    private static final String TEXT = "Excellent tour! Highly recommended.";
    private static final Date DATE = Date.valueOf("2024-06-20");

    @Before
    public void setUp() {
        feedback = new Feedback(EMAIL_TOURIST, ID_ADVERTISEMENT, RATE, TEXT, DATE);
    }

    @Test
    public void testConstructorAndGetters() {
        assertNotNull("Feedback should not be null", feedback);
        assertEquals("Tourist email should match", EMAIL_TOURIST, feedback.getEmailTourist());
        assertEquals("Advertisement ID should match", ID_ADVERTISEMENT, feedback.getIdAdvertisement());
        assertEquals("Rate should match", RATE, feedback.getRate());
        assertEquals("Text should match", TEXT, feedback.getText());
        assertEquals("Date should match", DATE, feedback.getDate());
    }

    @Test
    public void testToJSON() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        feedback.toJSON(out);
        String json = out.toString();
        
        assertNotNull("JSON output should not be null", json);
        assertTrue("JSON should contain 'feedback' field", json.contains("feedback"));
        assertTrue("JSON should contain tourist email", json.contains(EMAIL_TOURIST));
        assertTrue("JSON should contain text", json.contains(TEXT));
    }

    @Test
    public void testFromJSON() throws IOException {
        String jsonInput = "{\"feedback\":{" +
            "\"emailTourist\":\"tourist@test.com\"," +
            "\"idAdvertisement\":\"1\"," +
            "\"rate\":\"5\"," +
            "\"text\":\"Excellent tour! Highly recommended.\"," +
            "\"date\":\"2024-06-20\"" +
            "}}";

        ByteArrayInputStream in = new ByteArrayInputStream(jsonInput.getBytes());
        Feedback parsed = Feedback.fromJSON(in);

        assertNotNull("Parsed feedback should not be null", parsed);
        assertEquals("Parsed email should match", EMAIL_TOURIST, parsed.getEmailTourist());
        assertEquals("Parsed rate should match", RATE, parsed.getRate());
        assertEquals("Parsed text should match", TEXT, parsed.getText());
    }

    @Test
    public void testFeedbackWithMinimumRate() {
        Feedback minRateFeedback = new Feedback(
            "user@test.com", 1, 1, "Poor experience", DATE
        );
        assertEquals("Rate should be 1", 1, minRateFeedback.getRate());
    }

    @Test
    public void testFeedbackWithMaximumRate() {
        Feedback maxRateFeedback = new Feedback(
            "user@test.com", 1, 5, "Perfect!", DATE
        );
        assertEquals("Rate should be 5", 5, maxRateFeedback.getRate());
    }

    @Test
    public void testFeedbackWithEmptyText() {
        Feedback emptyTextFeedback = new Feedback(
            "user@test.com", 1, 4, "", DATE
        );
        assertEquals("Text should be empty", "", emptyTextFeedback.getText());
    }

    @Test
    public void testFeedbackWithLongText() {
        String longText = "This is a very long feedback text that describes in great detail " +
            "the wonderful experience I had during the tour. The guide was extremely knowledgeable, " +
            "the locations were beautiful, and the overall organization was impeccable. " +
            "I would definitely recommend this to anyone visiting the area.";
        
        Feedback longTextFeedback = new Feedback(
            "user@test.com", 1, 5, longText, DATE
        );
        assertEquals("Long text should match", longText, longTextFeedback.getText());
    }

    @Test
    public void testFeedbackWithSpecialCharacters() {
        String specialText = "Great tour! The guide spoke English, Italian & French. Cost €50 - worth it!";
        Feedback specialFeedback = new Feedback(
            "special@test.com", 2, 4, specialText, DATE
        );
        assertEquals("Text with special chars should match", specialText, specialFeedback.getText());
    }

    @Test
    public void testFeedbackWithDifferentAdvertisements() {
        Feedback feedback1 = new Feedback(EMAIL_TOURIST, 1, 5, "Great", DATE);
        Feedback feedback2 = new Feedback(EMAIL_TOURIST, 2, 3, "Good", DATE);
        
        assertNotEquals("Advertisement IDs should be different", 
            feedback1.getIdAdvertisement(), feedback2.getIdAdvertisement());
        assertEquals("Same tourist should have same email", 
            feedback1.getEmailTourist(), feedback2.getEmailTourist());
    }

    @Test
    public void testFeedbackRatingRange() {
        for (int rate = 1; rate <= 5; rate++) {
            Feedback rateFeedback = new Feedback(
                "user@test.com", 1, rate, "Test feedback", DATE
            );
            assertEquals("Rate should match iteration", rate, rateFeedback.getRate());
            assertTrue("Rate should be between 1 and 5", 
                rateFeedback.getRate() >= 1 && rateFeedback.getRate() <= 5);
        }
    }

    @Test
    public void testFeedbackWithNullText() {
        Feedback nullTextFeedback = new Feedback(
            "user@test.com", 1, 4, null, DATE
        );
        assertNull("Text should be null", nullTextFeedback.getText());
    }
}
