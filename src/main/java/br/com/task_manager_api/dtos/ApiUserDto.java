package br.com.task_manager_api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ApiUserDto {

  public interface ApiUserView {
    interface PostView{}
    interface PutView{}
    interface GetView{}
  }

  @NotBlank(groups = {ApiUserView.PostView.class, ApiUserView.PutView.class}, message = "Required field")
  @JsonView({ApiUserView.PostView.class, ApiUserView.PutView.class, ApiUserView.GetView.class})
  private String username;
}
