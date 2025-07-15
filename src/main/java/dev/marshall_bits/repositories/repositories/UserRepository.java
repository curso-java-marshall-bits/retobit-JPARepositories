package dev.marshall_bits.repositories.repositories;
import dev.marshall_bits.repositories.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    long count();
}
