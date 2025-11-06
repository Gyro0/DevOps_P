package it.unipd.dei.yourwaytoitaly.resource;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Unit test for City class
 * Tests constructor, getters, and JSON serialization/deserialization
 * 
 * @author YourWayToItaly Test Team
 * @version 1.0
 * @since 1.0
 */
public class CityTest {

    private City city;
    private static final int ID_CITY = 1;
    private static final String CITY_NAME = "Venice";

    @Before
    public void setUp() {
        city = new City(ID_CITY, CITY_NAME);
    }

    @Test
    public void testConstructorAndGetters() {
        assertNotNull("City should not be null", city);
        assertEquals("City ID should match", ID_CITY, city.getIdCity());
        assertEquals("City name should match", CITY_NAME, city.getIdCityName());
    }

    @Test
    public void testToJSON() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        city.toJSON(out);
        String json = out.toString();
        
        assertNotNull("JSON output should not be null", json);
        assertTrue("JSON should contain 'city' field", json.contains("city"));
        assertTrue("JSON should contain city name", json.contains(CITY_NAME));
    }

    @Test
    public void testFromJSON() throws IOException {
        String jsonInput = "{\"city\":{" +
            "\"idCity\":\"1\"," +
            "\"idCityName\":\"Venice\"" +
            "}}";

        ByteArrayInputStream in = new ByteArrayInputStream(jsonInput.getBytes());
        City parsed = City.fromJSON(in);

        assertNotNull("Parsed city should not be null", parsed);
        assertEquals("Parsed city ID should match", ID_CITY, parsed.getIdCity());
        assertEquals("Parsed city name should match", CITY_NAME, parsed.getIdCityName());
    }

    @Test
    public void testCityWithDifferentNames() {
        City rome = new City(2, "Rome");
        City milan = new City(3, "Milan");
        City florence = new City(4, "Florence");
        
        assertEquals("Rome should have correct name", "Rome", rome.getIdCityName());
        assertEquals("Milan should have correct name", "Milan", milan.getIdCityName());
        assertEquals("Florence should have correct name", "Florence", florence.getIdCityName());
    }

    @Test
    public void testCityWithSpecialCharacters() {
        City specialCity = new City(5, "L'Aquila");
        assertEquals("City name with apostrophe should match", "L'Aquila", specialCity.getIdCityName());
    }

    @Test
    public void testCityWithLongName() {
        String longName = "San Gimignano delle Belle Torri";
        City longNameCity = new City(10, longName);
        assertEquals("Long city name should match", longName, longNameCity.getIdCityName());
    }

    @Test
    public void testCityWithMultipleWords() {
        City multiWordCity = new City(6, "San Marino");
        assertTrue("City name should contain space", multiWordCity.getIdCityName().contains(" "));
        assertEquals("Multi-word city name should match", "San Marino", multiWordCity.getIdCityName());
    }

    @Test
    public void testCityIdUniqueness() {
        City city1 = new City(1, "Venice");
        City city2 = new City(2, "Venice");
        
        assertNotEquals("City IDs should be different", city1.getIdCity(), city2.getIdCity());
        assertEquals("City names can be the same", city1.getIdCityName(), city2.getIdCityName());
    }

    @Test
    public void testCityWithZeroId() {
        City zeroIdCity = new City(0, "Test City");
        assertEquals("City ID should be 0", 0, zeroIdCity.getIdCity());
    }

    @Test
    public void testCityWithLargeId() {
        City largeIdCity = new City(999999, "Test City");
        assertEquals("City ID should be 999999", 999999, largeIdCity.getIdCity());
    }

    @Test
    public void testItalianCities() {
        City[] italianCities = {
            new City(1, "Roma"),
            new City(2, "Milano"),
            new City(3, "Napoli"),
            new City(4, "Torino"),
            new City(5, "Venezia"),
            new City(6, "Firenze")
        };
        
        assertEquals("Should have 6 cities", 6, italianCities.length);
        for (City c : italianCities) {
            assertNotNull("City should not be null", c);
            assertTrue("City ID should be positive", c.getIdCity() > 0);
            assertNotNull("City name should not be null", c.getIdCityName());
        }
    }
}
