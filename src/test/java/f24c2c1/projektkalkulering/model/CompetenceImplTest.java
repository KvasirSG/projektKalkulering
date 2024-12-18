package f24c2c1.projektkalkulering.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompetenceImplTest {

    @Test
    void testSettersAndGetters() {
        // Arrange
        CompetenceImpl competence = new CompetenceImpl();

        long id = 1L;
        String name = "Test Competence";

        // Act
        competence.setId(id);
        competence.setName(name);

        // Assert
        assertEquals(id, competence.getId(), "ID should match");
        assertEquals(name, competence.getName(), "Name should match");
    }

    @Test
    void testDefaultValues() {
        // Arrange
        CompetenceImpl competence = new CompetenceImpl();

        // Assert
        assertEquals(0L, competence.getId(), "Default ID should be 0");
        assertNull(competence.getName(), "Default name should be null");
    }
}
