package br.com.task_manager_api.endpoints.interfaces;

import br.com.task_manager_api.dtos.ProjectDto;
import br.com.task_manager_api.models.Project;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/project")
public interface ProjectEndpoint {

  @PostMapping
  ResponseEntity<Object> insert(@RequestBody @Validated(ProjectDto.ProjectView.PostView.class) @JsonView(ProjectDto.ProjectView.PostView.class) ProjectDto projectDto);

  @PutMapping(value = "/{id}")
  ResponseEntity<Object> update(@PathVariable UUID id,
                                @RequestBody @Validated(ProjectDto.ProjectView.PutView.class) @JsonView(ProjectDto.ProjectView.PutView.class) ProjectDto projectDto);

  @GetMapping
  ResponseEntity<Page<Project>> page(@JsonView(ProjectDto.ProjectView.GetView.class) ProjectDto projectDto,
                                     @PageableDefault(page = 0, size = 10, sort = "uuid", direction = Sort.Direction.ASC) Pageable pageable);

  @DeleteMapping(value = "/{id}")
  ResponseEntity<String> delete(@PathVariable UUID id);
}
