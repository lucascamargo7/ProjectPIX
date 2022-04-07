package desenvolvimento.itau2.services;

import desenvolvimento.itau2.dto.saidaChaveJoinDTO;
import desenvolvimento.itau2.exceptions.ResourceNotFoundException;
import desenvolvimento.itau2.exceptions.UnprocessableException;
import desenvolvimento.itau2.model.chaveModel;
import desenvolvimento.itau2.model.clienteModel;

import desenvolvimento.itau2.projection.saidaChaveJoin;
import desenvolvimento.itau2.repository.chaveRepository;
import desenvolvimento.itau2.repository.clienteRepository;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.print.DocFlavor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import static desenvolvimento.itau2.model.validadores.*;

@Slf4j
@Service
@Component
public class chaveService {

    @Autowired
    chaveRepository repository;


    @Autowired
    clienteRepository clienteRepository;


    public chaveModel incluirChave(chaveModel chave) {
        //final List<chaveModel> chaveModels = repository.findAllBycliente(chave.getCliente());
        final String tipo = chave.getTipochave();

        if (validarChave(chave, "incluir")) {

            switch (tipo) {
                case "CPF":
                case "EMAIL":
                case "CNPJ":
                    if (validarTipoChave(chave)) {
                        return mapAndSaveChave(chave);
                    }
                    break;
                case "CELULAR":

                    if (validarTipoChave(chave)) {
                        chave.setValorchave("+" + chave.getValorchave());
                        return mapAndSaveChave(chave);
                    }
                    break;
                case "ALEATORIA":
                    if (validarTipoChave(chave)) {
                        String evp = UUID.randomUUID().toString();
                        chave.setValorchave(evp);
                        return mapAndSaveChave(chave);
                    }
                default:
                    log.info("Não foi validada nenhuma chave - adicionar excessão");
                    throw new UnprocessableException("Dados não validados");
            }
        }
        ;
        log.info("Não foi validada nenhuma chave - adicionar excessão");
        throw new UnprocessableException("Dados não validados");
    }

    private boolean validarTipoChave(chaveModel chave) {
        String tipo_chave = chave.getTipochave();

        switch (tipo_chave) {
            case "CPF":
                if (isCPF(chave.getValorchave())) {
                    log.info("CPF validado");
                    return true;
                }
                break;
            case "CELULAR":
                if (isCelular(chave.getValorchave())) {
                    return true;
                }
                break;
            case "EMAIL":
                log.info("Iniciando cadastro de EMAIL");
                if (isEmail(chave.getValorchave())) {
                    log.info("Email validado");
                    return true;
                }
                break;
            case "CNPJ":
                log.info("Iniciando cadastro de CNPJ");
                if (isCNPJ(chave.getValorchave())) {
                    return true;
                }
                break;
            case "ALEATORIA":
                log.info("Iniciando cadastro de chave aleatoria");
                return true;
            default:
                return false;
        }
        return false;
    }

