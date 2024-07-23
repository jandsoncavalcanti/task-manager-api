package br.com.task_manager_api.mappers;

import br.com.task_manager_api.dtos.ApiUserDto;
import br.com.task_manager_api.models.ApiUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApiUserMapper {

  ApiUserMapper INSTANCE = Mappers.getMapper(ApiUserMapper.class);

  ApiUser toModel(ApiUserDto apiUserDto);
  ApiUser toModel(ApiUserDto apiUserDto, @MappingTarget ApiUser apiUser);
}
