import org.junit.jupiter.api.Test;
import petrov.dev.Contact;
import static org.junit.jupiter.api.Assertions.*;

class ContactTest {

    @Test
    void testValidAccessCode() {
        Contact contact = new Contact("John", "Doe", "00F1C313");
        assertEquals("00F1C313", contact.getAccessCode());
    }

    @Test
    void testInvalidAccessCode() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Contact("John", "Doe", "INVALID");
        });
        assertEquals("Invalid access code format.", exception.getMessage());
    }
}