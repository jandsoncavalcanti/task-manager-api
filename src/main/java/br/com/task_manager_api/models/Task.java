package br.com.task_manager_api.models;

import br.com.task_manager_api.enums.TaskPriority;
import br.com.task_manager_api.enums.TaskStatus;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Data
public class Task {
  @Id
  @GeneratedValue
  private UUID uuid;

  @NotEmpty
  private String title;

  @NotNull
  @Enumerated(EnumType.STRING)
  private TaskStatus status;

  @NotNull
  @Enumerated(EnumType.ORDINAL)
  private TaskPriority priority;

  @ManyToOne
  @JoinColumn(name = "project_id")
  private Project project;

  @ManyToOne
  @JoinColumn(name = "apiuser_assigned_id")
  private ApiUser assignedTo;
}
