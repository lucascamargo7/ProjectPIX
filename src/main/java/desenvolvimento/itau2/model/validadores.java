package desenvolvimento.itau2.model;


import ch.qos.logback.core.joran.spi.ElementSelector;
import desenvolvimento.itau2.services.chaveService;
import desenvolvimento.itau2.services.clienteService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.PushBuilder;
//import javax.validation.constraints.Pattern;
import java.util.InputMismatchException;

public class validadores {
    @Autowired
    chaveService chaveService;
    clienteService clienteService;

    //Realiza a validação se o CPF É VALIDO
    public static boolean isCPF(String CPF) {
        CPF = CPF.replaceAll("\\D", "");
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11)) {

            System.out.println("CPF FALSO");
            return false;
        }


        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char) (r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char) (r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
                return true;
            } else {
                return false;
            }

        } catch (InputMismatchException erro) {
            return false;
        }
    }

    public static boolean isCelular(String celular) {

        boolean isNumeric =  celular.matches("[+-]?\\d*(\\.\\d+)?");

        if (isNumeric == false) return false;

        celular = celular.replaceAll("\\D", "");
        if (celular.length() < 13) return false;

        if (celular.length() == 13 && Integer.parseInt(celular.substring(5, 6)) != 9) return false;
        //System.out.println(celular.substring(4, 5));

        java.util.regex.Pattern p = java.util.regex.Pattern.compile(celular.charAt(0) + "{" + celular.length() + "}");
        java.util.regex.Matcher m = p.matcher(celular);
        if (m.find()) return false;

        Integer[] codigosDDD = {
                11, 12, 13, 14, 15, 16, 17, 18, 19,
                21, 22, 24, 27, 28, 31, 32, 33, 34,
                35, 37, 38, 41, 42, 43, 44, 45, 46,
                47, 48, 49, 51, 53, 54, 55, 61, 62,
                64, 63, 65, 66, 67, 68, 69, 71, 73,
                74, 75, 77, 79, 81, 82, 83, 84, 85,
                86, 87, 88, 89, 91, 92, 93, 94, 95,
                96, 97, 98, 99};

        if (java.util.Arrays.asList(codigosDDD).indexOf(Integer.parseInt(celular.substring(0, 2))) == -1) return false;

        Integer[] prefixos = {2, 3, 4, 5, 7};
        if (celular.length() == 10 && java.util.Arrays.asList(prefixos).indexOf(Integer.parseInt(celular.substring(2, 3))) == -1)
            return false;

        //Verdadeiro
        return true;

    }

    public static boolean isEmail(String email) {
        boolean isEmailIdValid = false;

        if (email != null && email.length() > 0 && email.length() <= 77) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isEmailIdValid = true;
            }
        }

        return isEmailIdValid;
    }

    public static boolean isCNPJ(String CNPJ) {
        CNPJ = CNPJ.replaceAll("\\D", "");
// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
        if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") ||
                CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333") ||
                CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555") ||
                CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") ||
                CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999") ||
                (CNPJ.length() != 14))
            return (false);

        char dig13, dig14;
        int sm, i, r, num, peso;

// "try" - protege o código para eventuais erros de conversao de tipo (int)
        try {
// Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
// converte o i-ésimo caractere do CNPJ em um número:
// por exemplo, transforma o caractere '0' no inteiro 0
// (48 eh a posição de '0' na tabela ASCII)
                num = (int) (CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig13 = '0';
            else dig13 = (char) ((11 - r) + 48);

// Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = (int) (CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig14 = '0';
            else dig14 = (char) ((11 - r) + 48);

// Verifica se os dígitos calculados conferem com os dígitos informados.
            if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
                return (true);
            else return (false);
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    public static boolean validaAlteraCliente(clienteModel cliente) {
        boolean valido = false;
        String tipo_conta = cliente.getTipoconta().toString().toUpperCase();
        if (tipo_conta.equals("CORRENTE") ||
                tipo_conta.equals("POUPANÇA") &&
                        tipo_conta.length()<=10){
            valido = true;
        }

        String num_agencia = Integer.toString(cliente.getNumagencia());
        boolean isNumeric =  num_agencia.matches("[+-]?\\d*(\\.\\d+)?");
        if (isNumeric == false || num_agencia.length()>4){
            valido = false;
        }

        String num_conta = Integer.toString(cliente.getNumconta());
        boolean isNumeric2 =  num_conta.matches("[+-]?\\d*(\\.\\d+)?");
        if(isNumeric2 == false || num_conta.length()>8){
            valido = false;
        }

        String nome = cliente.getNome().toString().toUpperCase();
        if(nome.trim()=="" || nome.length()>30){
            valido = false;
        }

        String sobrenome = cliente.getSobrenome().toString().toUpperCase();
        if(nome.length()>45){
            valido = false;
        }

        return valido;

    }
}


