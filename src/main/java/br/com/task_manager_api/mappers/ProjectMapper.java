package br.com.task_manager_api.mappers;

import br.com.task_manager_api.dtos.ProjectDto;
import br.com.task_manager_api.models.Project;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectMapper {

  ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

  Project toModel(ProjectDto apiUserDto);
  Project toModel(ProjectDto apiUserDto, @MappingTarget Project project);
}
