package src;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws IOException, FileNotFoundException {
        Eleicao eleicao = new Eleicao();
        try {
            Scanner scan = new Scanner(System.in);
            String sigla=scan.nextLine();
            eleicao.preencheDeputados("data/consulta_cand_2022/consulta_cand_2022_"+sigla+".csv", sigla, 6);
            //eleicao.imprimePartidos();
            eleicao.preencheVotosDeputados("data/votacao_secao_2022/votacao_secao_2022_"+sigla+".csv", sigla, 6);
            eleicao.imprimePartidos();
            scan.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}