package src;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

public class App {

    public static void main(String[] args) throws IOException, FileNotFoundException {
        int cargo;
        String arquivoConsulta;
        String arquivoVotacao;
        LocalDate data;

        if(args[0].equals("--federal")){
            cargo = 6;
        }
        else if(args[0].equals("--estadual")){
            cargo = 7;
        }
        else{
            return;
        }
        arquivoConsulta = args[1];
        arquivoVotacao = args[2];
        String[] dataTemp = args[3].split("/");
        data = LocalDate.of(Integer.parseInt(dataTemp[2]), Integer.parseInt(dataTemp[1]), Integer.parseInt(dataTemp[0]));

    
        Eleicao eleicao = new Eleicao();
        try {
            eleicao.preencheDeputados(arquivoConsulta, cargo, data);
            eleicao.preencheVotosDeputados(arquivoVotacao, cargo);
            System.out.println(eleicao.retornaInformacoes(cargo));
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}