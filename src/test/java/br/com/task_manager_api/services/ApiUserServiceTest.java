package br.com.task_manager_api.services;

import br.com.task_manager_api.directors.ApiUserDirector;
import br.com.task_manager_api.models.ApiUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApiUserServiceTest {

  @Autowired
  private ApiUserService service;

  @Test
  void insertedSuccessfully() {
    assertEquals(0, this.service.findAll().size());

    this.service.save(ApiUserDirector.validExample());

    assertEquals(1, this.service.findAll().size());
  }

  @Test
  void insertedUnsuccessfully() {
    ApiUser apiUser = ApiUserDirector.invalidExample();

    assertEquals(0, this.service.findAll().size());

    assertThrows(TransactionSystemException.class, () -> this.service.save(apiUser));

    assertEquals(0, this.service.findAll().size());
  }

  @Test
  void deletedSuccessfully() {
    ApiUser apiUser = this.service.save(ApiUserDirector.validExample());

    assertEquals(1, this.service.findAll().size());

    assertDoesNotThrow(() -> this.service.deleteById(apiUser.getUuid()));

    assertEquals(0, this.service.findAll().size());
  }

  @AfterEach
  void afterEach() {
    this.service.deleteAll();
  }
}
