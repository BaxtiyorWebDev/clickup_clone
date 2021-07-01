package uz.pdp.online.lesson_8_clickup_clone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.lesson_8_clickup_clone.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepos extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String username);
}
