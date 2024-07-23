package br.com.task_manager_api.endpoints.interfaces;

import br.com.task_manager_api.dtos.TaskDto;
import br.com.task_manager_api.models.Task;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/task")
public interface TaskEndpoint {

  @PostMapping
  ResponseEntity<Object> insert(@RequestBody @Validated(TaskDto.TaskView.PostView.class) @JsonView(TaskDto.TaskView.PostView.class) TaskDto taskDto);

  @PutMapping(value = "/{id}")
  ResponseEntity<Object> update(@PathVariable UUID id,
                                @RequestBody @Validated(TaskDto.TaskView.PutView.class) @JsonView(TaskDto.TaskView.PutView.class) TaskDto taskDto);

  @PatchMapping(value = "status/{id}")
  ResponseEntity<Object> changeStatus(@PathVariable UUID id,
                                @RequestBody @Validated(TaskDto.TaskView.StatusView.class) @JsonView(TaskDto.TaskView.StatusView.class) TaskDto taskDto);

  @PatchMapping(value = "priority/{id}")
  ResponseEntity<Object> changePriority(@PathVariable UUID id,
                                      @RequestBody @Validated(TaskDto.TaskView.PriorityView.class) @JsonView(TaskDto.TaskView.PriorityView.class) TaskDto taskDto);

  @PatchMapping(value = "project/{id}")
  ResponseEntity<Object> changeProject(@PathVariable UUID id,
                                       @RequestBody @Validated(TaskDto.TaskView.ProjectView.class) @JsonView(TaskDto.TaskView.ProjectView.class) TaskDto taskDto);

  @PatchMapping(value = "assignedTo/{id}")
  ResponseEntity<Object> changeAssignedTo(@PathVariable UUID id,
                                        @RequestBody @Validated(TaskDto.TaskView.AssignView.class) @JsonView(TaskDto.TaskView.AssignView.class) TaskDto taskDto);

  @GetMapping
  ResponseEntity<Page<Task>> page(@JsonView(TaskDto.TaskView.GetView.class) TaskDto taskDto,
                                  @PageableDefault(page = 0, size = 10, sort = "priority", direction = Sort.Direction.DESC) Pageable pageable);

  @DeleteMapping(value = "/{id}")
  ResponseEntity<String> delete(@PathVariable UUID id);
}
