package br.com.task_manager_api.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Entity
@Data
public class Project {
  @Id
  @GeneratedValue
  private UUID uuid;

  @NotEmpty
  private String name;
}
