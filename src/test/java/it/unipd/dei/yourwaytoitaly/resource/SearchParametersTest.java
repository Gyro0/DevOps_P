package it.unipd.dei.yourwaytoitaly.resource;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;

import static org.junit.Assert.*;

/**
 * Unit test for SearchParameters class
 * Tests constructor, getters, and JSON serialization/deserialization
 * 
 * @author YourWayToItaly Test Team
 * @version 1.0
 * @since 1.0
 */
public class SearchParametersTest {

    private SearchParameters searchParams;
    private static final int ID_TYPE = 1;
    private static final int ID_CITY = 5;
    private static final Date DATE_START = Date.valueOf("2024-06-01");

    @Before
    public void setUp() {
        searchParams = new SearchParameters(ID_TYPE, ID_CITY, DATE_START);
    }

    @Test
    public void testConstructorAndGetters() {
        assertNotNull("SearchParameters should not be null", searchParams);
        assertEquals("Type ID should match", ID_TYPE, searchParams.getIdType());
        assertEquals("City ID should match", ID_CITY, searchParams.getIdCity());
        assertEquals("Date start should match", DATE_START, searchParams.getDateStart());
    }

    @Test
    public void testToJSON() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        searchParams.toJSON(out);
        String json = out.toString();
        
        assertNotNull("JSON output should not be null", json);
        assertTrue("JSON should contain 'searchParameters' field", json.contains("searchParameters"));
        assertTrue("JSON should contain idType", json.contains("idType"));
        assertTrue("JSON should contain idCity", json.contains("idCity"));
        assertTrue("JSON should contain dateStart", json.contains("dateStart"));
    }

    @Test
    public void testFromJSON() throws IOException {
        String jsonInput = "{\"searchParameters\":{" +
            "\"idType\":\"1\"," +
            "\"idCity\":\"5\"," +
            "\"dateStart\":\"2024-06-01\"" +
            "}}";

        ByteArrayInputStream in = new ByteArrayInputStream(jsonInput.getBytes());
        SearchParameters parsed = SearchParameters.fromJSON(in);

        assertNotNull("Parsed search parameters should not be null", parsed);
        assertEquals("Parsed type ID should match", ID_TYPE, parsed.getIdType());
        assertEquals("Parsed city ID should match", ID_CITY, parsed.getIdCity());
        assertEquals("Parsed date should match", DATE_START, parsed.getDateStart());
    }

    @Test
    public void testSearchParametersWithDifferentDates() {
        SearchParameters past = new SearchParameters(1, 5, Date.valueOf("2020-01-01"));
        SearchParameters future = new SearchParameters(1, 5, Date.valueOf("2025-12-31"));
        
        assertTrue("Past date should be before future date", 
            past.getDateStart().before(future.getDateStart()));
    }

    @Test
    public void testSearchParametersWithZeroIds() {
        SearchParameters zeroIds = new SearchParameters(0, 0, DATE_START);
        assertEquals("Type ID should be 0", 0, zeroIds.getIdType());
        assertEquals("City ID should be 0", 0, zeroIds.getIdCity());
    }

    @Test
    public void testSearchParametersWithLargeIds() {
        SearchParameters largeIds = new SearchParameters(999, 888, DATE_START);
        assertEquals("Type ID should be 999", 999, largeIds.getIdType());
        assertEquals("City ID should be 888", 888, largeIds.getIdCity());
    }

    @Test
    public void testSearchParametersEquality() {
        SearchParameters params1 = new SearchParameters(ID_TYPE, ID_CITY, DATE_START);
        SearchParameters params2 = new SearchParameters(ID_TYPE, ID_CITY, DATE_START);
        
        assertEquals("Type IDs should match", params1.getIdType(), params2.getIdType());
        assertEquals("City IDs should match", params1.getIdCity(), params2.getIdCity());
        assertEquals("Dates should match", params1.getDateStart(), params2.getDateStart());
    }
}
