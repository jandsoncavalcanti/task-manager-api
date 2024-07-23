package br.com.task_manager_api.repositories;

import br.com.task_manager_api.models.ApiUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApiUserRepository extends JpaRepository<ApiUser, UUID> {
}
