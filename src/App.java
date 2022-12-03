import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

public class App {

    public static void main(String[] args) {
        int cargo;
        String arquivoConsulta;
        String arquivoVotacao;
        LocalDate data;

        if(args.length != 4){
            System.out.println("ERRO: quantidade de argumentos inválida!");
            return;
        }
        
        if(args[0].equals("--federal")){
            cargo = 6;
        }
        else if(args[0].equals("--estadual")){
            cargo = 7;
        }
        else{
            System.out.println("ERRO: tipo de eleição não definido ou inválido!");
            return;
        }
        arquivoConsulta = args[1];
        arquivoVotacao = args[2];
        
        try{
            String[] dataTemp = args[3].split("/");
            if(dataTemp.length != 3){
                throw new Error();
            }
            data = LocalDate.of(Integer.parseInt(dataTemp[2]), Integer.parseInt(dataTemp[1]), Integer.parseInt(dataTemp[0]));
        }
        catch (Error e){
            System.out.println("ERRO: formato de data inválido!");
            return;
        }

        Eleicao eleicao = new Eleicao();
        try {
            eleicao.preencheDeputados(arquivoConsulta, cargo, data);
            eleicao.preencheVotosDeputados(arquivoVotacao, cargo);
            System.out.println(eleicao.retornaInformacoes(cargo));
        }
        catch (UnsupportedEncodingException e) {
            System.out.println("ERRO: codificação de texto inválido!");
            return;
        }
        catch (FileNotFoundException e) {
            System.out.println("ERRO: um ou mais arquivos não foram encontrados!");
            return;
        }
        catch (IOException e) {
            System.out.println("ERRO: algo não funcionou como deveria!");
            return;
        }
    }
}
