package desenvolvimento.itau2.controller;

import desenvolvimento.itau2.dto.saidaChaveJoinDTO;
import desenvolvimento.itau2.model.chaveModel;
import desenvolvimento.itau2.model.clienteModel;
import desenvolvimento.itau2.projection.saidaChaveJoin;
import desenvolvimento.itau2.services.chaveService;
import desenvolvimento.itau2.services.clienteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class chaveController {
    @Autowired
    chaveService chaveService;
    clienteService clienteService;

    @PostMapping(path = "/api/cadastrarChave")
    public chaveModel cadastrarChave(@RequestBody @Valid chaveModel chave){
        return  chaveService.incluirChave(chave);
    }

    @GetMapping(path = "/api/listarChaves")
    public List<chaveModel> listarChaves() {
        return chaveService.listarChaves();
    }

    @GetMapping(path = "/api/saidaChaveJoin/{id}")
    public List<saidaChaveJoin> listaChaveJoin (@PathVariable String id){
        return chaveService.listarChaveJoin(id);
    }

    @GetMapping(path = "/api/listarChaveID/{id}")
    public Optional<chaveModel> ListarChaveID(@PathVariable String id) {
        return chaveService.listarChavesID(id);
    }

    @GetMapping(path = "/api/listarChaveTipo/{tipo}")
    public List<chaveModel> listarChaveTipo(@PathVariable String tipo) {
        return chaveService.listarChavesTipo(tipo);
    }


    @PutMapping(path = "/api/alterarChave/{id}")
    public chaveModel alterarChave(@PathVariable String id, @RequestBody @Valid chaveModel chave) {
        return chaveService.alterarChave(id, chave);
    }

    @DeleteMapping(path = "/api/deletarChave/{id}")
    public void deletarChave(@PathVariable String id) {
        chaveService.deletarChave(id);
    }

    @GetMapping(path = "/api/listarChaveCliente/{id}")
    public List<chaveModel> findAllBycliente(@PathVariable Long id){
        return chaveService.findAllBycliente(id);
    }

}
