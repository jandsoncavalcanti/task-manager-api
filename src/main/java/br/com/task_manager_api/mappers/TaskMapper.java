package br.com.task_manager_api.mappers;

import br.com.task_manager_api.dtos.TaskDto;
import br.com.task_manager_api.models.ApiUser;
import br.com.task_manager_api.models.Project;
import br.com.task_manager_api.models.Task;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

  TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

  @Mapping(source = "projectID", target = "project")
  @Mapping(source = "assignedToID", target = "assignedTo")
  Task toModel(TaskDto taskDto);

  @Mapping(source = "projectID", target = "project")
  @Mapping(source = "assignedToID", target = "assignedTo")
  Task toModel(TaskDto taskDto, @MappingTarget Task task);

  default Project toProject(UUID uuid) {
    if (uuid == null) {
      return null;
    }

    Project project = new Project();
    project.setUuid(uuid);
    return project;
  }

  default ApiUser toApiUser(UUID uuid) {
    if (uuid == null) {
      return null;
    }

    ApiUser apiUser = new ApiUser();
    apiUser.setUuid(uuid);
    return apiUser;
  }
}
