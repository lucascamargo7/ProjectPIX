package desenvolvimento.itau2.model;

import desenvolvimento.itau2.exceptions.UnprocessableException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Table(name = "clientes")
public class clienteModel {

    @Column(name = "cliente_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    Long id;


    @Column(name="num_agencia", length = 4, nullable = false)
    //@Min(value = 1, message = "Digite o numero da agência")
    @Getter @Setter
    private int numagencia;




    @Column(name = "num_conta", length = 8,  nullable = false)
    //@Min(value = 1, message = "Digite o numero da conta")
    @Getter @Setter private int numconta;

    @Column(length = 30,  nullable = false)
    //@NotBlank(message = "Digite o nome do correntista")
    @Getter @Setter private String nome;

    @Column(length = 45)
    @Getter @Setter private String sobrenome;

    //PESSOA FISICA OU JURIDICA
    @Column(name = "tipo_cliente", length = 8,  nullable = false)
    //@NotBlank(message = "Identifique se e para pessoa fisica ou juridica")
    @Getter @Setter private String tipocliente;

    @Column(name = "tipo_conta", length = 10,  nullable = false)
    //@NotBlank(message = "Digite se a conta é corrente ou conta poupança")
    @Getter @Setter private String tipoconta;

//    @OneToMany(mappedBy = "cliente")
//    private List<chaveModel> chaves = new ArrayList<>();



//    @OneToMany(mappedBy = "cliente")
//    private List<chaveModel> chaves;

//    @OneToMany(mappedBy = "cliente",orphanRemoval = true)
//    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL})
//    @Getter @Setter private List<chaveModel> chaveModels = new ArrayList<chaveModel>();

}
