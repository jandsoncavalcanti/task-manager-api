package br.com.task_manager_api.services;

import br.com.task_manager_api.directors.ApiUserDirector;
import br.com.task_manager_api.directors.TaskDirector;
import br.com.task_manager_api.models.ApiUser;
import br.com.task_manager_api.models.Subscription;
import br.com.task_manager_api.models.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SubscriptionServiceTest {

  @Autowired
  private TaskService taskService;
  @Autowired
  private ApiUserService apiUserService;
  @Autowired
  private SubscriptionService service;

  @Test
  void insertedSuccessfully() {
    Task task = taskService.save(TaskDirector.basicValidExample());
    ApiUser apiUser = apiUserService.save(ApiUserDirector.validExample());

    Subscription subscription = new Subscription();
    subscription.setTask(task);
    subscription.setApiUser(apiUser);
    subscription.setToken("Token 1");

    assertEquals(0, this.service.findAll().size());

    service.save(subscription);

    assertEquals(1, this.service.findAll().size());
  }

  @Test
  void insertedUnsuccessfully() {
    Subscription subscription = new Subscription();

    assertEquals(0, this.service.findAll().size());

    assertThrows(TransactionSystemException.class, () -> this.service.save(subscription));

    assertEquals(0, this.service.findAll().size());
  }


  @Test
  void deletedSuccessfully() {
    Task task = taskService.save(TaskDirector.basicValidExample());
    ApiUser apiUser = apiUserService.save(ApiUserDirector.validExample());

    Subscription subscription = new Subscription();
    subscription.setTask(task);
    subscription.setApiUser(apiUser);
    subscription.setToken("Token 1");

    service.save(subscription);

    assertEquals(1, this.service.findAll().size());

    assertDoesNotThrow(() -> this.service.deleteById(subscription.getUuid()));

    assertEquals(0, this.service.findAll().size());
  }

  @AfterEach
  void afterEach() {
    this.service.deleteAll();
  }
}
