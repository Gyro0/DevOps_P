package it.unipd.dei.yourwaytoitaly.resource;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Unit test for Rate class
 * Tests constructor, getter, and JSON serialization
 * 
 * @author YourWayToItaly Test Team
 * @version 1.0
 * @since 1.0
 */
public class RateTest {

    private Rate rate;
    private static final int RATE_VALUE = 4;

    @Before
    public void setUp() {
        rate = new Rate(RATE_VALUE);
    }

    @Test
    public void testConstructorAndGetter() {
        assertNotNull("Rate should not be null", rate);
        assertEquals("Rate value should match", RATE_VALUE, rate.getRate());
    }

    @Test
    public void testToJSON() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        rate.toJSON(out);
        String json = out.toString();
        
        assertNotNull("JSON output should not be null", json);
        assertTrue("JSON should contain 'rate' field", json.contains("rate"));
        assertTrue("JSON should contain rate value", json.contains(String.valueOf(RATE_VALUE)));
    }

    @Test
    public void testRateWithMinimumValue() {
        Rate minRate = new Rate(1);
        assertEquals("Rate should be 1", 1, minRate.getRate());
    }

    @Test
    public void testRateWithMaximumValue() {
        Rate maxRate = new Rate(5);
        assertEquals("Rate should be 5", 5, maxRate.getRate());
    }

    @Test
    public void testRateWithZeroValue() {
        Rate zeroRate = new Rate(0);
        assertEquals("Rate should be 0", 0, zeroRate.getRate());
    }

    @Test
    public void testRateWithNegativeValue() {
        Rate negativeRate = new Rate(-1);
        assertEquals("Rate should be -1", -1, negativeRate.getRate());
    }

    @Test
    public void testAllPossibleRatingValues() {
        for (int i = 1; i <= 5; i++) {
            Rate testRate = new Rate(i);
            assertEquals("Rate should match iteration value", i, testRate.getRate());
        }
    }

    @Test
    public void testRateEquality() {
        Rate rate1 = new Rate(3);
        Rate rate2 = new Rate(3);
        
        assertEquals("Both rates should have same value", rate1.getRate(), rate2.getRate());
    }

    @Test
    public void testRateInequality() {
        Rate rate1 = new Rate(2);
        Rate rate2 = new Rate(5);
        
        assertNotEquals("Rates should have different values", rate1.getRate(), rate2.getRate());
    }
}
