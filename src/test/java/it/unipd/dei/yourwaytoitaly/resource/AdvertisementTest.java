package it.unipd.dei.yourwaytoitaly.resource;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;

import static org.junit.Assert.*;

/**
 * Unit test for Advertisement class
 * Tests constructor, getters, and JSON serialization/deserialization
 * 
 * @author YourWayToItaly Test Team
 * @version 1.0
 * @since 1.0
 */
public class AdvertisementTest {

    private Advertisement advertisement;
    private static final int ID = 1;
    private static final String TITLE = "Tour of Venice";
    private static final String DESCRIPTION = "A wonderful tour";
    private static final int SCORE = 5;
    private static final int PRICE = 100;
    private static final int NUM_ITEMS = 20;
    private static final Date DATE_START = Date.valueOf("2024-06-01");
    private static final Date DATE_END = Date.valueOf("2024-06-30");
    private static final Time TIME_START = Time.valueOf("09:00:00");
    private static final Time TIME_END = Time.valueOf("18:00:00");
    private static final String EMAIL_COMPANY = "company@test.com";
    private static final int ID_TYPE = 1;

    @Before
    public void setUp() {
        advertisement = new Advertisement(
            ID, TITLE, DESCRIPTION, SCORE, PRICE, NUM_ITEMS,
            DATE_START, DATE_END, TIME_START, TIME_END,
            EMAIL_COMPANY, ID_TYPE
        );
    }

    @Test
    public void testConstructorAndGetters() {
        assertNotNull("Advertisement should not be null", advertisement);
        assertEquals("ID should match", ID, advertisement.getIdAdvertisement());
        assertEquals("Title should match", TITLE, advertisement.getTitle());
        assertEquals("Description should match", DESCRIPTION, advertisement.getDescription());
        assertEquals("Score should match", SCORE, advertisement.getScore());
        assertEquals("Price should match", PRICE, advertisement.getPrice());
        assertEquals("Number of items should match", NUM_ITEMS, advertisement.getNumTotItem());
        assertEquals("Start date should match", DATE_START, advertisement.getDateStart());
        assertEquals("End date should match", DATE_END, advertisement.getDateEnd());
        assertEquals("Start time should match", TIME_START, advertisement.getTimeStart());
        assertEquals("End time should match", TIME_END, advertisement.getTimeEnd());
        assertEquals("Company email should match", EMAIL_COMPANY, advertisement.getEmailCompany());
        assertEquals("Type ID should match", ID_TYPE, advertisement.getIdType());
    }

    @Test
    public void testToJSON() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        advertisement.toJSON(out);
        String json = out.toString();
        
        assertNotNull("JSON output should not be null", json);
        assertTrue("JSON should contain 'advertisement' field", json.contains("advertisement"));
        assertTrue("JSON should contain title", json.contains(TITLE));
        assertTrue("JSON should contain description", json.contains(DESCRIPTION));
        assertTrue("JSON should contain company email", json.contains(EMAIL_COMPANY));
    }

    @Test
    public void testFromJSON() throws IOException {
        String jsonInput = "{\"advertisement\":{" +
            "\"idAdvertisement\":\"1\"," +
            "\"title\":\"Tour of Venice\"," +
            "\"description\":\"A wonderful tour\"," +
            "\"score\":\"5\"," +
            "\"price\":\"100\"," +
            "\"numTotItem\":\"20\"," +
            "\"dateStart\":\"2024-06-01\"," +
            "\"dateEnd\":\"2024-06-30\"," +
            "\"timeStart\":\"09:00:00\"," +
            "\"timeEnd\":\"18:00:00\"," +
            "\"emailCompany\":\"company@test.com\"," +
            "\"idType\":\"1\"" +
            "}}";

        ByteArrayInputStream in = new ByteArrayInputStream(jsonInput.getBytes());
        Advertisement parsed = Advertisement.fromJSON(in);

        assertNotNull("Parsed advertisement should not be null", parsed);
        assertEquals("Parsed ID should match", ID, parsed.getIdAdvertisement());
        assertEquals("Parsed title should match", TITLE, parsed.getTitle());
        assertEquals("Parsed description should match", DESCRIPTION, parsed.getDescription());
        assertEquals("Parsed email should match", EMAIL_COMPANY, parsed.getEmailCompany());
    }

    @Test
    public void testAdvertisementWithZeroScore() {
        Advertisement ad = new Advertisement(
            2, "New Tour", "Description", 0, 50, 10,
            DATE_START, DATE_END, TIME_START, TIME_END,
            "test@company.com", 1
        );
        
        assertEquals("Score should be 0", 0, ad.getScore());
    }

    @Test
    public void testAdvertisementWithHighPrice() {
        Advertisement ad = new Advertisement(
            3, "Premium Tour", "Premium Description", 5, 10000, 5,
            DATE_START, DATE_END, TIME_START, TIME_END,
            "premium@company.com", 2
        );
        
        assertEquals("Price should be 10000", 10000, ad.getPrice());
    }

    @Test
    public void testAdvertisementWithSingleItem() {
        Advertisement ad = new Advertisement(
            4, "Exclusive Tour", "One spot only", 5, 500, 1,
            DATE_START, DATE_END, TIME_START, TIME_END,
            "exclusive@company.com", 3
        );
        
        assertEquals("Number of items should be 1", 1, ad.getNumTotItem());
    }
}
