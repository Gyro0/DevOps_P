package it.unipd.dei.yourwaytoitaly.resource;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for Company class
 * Tests constructor, getters, and type identification
 * 
 * @author YourWayToItaly Test Team
 * @version 1.0
 * @since 1.0
 */
public class CompanyTest {

    private Company company;
    private static final String EMAIL = "company@test.com";
    private static final String PASSWORD = "securePassword123";
    private static final String ADDRESS = "123 Main St, Venice";
    private static final String PHONE = "+39 041 1234567";
    private static final int ID_CITY = 5;
    private static final String NAME = "Venice Tours Inc.";

    @Before
    public void setUp() {
        company = new Company(EMAIL, PASSWORD, ADDRESS, PHONE, ID_CITY, NAME);
    }

    @Test
    public void testConstructorAndGetters() {
        assertNotNull("Company should not be null", company);
        assertEquals("Email should match", EMAIL, company.getEmail());
        assertEquals("Password should match", PASSWORD, company.getPassword());
        assertEquals("Address should match", ADDRESS, company.getAddress());
        assertEquals("Phone number should match", PHONE, company.getPhoneNumber());
        assertEquals("City ID should match", ID_CITY, company.getIdCity());
        assertEquals("Name should match", NAME, company.getName());
    }

    @Test
    public void testGetType() {
        assertEquals("Type should be 'Company'", "Company", company.getType());
    }

    @Test
    public void testCompanyIsInstanceOfUser() {
        assertTrue("Company should be instance of User", company instanceof User);
    }

    @Test
    public void testCompanyWithEmptyName() {
        Company emptyNameCompany = new Company(
            "test@email.com", "pass123", "Address", "123456", 1, ""
        );
        assertEquals("Name should be empty string", "", emptyNameCompany.getName());
    }

    @Test
    public void testCompanyWithLongAddress() {
        String longAddress = "Via Roma, 123, Appartamento 45, Scala B, Piano 3, 30100 Venezia, Italia";
        Company longAddressCompany = new Company(
            "long@email.com", "pass123", longAddress, "123456", 1, "Test Company"
        );
        assertEquals("Long address should match", longAddress, longAddressCompany.getAddress());
    }

    @Test
    public void testCompanyWithDifferentCityIds() {
        Company company1 = new Company(EMAIL, PASSWORD, ADDRESS, PHONE, 1, NAME);
        Company company2 = new Company(EMAIL, PASSWORD, ADDRESS, PHONE, 100, NAME);
        
        assertEquals("City ID 1 should match", 1, company1.getIdCity());
        assertEquals("City ID 100 should match", 100, company2.getIdCity());
        assertNotEquals("City IDs should be different", 
            company1.getIdCity(), company2.getIdCity());
    }

    @Test
    public void testCompanyEquality() {
        Company company1 = new Company(EMAIL, PASSWORD, ADDRESS, PHONE, ID_CITY, NAME);
        Company company2 = new Company(EMAIL, PASSWORD, ADDRESS, PHONE, ID_CITY, NAME);
        
        assertEquals("Emails should match", company1.getEmail(), company2.getEmail());
        assertEquals("Names should match", company1.getName(), company2.getName());
        assertEquals("Types should match", company1.getType(), company2.getType());
    }

    @Test
    public void testCompanyWithSpecialCharacters() {
        Company specialCompany = new Company(
            "special@test.com",
            "p@ssw0rd!",
            "Via dell'Università, 5",
            "+39 041 234-5678",
            3,
            "L'Azienda Turistica S.r.l."
        );
        
        assertEquals("Name with special chars should match", 
            "L'Azienda Turistica S.r.l.", specialCompany.getName());
        assertEquals("Address with apostrophe should match",
            "Via dell'Università, 5", specialCompany.getAddress());
    }
}
