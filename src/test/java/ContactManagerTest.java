import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import petrov.dev.Contact;
import petrov.dev.ContactManager;

import static org.junit.jupiter.api.Assertions.*;

class ContactManagerTest {
    private ContactManager contactManager;

    @BeforeEach
    void setUp() {
        contactManager = new ContactManager();
    }

    @AfterEach
    void tearDown() {
        contactManager.saveContacts();
    }

    @Test
    void testAddContact() {
        contactManager.addContact("John", "Doe", "00F1C313");
        assertEquals(1, contactManager.getContacts().size());
    }

    @Test
    void testSearchContact() {
        contactManager.addContact("John", "Doe", "00F1C313");
        Contact contact = contactManager.searchContact("00F1C313");
        assertNotNull(contact);
        assertEquals("John", contact.getFirstName());
    }

    @Test
    void testDeleteContact() {
        contactManager.addContact("John", "Doe", "00F1C313");
        assertTrue(contactManager.deleteContact("00F1C313"));
        assertNull(contactManager.searchContact("00F1C313"));
    }
}