package it.unipd.dei.yourwaytoitaly.resource;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Unit test for Image class
 * Tests constructor, getters, and JSON serialization/deserialization
 * 
 * @author YourWayToItaly Test Team
 * @version 1.0
 * @since 1.0
 */
public class ImageTest {

    private Image image;
    private static final int ID_IMAGE = 1;
    private static final String PATH = "/images/tour/venice_001.jpg";
    private static final String DESCRIPTION = "Beautiful view of Venice canals";
    private static final int ID_ADVERTISEMENT = 10;

    @Before
    public void setUp() {
        image = new Image(ID_IMAGE, PATH, DESCRIPTION, ID_ADVERTISEMENT);
    }

    @Test
    public void testConstructorAndGetters() {
        assertNotNull("Image should not be null", image);
        assertEquals("Image ID should match", ID_IMAGE, image.getIdImage());
        assertEquals("Path should match", PATH, image.getPath());
        assertEquals("Description should match", DESCRIPTION, image.getDescription());
        assertEquals("Advertisement ID should match", ID_ADVERTISEMENT, image.getIdAdvertisement());
    }

    @Test
    public void testToJSON() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        image.toJSON(out);
        String json = out.toString();
        
        assertNotNull("JSON output should not be null", json);
        assertTrue("JSON should contain 'image' field", json.contains("image"));
        assertTrue("JSON should contain path", json.contains(PATH));
        assertTrue("JSON should contain description", json.contains(DESCRIPTION));
    }

    @Test
    public void testFromJSON() throws IOException {
        String jsonInput = "{\"image\":{" +
            "\"idImage\":\"1\"," +
            "\"path\":\"/images/tour/venice_001.jpg\"," +
            "\"description\":\"Beautiful view of Venice canals\"," +
            "\"idAdvertisement\":\"10\"" +
            "}}";

        ByteArrayInputStream in = new ByteArrayInputStream(jsonInput.getBytes());
        Image parsed = Image.fromJSON(in);

        assertNotNull("Parsed image should not be null", parsed);
        assertEquals("Parsed image ID should match", ID_IMAGE, parsed.getIdImage());
        assertEquals("Parsed path should match", PATH, parsed.getPath());
        assertEquals("Parsed description should match", DESCRIPTION, parsed.getDescription());
        assertEquals("Parsed advertisement ID should match", ID_ADVERTISEMENT, parsed.getIdAdvertisement());
    }

    @Test
    public void testImageWithDifferentPaths() {
        Image jpg = new Image(1, "/images/photo.jpg", "JPEG image", 1);
        Image png = new Image(2, "/images/photo.png", "PNG image", 1);
        Image gif = new Image(3, "/images/photo.gif", "GIF image", 1);
        
        assertTrue("JPG path should end with .jpg", jpg.getPath().endsWith(".jpg"));
        assertTrue("PNG path should end with .png", png.getPath().endsWith(".png"));
        assertTrue("GIF path should end with .gif", gif.getPath().endsWith(".gif"));
    }

    @Test
    public void testImageWithEmptyDescription() {
        Image emptyDesc = new Image(5, "/path/to/image.jpg", "", 1);
        assertEquals("Description should be empty", "", emptyDesc.getDescription());
    }

    @Test
    public void testImageWithLongDescription() {
        String longDesc = "This is a very detailed description of the image that contains " +
            "information about the location, time of day, photographer, and the historical " +
            "significance of the scene captured in this photograph.";
        
        Image longDescImage = new Image(6, "/images/historic.jpg", longDesc, 1);
        assertEquals("Long description should match", longDesc, longDescImage.getDescription());
    }

    @Test
    public void testImageWithRelativePath() {
        Image relativeImage = new Image(7, "images/local/photo.jpg", "Relative path", 1);
        assertFalse("Relative path should not start with /", 
            relativeImage.getPath().startsWith("/"));
    }

    @Test
    public void testImageWithAbsolutePath() {
        Image absoluteImage = new Image(8, "/var/www/images/photo.jpg", "Absolute path", 1);
        assertTrue("Absolute path should start with /", 
            absoluteImage.getPath().startsWith("/"));
    }

    @Test
    public void testMultipleImagesForSameAdvertisement() {
        Image image1 = new Image(1, "/img1.jpg", "First image", 100);
        Image image2 = new Image(2, "/img2.jpg", "Second image", 100);
        Image image3 = new Image(3, "/img3.jpg", "Third image", 100);
        
        assertEquals("All images should have same advertisement ID", 
            image1.getIdAdvertisement(), image2.getIdAdvertisement());
        assertEquals("All images should have same advertisement ID", 
            image2.getIdAdvertisement(), image3.getIdAdvertisement());
        
        assertNotEquals("Image IDs should be different", 
            image1.getIdImage(), image2.getIdImage());
    }

    @Test
    public void testImageWithSpecialCharactersInPath() {
        Image specialImage = new Image(9, "/images/città/venezia-2024.jpg", 
            "Image with special chars", 1);
        assertTrue("Path should contain special character", 
            specialImage.getPath().contains("à"));
    }

    @Test
    public void testImageWithNullDescription() {
        Image nullDescImage = new Image(10, "/images/test.jpg", null, 1);
        assertNull("Description should be null", nullDescImage.getDescription());
    }

    @Test
    public void testImageIdSequence() {
        Image img1 = new Image(1, "/path1.jpg", "Desc1", 1);
        Image img2 = new Image(2, "/path2.jpg", "Desc2", 1);
        Image img3 = new Image(3, "/path3.jpg", "Desc3", 1);
        
        assertEquals("Image 1 ID should be 1", 1, img1.getIdImage());
        assertEquals("Image 2 ID should be 2", 2, img2.getIdImage());
        assertEquals("Image 3 ID should be 3", 3, img3.getIdImage());
        
        assertTrue("IDs should be sequential", 
            img2.getIdImage() == img1.getIdImage() + 1 &&
            img3.getIdImage() == img2.getIdImage() + 1);
    }
}
