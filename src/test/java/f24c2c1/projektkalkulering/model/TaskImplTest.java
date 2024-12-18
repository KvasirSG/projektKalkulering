package f24c2c1.projektkalkulering.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskImplTest {

    @Test
    void testSettersAndGetters() {
        // Arrange
        TaskImpl task = new TaskImpl();
        long id = 1L;
        String name = "Test Task";
        String description = "Test Description";
        LocalDate creationDate = LocalDate.of(2024, 12, 1);
        LocalDate startDate = LocalDate.of(2024, 12, 2);
        LocalDate endDate = LocalDate.of(2024, 12, 3);
        int estimate = 5; // in hours
        String status = "In Progress";
        long parentId = 10L;
        Boolean isSubTask = true;
        List<Task> subTasks = new ArrayList<>();

        // Act
        task.setId(id);
        task.setName(name);
        task.setDescription(description);
        task.setCreationDate(creationDate);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setEstimate(estimate);
        task.setStatus(status);
        task.setParentId(parentId);
        task.setIsSubTask(isSubTask);
        task.setSubTasks(subTasks);

        // Assert
        assertEquals(id, task.getId(), "ID should match");
        assertEquals(name, task.getName(), "Name should match");
        assertEquals(description, task.getDescription(), "Description should match");
        assertEquals(creationDate, task.getCreationDate(), "Creation date should match");
        assertEquals(startDate, task.getStartDate(), "Start date should match");
        assertEquals(endDate, task.getEndDate(), "End date should match");
        assertEquals(estimate, task.getEstimate(), "Estimate should match");
        assertEquals(status, task.getStatus(), "Status should match");
        assertEquals(parentId, task.getParentId(), "Parent ID should match");
        assertEquals(isSubTask, task.getIsSubTask(), "IsSubTask flag should match");
        assertEquals(subTasks, task.getSubTasks(), "Subtasks should match");
    }

    @Test
    void testDefaultValues() {
        // Arrange
        TaskImpl task = new TaskImpl();

        // Assert
        assertEquals(0L, task.getId(), "Default ID should be 0");
        assertNull(task.getName(), "Default name should be null");
        assertNull(task.getDescription(), "Default description should be null");
        assertNull(task.getCreationDate(), "Default creation date should be null");
        assertNull(task.getStartDate(), "Default start date should be null");
        assertNull(task.getEndDate(), "Default end date should be null");
        assertEquals(0, task.getEstimate(), "Default estimate should be 0");
        assertNull(task.getStatus(), "Default status should be null");
        assertNull(task.getIsSubTask(), "Default isSubTask should be null");
        assertEquals(0L, task.getParentId(), "Default parent ID should be 0");
        assertTrue(task.getSubTasks().isEmpty(), "Default subtasks list should be empty");
    }

    @Test
    void testSubTasksManagement() {
        // Arrange
        TaskImpl task = new TaskImpl();
        TaskImpl subTask1 = new TaskImpl();
        TaskImpl subTask2 = new TaskImpl();
        subTask1.setName("SubTask 1");
        subTask2.setName("SubTask 2");

        List<Task> subTasks = new ArrayList<>();
        subTasks.add(subTask1);
        subTasks.add(subTask2);

        // Act
        task.setSubTasks(subTasks);

        // Assert
        assertEquals(2, task.getSubTasks().size(), "Subtasks size should match");
        assertEquals(subTask1, task.getSubTasks().get(0), "First subtask should match");
        assertEquals(subTask2, task.getSubTasks().get(1), "Second subtask should match");
    }

    @Test
    void testSubTaskFlag() {
        // Arrange
        TaskImpl task = new TaskImpl();

        // Act
        task.setIsSubTask(true);

        // Assert
        assertTrue(task.getIsSubTask(), "Task should be marked as a subtask");
    }
}
