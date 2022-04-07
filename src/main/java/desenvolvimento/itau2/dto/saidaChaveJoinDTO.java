package desenvolvimento.itau2.dto;

import desenvolvimento.itau2.projection.saidaChaveJoin;

public class saidaChaveJoinDTO {

    private String Id;
    private String tipo_chave;
    private String valor_chave;
    private String cliente;
    private String nome;
    private String sobrenome;

    public String getTipo_conta() {
        return tipo_conta;
    }

    public void setTipo_conta(String tipo_conta) {
        this.tipo_conta = tipo_conta;
    }

    public String getNum_agencia() {
        return num_agencia;
    }

    public void setNum_agencia(String num_agencia) {
        this.num_agencia = num_agencia;
    }

    public String getNum_conta() {
        return num_conta;
    }

    public void setNum_conta(String num_conta) {
        this.num_conta = num_conta;
    }

    public String getUltima_alteracao() {
        return ultima_alteracao;
    }

    public void setUltima_alteracao(String ultima_alteracao) {
        this.ultima_alteracao = ultima_alteracao;
    }

    private String tipo_conta;
    private String num_agencia;
    private String num_conta;
    private String ultima_alteracao;

    public saidaChaveJoinDTO(){

    }

    public saidaChaveJoinDTO(String id, String tipo_chave, String valor_chave, String tipo_conta,
                             String num_agencia, String num_conta, String cliente, String nome, String sobrenome,
                             String ultima_alteracao) {
        Id = id;
        this.tipo_chave = tipo_chave;
        this.valor_chave = valor_chave;
        this.cliente = cliente;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.tipo_conta = tipo_conta;
        this.num_agencia = num_agencia;
        this.num_conta = num_conta;
        this.ultima_alteracao = ultima_alteracao;
    }

    public saidaChaveJoinDTO(saidaChaveJoin projection) {
        Id = projection.getID();
        tipo_chave = projection.gettipo_chave();
        valor_chave = projection.getvalor_chave();
        cliente = projection.getcliente();
        nome = projection.getnome();
        sobrenome = projection.getsobrenome();
        tipo_conta = projection.gettipo_conta();
        num_agencia = projection.getnum_agencia();
        num_conta = projection.getnum_conta();
        ultima_alteracao = projection.getultima_alteracao();
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTipo_chave() {
        return tipo_chave;
    }

    public void setTipo_chave(String tipo_chave) {
        this.tipo_chave = tipo_chave;
    }

    public String getValor_chave() {
        return valor_chave;
    }

    public void setValor_chave(String valor_chave) {
        this.valor_chave = valor_chave;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
}
