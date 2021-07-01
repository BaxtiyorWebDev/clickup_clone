package uz.pdp.online.lesson_8_clickup_clone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.lesson_8_clickup_clone.entity.Workspace;

import java.util.UUID;

public interface WorkspaceRepos  extends JpaRepository<Workspace,Long> {
    boolean existsByOwnerIdAndName(UUID owner_id, String name);

    boolean existsByOwnerIdAndNameAndIdNot(UUID owner_id, String name, Long id);
}
