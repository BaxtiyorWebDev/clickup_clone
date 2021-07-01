package uz.pdp.online.lesson_8_clickup_clone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.lesson_8_clickup_clone.entity.WorkspaceRole;

import java.util.UUID;

public interface WorkspaceRoleRepos extends JpaRepository<WorkspaceRole, UUID> {
}
