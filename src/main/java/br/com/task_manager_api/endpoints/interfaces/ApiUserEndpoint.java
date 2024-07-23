package br.com.task_manager_api.endpoints.interfaces;

import br.com.task_manager_api.dtos.ApiUserDto;
import br.com.task_manager_api.models.ApiUser;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/user")
public interface ApiUserEndpoint {

  @PostMapping
  ResponseEntity<Object> insert(@RequestBody @Validated(ApiUserDto.ApiUserView.PostView.class) @JsonView(ApiUserDto.ApiUserView.PostView.class) ApiUserDto apiUserDto);

  @PutMapping(value = "/{id}")
  ResponseEntity<Object> update(@PathVariable UUID id,
                                       @RequestBody @Validated(ApiUserDto.ApiUserView.PutView.class) @JsonView(ApiUserDto.ApiUserView.PutView.class) ApiUserDto apiUserDto);

  @GetMapping
  ResponseEntity<Page<ApiUser>> page(@JsonView(ApiUserDto.ApiUserView.GetView.class) ApiUserDto apiUserDto,
                                     @PageableDefault(page = 0, size = 10, sort = "uuid", direction = Sort.Direction.ASC) Pageable pageable);

  @DeleteMapping(value = "/{id}")
  ResponseEntity<String> delete(@PathVariable UUID id);
}
