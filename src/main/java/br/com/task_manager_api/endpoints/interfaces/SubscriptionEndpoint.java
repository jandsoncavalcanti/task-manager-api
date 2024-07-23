package br.com.task_manager_api.endpoints.interfaces;

import br.com.task_manager_api.dtos.SubscriptionDto;
import br.com.task_manager_api.models.Subscription;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/subscription")
public interface SubscriptionEndpoint {

  @PostMapping
  ResponseEntity<Object> insert(@RequestBody @Validated(SubscriptionDto.SubscriptionView.PostView.class) @JsonView(SubscriptionDto.SubscriptionView.PostView.class) SubscriptionDto subscriptionDto);

  @GetMapping
  ResponseEntity<Page<Subscription>> page(@JsonView(SubscriptionDto.SubscriptionView.GetView.class) SubscriptionDto subscriptionDto,
                                          @PageableDefault(page = 0, size = 10, sort = "uuid", direction = Sort.Direction.ASC) Pageable pageable);

  @DeleteMapping(value = "/{id}")
  ResponseEntity<String> delete(@PathVariable UUID id);
}
