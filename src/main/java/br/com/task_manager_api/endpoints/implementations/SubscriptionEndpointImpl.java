package br.com.task_manager_api.endpoints.implementations;

import br.com.task_manager_api.dtos.SubscriptionDto;
import br.com.task_manager_api.endpoints.interfaces.SubscriptionEndpoint;
import br.com.task_manager_api.mappers.SubscriptionMapper;
import br.com.task_manager_api.models.ApiUser;
import br.com.task_manager_api.models.Subscription;
import br.com.task_manager_api.models.Task;
import br.com.task_manager_api.services.ApiUserService;
import br.com.task_manager_api.services.SubscriptionService;
import br.com.task_manager_api.services.TaskService;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Log4j2
public class SubscriptionEndpointImpl implements SubscriptionEndpoint {

  private final SubscriptionService service;
  private final TaskService taskService;
  private final ApiUserService apiUserService;

  @Override
  public ResponseEntity<Object> insert(SubscriptionDto subscriptionDto) {
    Optional<ApiUser> apiUser = this.apiUserService.findById(subscriptionDto.getApiUserID());

    if (!apiUser.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    Optional<Task> task = this.taskService.findById(subscriptionDto.getTaskID());

    if (!task.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
    }

    Subscription subscription = SubscriptionMapper.INSTANCE.toModel(subscriptionDto);
    subscription.setApiUser(apiUser.get());
    subscription.setTask(task.get());

    if (!this.service.findBy(subscription).isEmpty()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Subscription already exists");
    }

    try {
      return ResponseEntity.status(HttpStatus.OK).body(this.service.save(subscription));
    } catch (Exception e) {
      log.error(e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Subscription could not be inserted");
    }
  }

  @Override
  public ResponseEntity<Page<Subscription>> page(SubscriptionDto subscriptionDto, Pageable pageable) {
    Subscription subscription = SubscriptionMapper.INSTANCE.toModel(subscriptionDto);
    return ResponseEntity.ok().body(this.service.findBy(subscription, pageable));
  }

  @Override
  public ResponseEntity<String> delete(UUID id) {
    Optional<Subscription> subscription = this.service.findById(id);

    if (!subscription.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscription not found");
    }

    try {
      this.service.deleteById(id);
    } catch (Exception e) {
      log.error(e);
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Subscription could not be deleted");
    }

    return ResponseEntity.ok().body("Subscription deleted successfully");
  }
}
