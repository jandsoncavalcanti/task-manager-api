package br.com.task_manager_api.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Data
public class Subscription {
  @Id
  @GeneratedValue
  private UUID uuid;

  @ManyToOne
  @JoinColumn(name = "apiuser_id")
  @NotNull
  private ApiUser apiUser;

  @ManyToOne
  @JoinColumn(name = "task_id")
  @NotNull
  private Task task;

  @NotEmpty
  private String token;
}
