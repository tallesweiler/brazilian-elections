package src;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws IOException, FileNotFoundException {
        Eleicao eleicao = new Eleicao();
        try {
            BufferedWriter arquivo = new BufferedWriter(new FileWriter("teste.txt"));
            Scanner scan = new Scanner(System.in);
            String sigla=scan.nextLine();
            eleicao.preencheDeputados("consulta_cand_2022/consulta_cand_2022_"+sigla+".csv", sigla, 6);
            eleicao.preencheVotosDeputados("votacao_secao_2022/votacao_secao_2022_"+sigla+".csv", sigla, 6);
            eleicao.imprimeInformacoes(arquivo);
            arquivo.close();
            scan.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}