package br.com.task_manager_api.services;

import br.com.task_manager_api.models.Subscription;
import br.com.task_manager_api.models.Task;
import br.com.task_manager_api.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class TaskService {

  private final TaskRepository repository;
  private EntityManager entityManager;
  //private final FirebaseMessaging fcm;
  private final SubscriptionService subscriptionService;

  public Task save(Task task) {//throws FirebaseMessagingException {
    notifyDevices(task);

    return this.repository.save(task);
  }

  private void notifyDevices(Task task) {
    if (task.getUuid() != null) {
      entityManager.detach(task);
      Optional<Task> previousTask = this.repository.findById(task.getUuid());
      if (previousTask.isPresent() && !previousTask.get().getStatus().equals(task.getStatus())) {
        /*Option 1:
        Message msg = Message.builder().setTopic(task.getUuid().toString()).putData("body", "The status of the task " + task.getTitle() + " was updated!").putData("title", "Task" + task.getTitle()).build();
        fcm.send(msg);*/

        //Option 2:
        Subscription subscription = new Subscription();
        subscription.setTask(new Task());
        subscription.getTask().setUuid(task.getUuid());

        List<String> tokens = this.subscriptionService.findBy(subscription).stream().map(Subscription::getToken).collect(Collectors.toList());

        /*MulticastMessage msg = MulticastMessage.builder().addAllTokens(tokens).putData("body", "The status of the task " + task.getTitle() + " was updated!").putData("title", "Task" + task.getTitle()).build();
        fcm.sendMulticast(msg);*/

        tokens.forEach(token -> log.info("Title: Task's status updated;Body: the status of the task {} was updated; Sending message to token: {}", task.getTitle(), token));
      }
    }
  }

  public List<Task> findAll() {
    return this.repository.findAll();
  }

  public Page<Task> findAll(Specification<Task> specification, Pageable pageable) {
    return this.repository.findAll(specification, pageable);
  }

  public Optional<Task> findById(UUID id) {
    return this.repository.findById(id);
  }

  public void deleteAll() {
    this.repository.deleteAll();
  }

  public void deleteById(UUID id) {
    this.repository.deleteById(id);
  }

  public void delete(Task task) {
    this.repository.delete(task);
  }
}
