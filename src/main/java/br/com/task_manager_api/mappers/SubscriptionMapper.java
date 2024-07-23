package br.com.task_manager_api.mappers;

import br.com.task_manager_api.dtos.SubscriptionDto;
import br.com.task_manager_api.models.ApiUser;
import br.com.task_manager_api.models.Subscription;
import br.com.task_manager_api.models.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubscriptionMapper {

  SubscriptionMapper INSTANCE = Mappers.getMapper(SubscriptionMapper.class);

  @Mapping(source = "apiUserID", target = "apiUser")
  @Mapping(source = "taskID", target = "task")
  Subscription toModel(SubscriptionDto subscriptionDto);

  default ApiUser toApiUser (UUID uuid) {
    if (uuid == null) {
      return null;
    }

    ApiUser apiUser = new ApiUser();
    apiUser.setUuid(uuid);

    return apiUser;
  }

  default Task toTask (UUID uuid) {
    if (uuid == null) {
      return null;
    }

    Task task = new Task();
    task.setUuid(uuid);

    return task;
  }
}
