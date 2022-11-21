package src;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws IOException, FileNotFoundException {
        // --federal/--estadual consulta_cand votacao_secao 20/11/2022
        int cargo;
        String arquivoConsulta;
        String arquivoVotacao;
        String arquivoSaida = "output-";
        LocalDate data;

        if(args[0].equals("--federal")){
            cargo = 6;
            arquivoSaida += "federal.txt";
        }
        else if(args[0].equals("--estadual")){
            cargo = 7;
            arquivoSaida += "estadual.txt";
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
            BufferedWriter arquivo = new BufferedWriter(new FileWriter(arquivoSaida));
            eleicao.preencheDeputados(arquivoConsulta, cargo, data);
            eleicao.preencheVotosDeputados(arquivoVotacao, cargo);
            eleicao.imprimeInformacoes(arquivo, 6);
            arquivo.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}