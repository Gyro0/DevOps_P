package it.unipd.dei.yourwaytoitaly.resource;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Unit test for TypeAdvertisement class
 * Tests constructor, getters, and JSON serialization/deserialization
 * 
 * @author YourWayToItaly Test Team
 * @version 1.0
 * @since 1.0
 */
public class TypeAdvertisementTest {

    private TypeAdvertisement typeAdvertisement;
    private static final int ID_TYPE = 1;
    private static final String TYPE = "Cultural Tour";

    @Before
    public void setUp() {
        typeAdvertisement = new TypeAdvertisement(ID_TYPE, TYPE);
    }

    @Test
    public void testConstructorAndGetters() {
        assertNotNull("TypeAdvertisement should not be null", typeAdvertisement);
        assertEquals("Type ID should match", ID_TYPE, typeAdvertisement.getIdType());
        assertEquals("Type should match", TYPE, typeAdvertisement.getType());
    }

    @Test
    public void testToJSON() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        typeAdvertisement.toJSON(out);
        String json = out.toString();
        
        assertNotNull("JSON output should not be null", json);
        assertTrue("JSON should contain 'typeAdvertisement' field", json.contains("typeAdvertisement"));
        assertTrue("JSON should contain type", json.contains(TYPE));
    }

    @Test
    public void testFromJSON() throws IOException {
        String jsonInput = "{\"typeAdvertisement\":{" +
            "\"idType\":\"1\"," +
            "\"type\":\"Cultural Tour\"" +
            "}}";

        ByteArrayInputStream in = new ByteArrayInputStream(jsonInput.getBytes());
        TypeAdvertisement parsed = TypeAdvertisement.fromJSON(in);

        assertNotNull("Parsed type advertisement should not be null", parsed);
        assertEquals("Parsed type ID should match", ID_TYPE, parsed.getIdType());
        assertEquals("Parsed type should match", TYPE, parsed.getType());
    }

    @Test
    public void testDifferentAdvertisementTypes() {
        TypeAdvertisement cultural = new TypeAdvertisement(1, "Cultural Tour");
        TypeAdvertisement food = new TypeAdvertisement(2, "Food & Wine Tour");
        TypeAdvertisement adventure = new TypeAdvertisement(3, "Adventure Tour");
        TypeAdvertisement historical = new TypeAdvertisement(4, "Historical Tour");
        
        assertEquals("Cultural tour type should match", "Cultural Tour", cultural.getType());
        assertEquals("Food tour type should match", "Food & Wine Tour", food.getType());
        assertEquals("Adventure tour type should match", "Adventure Tour", adventure.getType());
        assertEquals("Historical tour type should match", "Historical Tour", historical.getType());
    }

    @Test
    public void testTypeAdvertisementWithEmptyType() {
        TypeAdvertisement emptyType = new TypeAdvertisement(99, "");
        assertEquals("Empty type should match", "", emptyType.getType());
    }

    @Test
    public void testTypeAdvertisementWithLongTypeName() {
        String longType = "Comprehensive Historical and Cultural Heritage Tour with Local Guide";
        TypeAdvertisement longTypeAd = new TypeAdvertisement(10, longType);
        assertEquals("Long type name should match", longType, longTypeAd.getType());
    }

    @Test
    public void testTypeIdUniqueness() {
        TypeAdvertisement type1 = new TypeAdvertisement(1, "Same Type");
        TypeAdvertisement type2 = new TypeAdvertisement(2, "Same Type");
        
        assertNotEquals("Type IDs should be different", type1.getIdType(), type2.getIdType());
        assertEquals("Type names can be the same", type1.getType(), type2.getType());
    }
}
