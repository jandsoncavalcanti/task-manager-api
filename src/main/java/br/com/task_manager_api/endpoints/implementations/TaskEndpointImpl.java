package br.com.task_manager_api.endpoints.implementations;

import br.com.task_manager_api.dtos.TaskDto;
import br.com.task_manager_api.endpoints.interfaces.TaskEndpoint;
import br.com.task_manager_api.mappers.TaskMapper;
import br.com.task_manager_api.models.ApiUser;
import br.com.task_manager_api.models.Project;
import br.com.task_manager_api.models.Task;
import br.com.task_manager_api.services.ApiUserService;
import br.com.task_manager_api.services.ProjectService;
import br.com.task_manager_api.services.TaskService;
import br.com.task_manager_api.specifications.TaskFilterSpecification;
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
public class TaskEndpointImpl implements TaskEndpoint {

  private static final String TASK_NOT_FOUND = "Task not found";
  private static final String TASK_COULD_NOT_BE_UPDATED = "Task could not be updated";
  private static final String PROJECT_NOT_FOUND = "Project not found";
  private static final String USER_NOT_FOUND = "User not found";

  private final TaskService service;
  private final ProjectService projectService;
  private final ApiUserService apiUserService;

  @Override
  public ResponseEntity<Object> insert(TaskDto taskDto) {
    Optional<Project> project = Optional.empty();
    Optional<ApiUser> apiUser = Optional.empty();

    if (taskDto.getProjectID() != null) {
      project = this.projectService.findById(taskDto.getProjectID());

      if (!project.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PROJECT_NOT_FOUND);
      }
    }

    if (taskDto.getAssignedToID() != null) {
      apiUser = this.apiUserService.findById(taskDto.getAssignedToID());

      if (!apiUser.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND);
      }
    }

    try {
      Task task = TaskMapper.INSTANCE.toModel(taskDto);
      task.setProject(project.orElse(null));
      task.setAssignedTo(apiUser.orElse(null));
      return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(task));
    } catch (Exception e) {
      log.error(e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Task could not be inserted");
    }
  }

  @Override
  public ResponseEntity<Object> update(UUID id, TaskDto taskDto) {
    Optional<Task> task = this.service.findById(id);

    if (!task.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TASK_NOT_FOUND);
    }

    Optional<Project> project = Optional.empty();
    Optional<ApiUser> apiUser = Optional.empty();

    if (taskDto.getProjectID() != null) {
      project = this.projectService.findById(taskDto.getProjectID());

      if (!project.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PROJECT_NOT_FOUND);
      }
    }

    if (taskDto.getAssignedToID() != null) {
      apiUser = this.apiUserService.findById(taskDto.getAssignedToID());

      if (!apiUser.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND);
      }
    }

    try {
      Task taskToPersist = TaskMapper.INSTANCE.toModel(taskDto, task.get());
      taskToPersist.setProject(project.orElse(null));
      taskToPersist.setAssignedTo(apiUser.orElse(null));
      return ResponseEntity.ok().body(this.service.save(taskToPersist));
    } catch (Exception e) {
      log.error(e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(TASK_COULD_NOT_BE_UPDATED);
    }
  }

  @Override
  public ResponseEntity<Object> changeStatus(UUID id, TaskDto taskDto) {
    Optional<Task> task = this.service.findById(id);

    if (!task.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TASK_NOT_FOUND);
    }

    try {
      task.get().setStatus(taskDto.getStatus());
      return ResponseEntity.ok().body(this.service.save(task.get()));
    } catch (Exception e) {
      log.error(e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(TASK_COULD_NOT_BE_UPDATED);
    }
  }

  @Override
  public ResponseEntity<Object> changePriority(UUID id, TaskDto taskDto) {
    Optional<Task> task = this.service.findById(id);

    if (!task.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TASK_NOT_FOUND);
    }

    try {
      task.get().setPriority(taskDto.getPriority());
      return ResponseEntity.ok().body(this.service.save(task.get()));
    } catch (Exception e) {
      log.error(e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(TASK_COULD_NOT_BE_UPDATED);
    }
  }

  @Override
  public ResponseEntity<Object> changeProject(UUID id, TaskDto taskDto) {
    Optional<Task> task = this.service.findById(id);

    if (!task.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TASK_NOT_FOUND);
    }

    Optional<Project> project = this.projectService.findById(taskDto.getProjectID());

    if (!project.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PROJECT_NOT_FOUND);
    }

    try {
      task.get().setProject(project.get());
      return ResponseEntity.ok().body(this.service.save(task.get()));
    } catch (Exception e) {
      log.error(e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(TASK_COULD_NOT_BE_UPDATED);
    }
  }

  @Override
  public ResponseEntity<Object> changeAssignedTo(UUID id, TaskDto taskDto) {
    Optional<Task> task = this.service.findById(id);

    if (!task.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TASK_NOT_FOUND);
    }

    Optional<ApiUser> user = this.apiUserService.findById(taskDto.getAssignedToID());

    if (!user.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND);
    }

    try {
      task.get().setAssignedTo(user.get());
      return ResponseEntity.ok().body(this.service.save(task.get()));
    } catch (Exception e) {
      log.error(e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(TASK_COULD_NOT_BE_UPDATED);
    }
  }

  @Override
  public ResponseEntity<Page<Task>> page(TaskDto taskDto, Pageable pageable) {
    return ResponseEntity.ok().body(this.service.findAll(new TaskFilterSpecification().search(taskDto), pageable));
  }

  @Override
  public ResponseEntity<String> delete(UUID id) {
    Optional<Task> task = this.service.findById(id);

    if (!task.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TASK_NOT_FOUND);
    }

    try {
      this.service.delete(task.get());
    } catch (Exception e) {
      log.error(e);
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Task could not be deleted");
    }

    return ResponseEntity.ok().body("Task deleted successfully");
  }
}
