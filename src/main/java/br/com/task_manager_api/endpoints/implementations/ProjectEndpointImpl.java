package br.com.task_manager_api.endpoints.implementations;

import br.com.task_manager_api.dtos.ProjectDto;
import br.com.task_manager_api.endpoints.interfaces.ProjectEndpoint;
import br.com.task_manager_api.mappers.ProjectMapper;
import br.com.task_manager_api.models.Project;
import br.com.task_manager_api.services.ProjectService;
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
public class ProjectEndpointImpl implements ProjectEndpoint {

  private static final String PROJECT_NOT_FOUND = "Project not found";
  private final ProjectService service;

  @Override
  public ResponseEntity<Object> insert(ProjectDto projectDto) {
    try {
      Project project = ProjectMapper.INSTANCE.toModel(projectDto);
      return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(project));
    } catch (Exception e) {
      log.error(e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Project could not be inserted");
    }
  }

  @Override
  public ResponseEntity<Object> update(UUID id, ProjectDto projectDto) {
    Optional<Project> project = this.service.findById(id);

    if (!project.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PROJECT_NOT_FOUND);
    }

    try {
      Project projectToPersist = ProjectMapper.INSTANCE.toModel(projectDto, project.get());
      return ResponseEntity.ok().body(this.service.save(projectToPersist));
    } catch (Exception e) {
      log.error(e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Project could not be updated");
    }
  }

  @Override
  public ResponseEntity<Page<Project>> page(ProjectDto projectDto, Pageable pageable) {
    Project project = ProjectMapper.INSTANCE.toModel(projectDto);
    return ResponseEntity.ok().body(this.service.findAll(project, pageable));
  }

  @Override
  public ResponseEntity<String> delete(UUID id) {
    Optional<Project> project = this.service.findById(id);

    if (!project.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PROJECT_NOT_FOUND);
    }

    try {
      this.service.delete(project.get());
    } catch (Exception e) {
      log.error(e);
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Project could not be deleted");
    }

    return ResponseEntity.ok().body("Project deleted successfully");
  }
}
