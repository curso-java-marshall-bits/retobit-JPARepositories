
package dev.marshall_bits.repositories.repositories;

import dev.marshall_bits.repositories.models.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    //FindByName
    List<User> findByName(String username);
    
    //FindByEmail
    List<User> findByEmail(String email);
    
}
