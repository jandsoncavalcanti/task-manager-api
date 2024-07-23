package br.com.task_manager_api.services;

import br.com.task_manager_api.models.Project;
import br.com.task_manager_api.repositories.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProjectService {

  private final ProjectRepository repository;

  public Project save(Project project) {
    return this.repository.save(project);
  }

  public List<Project> findAll() {
    return this.repository.findAll();
  }

  public Page<Project> findAll(Project project, Pageable pageable) {
    return this.repository.findAll(Example.of(project), pageable);
  }

  public Optional<Project> findById(UUID id) {
    return this.repository.findById(id);
  }

  public void deleteAll() {
    this.repository.deleteAll();
  }

  public void deleteById(UUID id) {
    this.repository.deleteById(id);
  }

  public void delete(Project project) {
    this.repository.delete(project);
  }
}
