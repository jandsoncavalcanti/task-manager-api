package br.com.task_manager_api.services;

import br.com.task_manager_api.directors.ProjectDirector;
import br.com.task_manager_api.models.Project;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectServiceTest {

  @Autowired
  private ProjectService service;

  @Test
  void insertedSuccessfully() {
    assertEquals(0, this.service.findAll().size());

    this.service.save(ProjectDirector.validExample());

    assertEquals(1, this.service.findAll().size());
  }

  @Test
  void insertedUnsuccessfully() {
    Project project = ProjectDirector.invalidExample();

    assertEquals(0, this.service.findAll().size());

    assertThrows(TransactionSystemException.class, () -> this.service.save(project));

    assertEquals(0, this.service.findAll().size());
  }

  @Test
  void deletedSuccessfully() {
    Project project = this.service.save(ProjectDirector.validExample());

    assertEquals(1, this.service.findAll().size());

    assertDoesNotThrow(() -> this.service.deleteById(project.getUuid()));

    assertEquals(0, this.service.findAll().size());
  }

  @AfterEach
  void afterEach() {
    this.service.deleteAll();
  }
}
