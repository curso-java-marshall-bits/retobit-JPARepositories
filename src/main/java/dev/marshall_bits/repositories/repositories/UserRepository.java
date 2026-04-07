package dev.marshall_bits.repositories.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import dev.marshall_bits.repositories.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}