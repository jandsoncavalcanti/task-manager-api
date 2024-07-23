package br.com.task_manager_api.endpoints.implementations;

import br.com.task_manager_api.dtos.ApiUserDto;
import br.com.task_manager_api.endpoints.interfaces.ApiUserEndpoint;
import br.com.task_manager_api.mappers.ApiUserMapper;
import br.com.task_manager_api.models.ApiUser;
import br.com.task_manager_api.services.ApiUserService;
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
public class ApiUserEndpointImpl implements ApiUserEndpoint {

  private static final String USER_NOT_FOUND = "User not found";
  private final ApiUserService service;

  @Override
  public ResponseEntity<Object> insert(ApiUserDto apiUserDto) {
    try {
      ApiUser apiUser = ApiUserMapper.INSTANCE.toModel(apiUserDto);
      return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(apiUser));
    } catch (Exception e) {
      log.error(e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User could not be inserted");
    }
  }

  @Override
  public ResponseEntity<Object> update(UUID id, ApiUserDto apiUserDto) {
    Optional<ApiUser> apiUser = this.service.findById(id);

    if (!apiUser.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND);
    }

    try {
      ApiUser user = ApiUserMapper.INSTANCE.toModel(apiUserDto, apiUser.get());
      return ResponseEntity.ok().body(this.service.save(user));
    } catch (Exception e) {
      log.error(e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User could not be updated");
    }
  }

  @Override
  public ResponseEntity<Page<ApiUser>> page(ApiUserDto apiUserDto, Pageable pageable) {
    ApiUser apiUser = ApiUserMapper.INSTANCE.toModel(apiUserDto);
    return ResponseEntity.ok().body(this.service.findAll(apiUser, pageable));
  }

  @Override
  public ResponseEntity<String> delete(UUID id) {
    Optional<ApiUser> apiUser = this.service.findById(id);

    if (!apiUser.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND);
    }

    try {
      this.service.delete(apiUser.get());
    } catch (Exception e) {
      log.error(e);
      return ResponseEntity.status(HttpStatus.CONFLICT).body("User could not be deleted");
    }

    return ResponseEntity.ok().body("User deleted successfully");
  }
}
