package desenvolvimento.itau2.controller;

import desenvolvimento.itau2.model.chaveModel;
import desenvolvimento.itau2.model.clienteModel;
import desenvolvimento.itau2.services.clienteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.function.LongFunction;

@RestController
@AllArgsConstructor
public class clienteController {

    @Autowired
    clienteService clienteService;

    @PostMapping(path = "/api/cadastrarCliente")
    public clienteModel cadastrarCliente (@RequestBody @Valid clienteModel cliente ){
        return clienteService.incluirCliente(cliente);
    }

    @GetMapping(path = "/api/listarClientes")
    public List<clienteModel> listarClientes(){
        return clienteService.listarClientes();
    }


    @GetMapping(path = "/api/listarClienteID/{id}")
    public Optional<clienteModel> listarClienteID(@PathVariable Long id){
        return clienteService.listarClienteID(id);
    }


    @PutMapping(path = "/api/alterarCliente/{id}")
    public clienteModel alterarCliente(@PathVariable Long id, @RequestBody @Valid clienteModel cliente){
        return clienteService.alterarCliente(id, cliente);
    }

    @DeleteMapping(path = "/api/deletarCliente/{id}")
    public void deletarCliente(@PathVariable Long id){
        clienteService.deletarCliente(id);
    }


}