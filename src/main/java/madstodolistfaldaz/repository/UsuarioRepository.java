package madstodolistfaldaz.repository;

import madstodolistfaldaz.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String s);

    // Sobrescribir findAll para devolver List en lugar de Iterable
    List<Usuario> findAll();
}