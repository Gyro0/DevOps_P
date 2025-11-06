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
 * Unit test for Booking class
 * Tests constructor, getters, and JSON serialization/deserialization
 * 
 * @author YourWayToItaly Test Team
 * @version 1.0
 * @since 1.0
 */
public class BookingTest {

    private Booking booking;
    private static final String EMAIL_TOURIST = "tourist@test.com";
    private static final int ID_ADVERTISEMENT = 1;
    private static final Date DATE = Date.valueOf("2024-06-15");
    private static final Time TIME = Time.valueOf("10:30:00");
    private static final int NUM_BOOKING = 2;
    private static final String STATE = "CONFIRMED";

    @Before
    public void setUp() {
        booking = new Booking(EMAIL_TOURIST, ID_ADVERTISEMENT, DATE, TIME, NUM_BOOKING, STATE);
    }

    @Test
    public void testConstructorAndGetters() {
        assertNotNull("Booking should not be null", booking);
        assertEquals("Tourist email should match", EMAIL_TOURIST, booking.getEmailTourist());
        assertEquals("Advertisement ID should match", ID_ADVERTISEMENT, booking.getIdAdvertisement());
        assertEquals("Date should match", DATE, booking.getDate());
        assertEquals("Time should match", TIME, booking.getTime());
        assertEquals("Number of bookings should match", NUM_BOOKING, booking.getNumBooking());
        assertEquals("State should match", STATE, booking.getState());
    }

    @Test
    public void testToJSON() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        booking.toJSON(out);
        String json = out.toString();
        
        assertNotNull("JSON output should not be null", json);
        assertTrue("JSON should contain 'booking' field", json.contains("booking"));
        assertTrue("JSON should contain tourist email", json.contains(EMAIL_TOURIST));
        assertTrue("JSON should contain state", json.contains(STATE));
    }

    @Test
    public void testFromJSON() throws IOException {
        String jsonInput = "{\"booking\":{" +
            "\"emailTourist\":\"tourist@test.com\"," +
            "\"idAdvertisement\":\"1\"," +
            "\"date\":\"2024-06-15\"," +
            "\"time\":\"10:30:00\"," +
            "\"numBooking\":\"2\"," +
            "\"state\":\"CONFIRMED\"" +
            "}}";

        ByteArrayInputStream in = new ByteArrayInputStream(jsonInput.getBytes());
        Booking parsed = Booking.fromJSON(in);

        assertNotNull("Parsed booking should not be null", parsed);
        assertEquals("Parsed email should match", EMAIL_TOURIST, parsed.getEmailTourist());
        assertEquals("Parsed advertisement ID should match", ID_ADVERTISEMENT, parsed.getIdAdvertisement());
        assertEquals("Parsed state should match", STATE, parsed.getState());
    }

    @Test
    public void testBookingWithPendingState() {
        Booking pendingBooking = new Booking(
            "user@test.com", 5, DATE, TIME, 1, "PENDING"
        );
        assertEquals("State should be PENDING", "PENDING", pendingBooking.getState());
    }

    @Test
    public void testBookingWithCancelledState() {
        Booking cancelledBooking = new Booking(
            "user@test.com", 5, DATE, TIME, 3, "CANCELLED"
        );
        assertEquals("State should be CANCELLED", "CANCELLED", cancelledBooking.getState());
    }

    @Test
    public void testBookingWithSingleItem() {
        Booking singleBooking = new Booking(
            "single@test.com", 10, DATE, TIME, 1, "CONFIRMED"
        );
        assertEquals("Number of bookings should be 1", 1, singleBooking.getNumBooking());
    }

    @Test
    public void testBookingWithMultipleItems() {
        Booking multipleBooking = new Booking(
            "group@test.com", 10, DATE, TIME, 15, "CONFIRMED"
        );
        assertEquals("Number of bookings should be 15", 15, multipleBooking.getNumBooking());
    }

    @Test
    public void testBookingWithEarlyMorningTime() {
        Time earlyTime = Time.valueOf("06:00:00");
        Booking earlyBooking = new Booking(
            EMAIL_TOURIST, ID_ADVERTISEMENT, DATE, earlyTime, NUM_BOOKING, STATE
        );
        assertEquals("Early time should match", earlyTime, earlyBooking.getTime());
    }

    @Test
    public void testBookingWithLateEveningTime() {
        Time lateTime = Time.valueOf("23:59:59");
        Booking lateBooking = new Booking(
            EMAIL_TOURIST, ID_ADVERTISEMENT, DATE, lateTime, NUM_BOOKING, STATE
        );
        assertEquals("Late time should match", lateTime, lateBooking.getTime());
    }

    @Test
    public void testBookingWithDifferentDates() {
        Date pastDate = Date.valueOf("2020-01-01");
        Date futureDate = Date.valueOf("2025-12-31");
        
        Booking pastBooking = new Booking(EMAIL_TOURIST, 1, pastDate, TIME, 1, "COMPLETED");
        Booking futureBooking = new Booking(EMAIL_TOURIST, 2, futureDate, TIME, 1, "PENDING");
        
        assertEquals("Past date should match", pastDate, pastBooking.getDate());
        assertEquals("Future date should match", futureDate, futureBooking.getDate());
    }
}
