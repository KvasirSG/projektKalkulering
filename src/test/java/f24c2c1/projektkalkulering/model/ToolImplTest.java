package f24c2c1.projektkalkulering.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ToolImplTest {

    @Test
    void testSettersAndGetters() {
        // Arrange
        ToolImpl tool = new ToolImpl();
        long id = 1L;
        String name = "Hammer";
        double value = 29.99;

        // Act
        tool.setId(id);
        tool.setName(name);
        tool.setValue(value);

        // Assert
        assertEquals(id, tool.getId(), "ID should match");
        assertEquals(name, tool.getName(), "Name should match");
        assertEquals(value, tool.getValue(), "Value should match");
    }

    @Test
    void testDefaultValues() {
        // Arrange
        ToolImpl tool = new ToolImpl();

        // Assert
        assertEquals(0L, tool.getId(), "Default ID should be 0");
        assertNull(tool.getName(), "Default name should be null");
        assertEquals(0.0, tool.getValue(), "Default value should be 0.0");
    }

    @Test
    void testSetValueWithNegativeValue() {
        // Arrange
        ToolImpl tool = new ToolImpl();

        // Act
        tool.setValue(-10.0);

        // Assert
        assertEquals(-10.0, tool.getValue(), "Value should allow negative numbers");
    }
}
