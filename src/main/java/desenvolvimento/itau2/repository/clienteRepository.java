package desenvolvimento.itau2.repository;

import desenvolvimento.itau2.model.clienteModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface clienteRepository extends JpaRepository<clienteModel, Long> {
}
