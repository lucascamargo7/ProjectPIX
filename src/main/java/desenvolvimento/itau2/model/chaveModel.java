package desenvolvimento.itau2.model;

import lombok.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Data
@Table(name = "chave_pix")
public class chaveModel implements Serializable  {
    @Id
    @Getter
    private String id = UUID.randomUUID().toString();

    @Column(name = "tipo_chave",nullable = false)
    //@NotBlank(message = "Informe o tipo de chave - (celular|email|cpf|cnpj|aleatorio)")
    @Getter @Setter private String tipochave;

    @Column(name="valor_chave",nullable = false, length = 77)
    //@NotBlank(message = "Informe a chave")
    @Getter @Setter private String valorchave;

    @Column(name="ultima_alteracao", nullable = false)
    //@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    @Getter @Setter private String ultimaalteracao;


    @Column(name = "cliente", nullable = false)
    @Getter @Setter private Long cliente;



    @Column(name="status", nullable = false)
    @Getter @Setter private String status;



}
