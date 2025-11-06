package it.unipd.dei.yourwaytoitaly.resource;

import org.junit.Before;
import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.*;

/**
 * Unit test for Tourist class
 * Tests constructor, getters, and type identification
 * 
 * @author YourWayToItaly Test Team
 * @version 1.0
 * @since 1.0
 */
public class TouristTest {

    private Tourist tourist;
    private static final String EMAIL = "tourist@test.com";
    private static final String PASSWORD = "touristPass123";
    private static final String NAME = "Mario";
    private static final String ADDRESS = "Via Roma, 10, Milano";
    private static final String PHONE = "+39 02 1234567";
    private static final int ID_CITY = 10;
    private static final String SURNAME = "Rossi";
    private static final Date BIRTH_DATE = Date.valueOf("1990-05-15");

    @Before
    public void setUp() {
        tourist = new Tourist(EMAIL, PASSWORD, NAME, ADDRESS, PHONE, ID_CITY, SURNAME, BIRTH_DATE);
    }

    @Test
    public void testConstructorAndGetters() {
        assertNotNull("Tourist should not be null", tourist);
        assertEquals("Email should match", EMAIL, tourist.getEmail());
        assertEquals("Password should match", PASSWORD, tourist.getPassword());
        assertEquals("Name should match", NAME, tourist.getName());
        assertEquals("Address should match", ADDRESS, tourist.getAddress());
        assertEquals("Phone number should match", PHONE, tourist.getPhoneNumber());
        assertEquals("City ID should match", ID_CITY, tourist.getIdCity());
        assertEquals("Surname should match", SURNAME, tourist.getSurname());
        assertEquals("Birth date should match", BIRTH_DATE, tourist.getBirthDate());
    }

    @Test
    public void testGetType() {
        assertEquals("Type should be 'Tourist'", "Tourist", tourist.getType());
    }

    @Test
    public void testTouristIsInstanceOfUser() {
        assertTrue("Tourist should be instance of User", tourist instanceof User);
    }

    @Test
    public void testTouristWithOldBirthDate() {
        Date oldDate = Date.valueOf("1950-01-01");
        Tourist oldTourist = new Tourist(
            "old@test.com", "pass", "Giuseppe", "Address", "123456", 1, "Verdi", oldDate
        );
        assertEquals("Old birth date should match", oldDate, oldTourist.getBirthDate());
    }

    @Test
    public void testTouristWithRecentBirthDate() {
        Date recentDate = Date.valueOf("2005-12-31");
        Tourist youngTourist = new Tourist(
            "young@test.com", "pass", "Luca", "Address", "123456", 1, "Bianchi", recentDate
        );
        assertEquals("Recent birth date should match", recentDate, youngTourist.getBirthDate());
    }

    @Test
    public void testTouristFullName() {
        String fullName = tourist.getName() + " " + tourist.getSurname();
        assertEquals("Full name should be 'Mario Rossi'", "Mario Rossi", fullName);
    }

    @Test
    public void testTouristWithLongSurname() {
        String longSurname = "De Angelis-Martinelli";
        Tourist longSurnameTourist = new Tourist(
            "long@test.com", "pass", "Anna", "Address", "123456", 1, longSurname, BIRTH_DATE
        );
        assertEquals("Long surname should match", longSurname, longSurnameTourist.getSurname());
    }

    @Test
    public void testTouristWithSpecialCharacters() {
        Tourist specialTourist = new Tourist(
            "special@test.com",
            "p@ss123!",
            "François",
            "Piazza San Marco, 1",
            "+39 041 234-5678",
            5,
            "D'Angelo",
            Date.valueOf("1985-03-20")
        );
        
        assertEquals("Name with special char should match", "François", specialTourist.getName());
        assertEquals("Surname with apostrophe should match", "D'Angelo", specialTourist.getSurname());
    }

    @Test
    public void testTouristEquality() {
        Tourist tourist1 = new Tourist(EMAIL, PASSWORD, NAME, ADDRESS, PHONE, ID_CITY, SURNAME, BIRTH_DATE);
        Tourist tourist2 = new Tourist(EMAIL, PASSWORD, NAME, ADDRESS, PHONE, ID_CITY, SURNAME, BIRTH_DATE);
        
        assertEquals("Emails should match", tourist1.getEmail(), tourist2.getEmail());
        assertEquals("Names should match", tourist1.getName(), tourist2.getName());
        assertEquals("Surnames should match", tourist1.getSurname(), tourist2.getSurname());
        assertEquals("Birth dates should match", tourist1.getBirthDate(), tourist2.getBirthDate());
        assertEquals("Types should match", tourist1.getType(), tourist2.getType());
    }

    @Test
    public void testTouristDifferentFromCompany() {
        Company company = new Company(EMAIL, PASSWORD, ADDRESS, PHONE, ID_CITY, "Company Name");
        
        assertNotEquals("Tourist type should differ from Company type", 
            tourist.getType(), company.getType());
    }
}
