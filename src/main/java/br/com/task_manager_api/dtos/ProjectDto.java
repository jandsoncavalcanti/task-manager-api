package br.com.task_manager_api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ProjectDto {

  public interface ProjectView {
    interface PostView{}
    interface PutView{}
    interface GetView{}
  }

  @NotBlank(groups = {ProjectView.PostView.class, ProjectView.PutView.class}, message = "Required field")
  @JsonView({ProjectView.PostView.class, ProjectView.PutView.class, ProjectView.GetView.class})
  private String name;
}
