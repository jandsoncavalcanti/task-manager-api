package br.com.task_manager_api.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class SubscriptionDto {

  public interface SubscriptionView {
    interface PostView{}
    interface PutView{}
    interface GetView{}
  }

  @JsonView({SubscriptionView.PostView.class, SubscriptionView.PutView.class, SubscriptionView.GetView.class})
  @NotNull(groups = {SubscriptionView.PostView.class, SubscriptionView.PutView.class}, message = "Required field")
  private UUID apiUserID;
  @JsonView({SubscriptionView.PostView.class, SubscriptionView.PutView.class, SubscriptionView.GetView.class})
  @NotNull(groups = {SubscriptionView.PostView.class, SubscriptionView.PutView.class}, message = "Required field")
  private UUID taskID;
  @JsonView({SubscriptionView.PostView.class, SubscriptionView.PutView.class, SubscriptionView.GetView.class})
  @NotEmpty(groups = {SubscriptionView.PostView.class, SubscriptionView.PutView.class}, message = "Required field")
  private String token;
}