    private boolean validarChave(chaveModel chave, String alterar_incluir) {


        boolean isValid = false;
        clienteModel cliente;


        log.info("Verifica se a chave já existe na base de dados");
        if (!chave.getTipochave().toUpperCase().equals("ALEATORIA")) {
            if(chave.getTipochave().toUpperCase().equals("CELULAR")){
                final List<chaveModel> chaveModels = repository.findAllByvalorchave("+"+chave.getValorchave());
                if (chaveModels.size() > 0) {

                    return isValid;
                }
            }
            final List<chaveModel> chaveModels = repository.findAllByvalorchave(chave.getValorchave());
            if (chaveModels.size() > 0) {

                return isValid;
            }

        }

        log.info("Verifica se ja existe uma chave do mesmo tipo para o cliente, exceto aleatoria");
        //Aqui vai uma sentença que necessita de um AND (Analisar)

        log.info("Verificando se o cliente existe e se não existe inconsistência");
        log.info("em pessoa FISICA/JURIDICA e CPF/CNPJ");

        Long cliente_id = chave.getCliente();

        if (cliente_id == null ){
            return isValid;
        }

        if (clienteRepository.findById(chave.getCliente()).isPresent()) {
            cliente = clienteRepository.findById(chave.getCliente()).get();

            log.info("Pega a lista de chaves já cadastradas para o cliente");
            String tipo_cliente = cliente.getTipocliente();
            Integer qtd_chaves;
            final List<chaveModel> chaveCadastradas = repository.findAllBycliente(chave.getCliente());
            qtd_chaves = chaveCadastradas.size();

            log.info("Verifica se há inconformidade na quantidade de chaves");
            if (alterar_incluir.equals("incluir")) {
                if (tipo_cliente.toUpperCase().equals("FISICA") && qtd_chaves >= 5) {
                    return isValid;
                }

                if (tipo_cliente.toUpperCase().equals("JURIDICA") && qtd_chaves >= 20) {
                    return isValid;
                }
            }

            if (chave.getTipochave().equals("CPF") || chave.getTipochave().equals("CNPJ")) {
                if (cliente.getTipocliente().toUpperCase().equals("JURIDICA") &&
                        chave.getTipochave().equals("CNPJ")) {
                    isValid = true;
                }

                if (cliente.getTipocliente().toUpperCase().equals("FISICA") &&
                        chave.getTipochave().equals("CPF")) {
                    isValid = true;
                }

            }
        } else {
            isValid = false;
            return isValid;
        }

        isValid = true;
        return isValid;
    }

    private chaveModel mapAndSaveChave(final chaveModel chave) {

        chave.setStatus("Ativo");
        chave.setUltimaalteracao(LocalDateTime.now().toString());
        return repository.save(chave);
    }

    public List<chaveModel> listarChaves() {
        List<chaveModel> chaveModels = repository.findAll();
        if (chaveModels.size() < 1) {
            throw new ResourceNotFoundException("Dados não encontrados");
        }
        return chaveModels;
    }

    public Optional<chaveModel> listarChavesID(@PathVariable String id) {
        Optional<chaveModel> chave = repository.findById(id);
        if (chave.isEmpty()) {
            throw new ResourceNotFoundException("Dados não encontrados");
        }
        return chave;
    }

    public List<chaveModel> listarChavesTipo(@PathVariable String tipo) {
        List<chaveModel> chaves = repository.findAllBytipochave(tipo);
        if (chaves.isEmpty()) {
            throw new ResourceNotFoundException("Dados não encontrados");
        }
        return chaves;
    }

    public List<saidaChaveJoin> listarChaveJoin(@PathVariable String id){
        List<saidaChaveJoin> saidaChaveJoins = repository.findByChaveJoin(id);
        //List<saidaChaveJoinDTO> result = saidaChaveJoins.stream().map(x -> new saidaChaveJoinDTO(x)).collect(Collectors.toList());
        return saidaChaveJoins;
    }

    public List<chaveModel> findAllBycliente(@PathVariable Long id) {
        List<chaveModel> chaves = repository.findAllBycliente(id);
        if (chaves.size() < 1) {
            throw new ResourceNotFoundException("Dados não encontrados");
        }
        return chaves;
    }

    public chaveModel alterarChave(@PathVariable String id, @RequestBody chaveModel chave) {
        try {
            chaveModel chaveAtual = repository.findById(id).get();
            if (validarChave(chave, "alterar")) {
                if (validarTipoChave(chave)) {
                    BeanUtils.copyProperties(chave, chaveAtual, "id");
                    return mapAndSaveChave(chaveAtual);
                }
            }
        } catch
        (Exception e) {
            throw new UnprocessableException("");
        }
        ;
        return null;
    }

    public void deletarChave(@PathVariable String id) {
        repository.deleteById(id);
    }

}

//    public ResponseEntity findByvalorchave(@PathVariable(name = "valorchave", required = true) String valorchave) {
//        if (repository.findByvalorchave(valorchave).isEmpty()) {
//
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//
//        }
//        return new ResponseEntity(HttpStatus.FOUND);
//
//    }
