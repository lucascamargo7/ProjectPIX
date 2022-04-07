package desenvolvimento.itau2.services;

import desenvolvimento.itau2.exceptions.ResourceNotFoundException;
import desenvolvimento.itau2.exceptions.UnprocessableException;
import desenvolvimento.itau2.model.clienteModel;
import desenvolvimento.itau2.model.validadores;
import desenvolvimento.itau2.repository.clienteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@Component
public class clienteService {

    @Autowired
    clienteRepository repository;

    public clienteModel incluirCliente(clienteModel cliente) {
        if (validaCliente(cliente)) {
            return repository.save(cliente);
        }
        throw new UnprocessableException("Não foi possivel inserir");
    }

    public List<clienteModel> listarClientes() {
        List<clienteModel> clientes = repository.findAll();
        if (clientes.size() < 1) {
            throw new ResourceNotFoundException("Não encontrou");
        }
        return repository.findAll();
    }

    public Optional<clienteModel> listarClienteID(@PathVariable Long id) {
        final Optional<clienteModel> cliente = repository.findById(id);
        if (cliente.isEmpty()) {
            throw new ResourceNotFoundException("Cliente não encontrado " + id);
        }
        return cliente;
    }

    public clienteModel alterarCliente(@PathVariable Long id, @RequestBody clienteModel cliente) {

        if (validadores.validaAlteraCliente(cliente) == true) {
            clienteModel clienteAtual = repository.findById(id).get();
            BeanUtils.copyProperties(cliente, clienteAtual, "id");
            return repository.save(clienteAtual);
        }

        throw new UnprocessableException("Não foi possivel inserir");

    }

    public void deletarCliente(@PathVariable Long id) {
        repository.deleteById(id);
    }

    public boolean validaCliente(clienteModel cliente) {
        String tipo_conta = cliente.getTipoconta();
        Integer numagencia = cliente.getNumagencia();
        Integer numconta = cliente.getNumconta();

        if (cliente.getNome().trim().isEmpty()) {
            return false;
        }


        if (cliente.getTipocliente().toUpperCase().equals("FISICA") ||
                (cliente.getTipocliente().toUpperCase().equals("JURIDICA"))) {
            if (tipo_conta.toUpperCase().equals("CORRENTE") ||
                    (tipo_conta.toUpperCase().equals("POUPANÇA"))) {
                if (tipo_conta.length() < 10) {
                    if (numagencia > 0 && numagencia.toString().length() <= 4) {
                        if (numconta > 0 && numconta.toString().length() <= 8) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
