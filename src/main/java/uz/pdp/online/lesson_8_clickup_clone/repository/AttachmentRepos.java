package uz.pdp.online.lesson_8_clickup_clone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.lesson_8_clickup_clone.entity.Attachment;

import java.util.UUID;

public interface AttachmentRepos extends JpaRepository<Attachment, UUID> {
}
