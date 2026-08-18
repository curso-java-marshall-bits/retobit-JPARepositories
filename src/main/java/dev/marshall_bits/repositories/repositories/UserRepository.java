package dev.marshall_bits.repositories.repositories;

import dev.marshall_bits.repositories.models.Post;
import dev.marshall_bits.repositories.models.PostCategory;
import dev.marshall_bits.repositories.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    // Metodo para buscar un usuario por su nombre de usuario.

    User findByUsername(String name);

    //Metodo para buscar un usuario por su correo electrónico.
    User findByEmail(String email);


}
