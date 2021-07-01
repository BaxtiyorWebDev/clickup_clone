package uz.pdp.online.lesson_8_clickup_clone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.lesson_8_clickup_clone.entity.WorkspacePermission;
import uz.pdp.online.lesson_8_clickup_clone.entity.enums.WorkspacePermissionName;

import java.util.Optional;
import java.util.UUID;

public interface WorkspacePermissionRepos extends JpaRepository<WorkspacePermission, UUID> {
    Optional<WorkspacePermission> findByWorkspaceRoleIdAndPermission(UUID workspaceRole_id, WorkspacePermissionName permission);
}
