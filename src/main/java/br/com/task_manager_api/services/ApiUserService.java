package br.com.task_manager_api.services;

import br.com.task_manager_api.models.ApiUser;
import br.com.task_manager_api.repositories.ApiUserRepository;
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
public class ApiUserService {

  private final ApiUserRepository repository;

  public ApiUser save(ApiUser apiUser) {
    return this.repository.save(apiUser);
  }

  public List<ApiUser> findAll() {
    return this.repository.findAll();
  }

  public Page<ApiUser> findAll(ApiUser apiUser, Pageable pageable) {
    return this.repository.findAll(Example.of(apiUser), pageable);
  }

  public Optional<ApiUser> findById(UUID id) {
    return this.repository.findById(id);
  }

  public void deleteAll() {
    this.repository.deleteAll();
  }

  public void deleteById(UUID id) {
    this.repository.deleteById(id);
  }

  public void delete(ApiUser apiUser){
    this.repository.delete(apiUser);
  }

}
