package f24c2c1.projektkalkulering.service;

import f24c2c1.projektkalkulering.model.Task;
import f24c2c1.projektkalkulering.model.TaskImpl;
import f24c2c1.projektkalkulering.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    private TaskRepository taskRepository;
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskRepository = Mockito.mock(TaskRepository.class);
        taskService = new TaskService(taskRepository);
    }

    @Test
    void testGetAllTasks() {
        Task task1 = new TaskImpl();
        task1.setId(1L);
        task1.setName("Task 1");

        Task task2 = new TaskImpl();
        task2.setId(2L);
        task2.setName("Task 2");

        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        List<Task> tasks = taskService.getAllTasks();

        assertThat(tasks).hasSize(2);
        assertThat(tasks.get(0).getName()).isEqualTo("Task 1");
        assertThat(tasks.get(1).getName()).isEqualTo("Task 2");

        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetTaskById() {
        Task task = new TaskImpl();
        task.setId(1L);
        task.setName("Test Task");

        when(taskRepository.findById(1L)).thenReturn(task);

        Task foundTask = taskService.getTaskById(1L);

        assertThat(foundTask).isNotNull();
        assertThat(foundTask.getName()).isEqualTo("Test Task");

        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTaskByIdNotFound() {
        when(taskRepository.findById(99L)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> taskService.getTaskById(99L));

        assertThat(exception.getMessage()).isEqualTo("Task with ID 99 not found");
        verify(taskRepository, times(1)).findById(99L);
    }

    @Test
    void testCreateTask() {
        Task task = new TaskImpl();
        task.setName("New Task");

        when(taskRepository.save(task)).thenReturn(1L);

        taskService.createTask(task);

        assertThat(task.getId()).isEqualTo(1L);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testUpdateTask() {
        Task task = new TaskImpl();
        task.setId(1L);
        task.setName("Updated Task");

        doAnswer(invocation -> null).when(taskRepository).update(task);

        taskService.updateTask(task);

        verify(taskRepository, times(1)).update(task);
    }

    @Test
    void testDeleteTask() {
        long taskId = 1L;

        doAnswer(invocation -> null).when(taskRepository).deleteById(taskId);

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).deleteById(taskId);
    }
}
