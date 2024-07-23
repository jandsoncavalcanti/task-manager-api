package br.com.task_manager_api.dtos;

import br.com.task_manager_api.enums.TaskPriority;
import br.com.task_manager_api.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TaskDto {

  public interface TaskView {
    interface PostView{}
    interface PutView{}
    interface StatusView{}
    interface PriorityView{}
    interface ProjectView{}
    interface AssignView{}
    interface GetView{}
  }

  @JsonView({TaskView.PostView.class, TaskView.PutView.class, TaskView.GetView.class})
  @NotEmpty(groups = {TaskView.PostView.class, TaskView.PutView.class})
  private String title;

  @JsonView({TaskView.PostView.class, TaskView.PutView.class, TaskView.StatusView.class, TaskView.GetView.class})
  @NotNull(groups = {TaskView.PostView.class, TaskView.PutView.class, TaskView.StatusView.class}, message = "Required field")
  private TaskStatus status;

  @JsonView({TaskView.PostView.class, TaskView.PutView.class, TaskView.PriorityView.class, TaskView.GetView.class})
  @NotNull(groups = {TaskView.PostView.class, TaskView.PutView.class, TaskView.PriorityView.class}, message = "Required field")
  private TaskPriority priority;

  @JsonView({TaskView.PostView.class, TaskView.PutView.class, TaskView.ProjectView.class, TaskView.GetView.class})
  @NotNull(groups = {TaskView.ProjectView.class}, message = "Required field")
  private UUID projectID;

  @JsonView({TaskView.PostView.class, TaskView.PutView.class, TaskView.AssignView.class, TaskView.GetView.class})
  @NotNull(groups = {TaskView.AssignView.class}, message = "Required field")
  private UUID assignedToID;

  @JsonView({TaskView.GetView.class})
  private UUID watchingID;
}
