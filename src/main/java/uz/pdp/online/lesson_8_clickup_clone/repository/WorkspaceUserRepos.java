package uz.pdp.online.lesson_8_clickup_clone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import uz.pdp.online.lesson_8_clickup_clone.entity.Workspace;
import uz.pdp.online.lesson_8_clickup_clone.entity.WorkspaceUser;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkspaceUserRepos extends JpaRepository<WorkspaceUser, UUID> {
    Optional<WorkspaceUser> findByWorkspaceIdAndUserId(Long workspace_id, UUID user_id);

    @Transactional//o'chirishda xato bolishini oldini olish uchun
    @Modifying// natijasi kerak emasligini bildirish uchun(void)
    void deleteByWorkspaceIdAndUserId(Long workspace_id, UUID user_id);

    List<WorkspaceUser> findAllByWorkspaceId(Long workspace_id);

    List<Workspace> findAllByUserId(UUID user_id);
}
