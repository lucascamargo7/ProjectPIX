package desenvolvimento.itau2.repository;

import desenvolvimento.itau2.model.chaveModel;
import desenvolvimento.itau2.projection.saidaChaveJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface chaveRepository extends JpaRepository<chaveModel, String> {

    Optional<chaveModel> findByvalorchave(String valorchave);

    Optional<chaveModel> findBycliente(String cliente);

    List<chaveModel> findAllBycliente(Long cliente);

    List<chaveModel> findAllByvalorchave(String valorchave);

    List<chaveModel> findAllBytipochave(String tipochave);

    @Query(nativeQuery = true, value = "select c.id, c.tipo_chave, c.valor_chave, c.cliente, cli.nome, cli.sobrenome, "
            +" cli.num_agencia, cli.num_conta, c.ultima_alteracao, cli.tipo_conta  "
    + "from chave_pix as c, clientes as cli where c.cliente = cli.cliente_id and c.id = :id")
    List<saidaChaveJoin> findByChaveJoin(String id);


}
