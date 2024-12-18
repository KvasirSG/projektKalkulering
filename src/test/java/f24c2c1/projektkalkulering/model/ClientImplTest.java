package f24c2c1.projektkalkulering.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientImplTest {

    @Test
    void testClientImplSettersAndGetters() {
        // Arrange
        ClientImpl client = new ClientImpl();

        long id = 1L;
        String name = "Test Client";
        String contactName = "John Doe";
        String address = "123 Main Street";
        String city = "Test City";
        String zip = "12345";
        String phone = "123-456-7890";
        String email = "test@example.com";

        // Act
        client.setId(id);
        client.setName(name);
        client.setContactName(contactName);
        client.setAddress(address);
        client.setCity(city);
        client.setZip(zip);
        client.setPhone(phone);
        client.setEmail(email);

        // Assert
        assertEquals(id, client.getId(), "ID should match");
        assertEquals(name, client.getName(), "Name should match");
        assertEquals(contactName, client.getContactName(), "Contact Name should match");
        assertEquals(address, client.getAddress(), "Address should match");
        assertEquals(city, client.getCity(), "City should match");
        assertEquals(zip, client.getZip(), "ZIP should match");
        assertEquals(phone, client.getPhone(), "Phone should match");
        assertEquals(email, client.getEmail(), "Email should match");
    }

    @Test
    void testDefaultValues() {
        // Arrange
        ClientImpl client = new ClientImpl();

        // Assert
        assertEquals(0L, client.getId(), "Default ID should be 0");
        assertNull(client.getName(), "Default name should be null");
        assertNull(client.getContactName(), "Default contact name should be null");
        assertNull(client.getAddress(), "Default address should be null");
        assertNull(client.getCity(), "Default city should be null");
        assertNull(client.getZip(), "Default ZIP should be null");
        assertNull(client.getPhone(), "Default phone should be null");
        assertNull(client.getEmail(), "Default email should be null");
    }
}
