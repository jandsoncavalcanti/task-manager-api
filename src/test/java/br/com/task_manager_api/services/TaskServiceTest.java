package br.com.task_manager_api.services;

import br.com.task_manager_api.directors.TaskDirector;
import br.com.task_manager_api.models.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServiceTest {

  @Autowired
  private TaskService service;

  @Test
  void insertedSuccessfully() {
    assertEquals(0, this.service.findAll().size());

    this.service.save(TaskDirector.basicValidExample());

    assertEquals(1, this.service.findAll().size());
  }

  @Test
  void insertedUnsuccessfully() {
    Task task = TaskDirector.noPriorityExample();

    assertEquals(0, this.service.findAll().size());

    assertThrows(TransactionSystemException.class, () -> this.service.save(task));

    assertEquals(0, this.service.findAll().size());
  }

  @Test
  void deletedSuccessfully() {
    Task task = this.service.save(TaskDirector.basicValidExample());

    assertEquals(1, this.service.findAll().size());

    assertDoesNotThrow(() -> this.service.deleteById(task.getUuid()));

    assertEquals(0, this.service.findAll().size());
  }

  @AfterEach
  void afterEach() {
    this.service.deleteAll();
  }
}
