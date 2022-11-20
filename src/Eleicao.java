package src;

import java.io.BufferedReader;
//import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
//import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Eleicao {
    private Map<Integer, Partido> partidos;

    public Eleicao(){
        this.partidos = new HashMap<>();
    }

    public void preencheDeputados(String nomeArquivo, String sigla, int cargo) throws FileNotFoundException, IOException {
        try(FileInputStream in = new FileInputStream(nomeArquivo);
            Reader r = new InputStreamReader(in, "ISO-8859-1");
            BufferedReader br = new BufferedReader(r);) 
        {
            String linha=br.readLine();
            linha=br.readLine();

            while(linha!=null) {
                try (Scanner scan = new Scanner(linha)) {
                    Deputado novoDeputado = preencheDeputado(scan, cargo);
                    if(novoDeputado != null){
                        Partido partidoTemp = partidos.get(novoDeputado.getNumeroDoPartido());
                        if(partidoTemp == null){
                            partidos.put(novoDeputado.getNumeroDoPartido(), new Partido(novoDeputado.getNumeroDoPartido(), novoDeputado.getSiglaDoPartido()));
                            partidoTemp = partidos.get(novoDeputado.getNumeroDoPartido());
                        }
                        partidoTemp.adicionaDeputado(novoDeputado.getNumeroDoCandidato(), novoDeputado);
                    }
                    linha=br.readLine();
                } catch(NoSuchElementException e) {
                    System.out.println(e);
                }
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private Deputado preencheDeputado(Scanner scan, int cargo) {
        int CD_CARGO;                   //6 para deputado federal e 7 para deputado estadual;
        int NR_CANDIDATO;               //número do candidato;
        String NM_URNA_CANDIDATO;       //nome do candidato na urna;
        int NR_PARTIDO;                 //número do partido;
        String SG_PARTIDO;              //sigla do partido;
        int NR_FEDERACAO;               //número da federação, com -1 representando candidato em partido isolado (que não participa de federação)
        String DT_NASCIMENTO;           //data de nascimento do candidato no formato dd/mm/aaaa;
        int CD_GENERO;                  //2 representando masculino e 4 representando feminino;
        int CD_SIT_TOT_TURNO;           //2 ou 3 representando candidato eleito;
        String NM_TIPO_DESTINACAO_VOTOS;//quando for “Válido (legenda)” os votos deste candidato vão para a legenda (e devem ser computados para a legenda, mesmo em caso de CD_SITUACAO_CANDIDADO_TOT diferente de 2 ou 16)
        int CD_SITUACAO_CANDIDATO_TOT;  //processar apenas aqueles com os valores 2 ou 16 que representam candidatos com candidatura deferida;

        scan.useDelimiter(";");
        passaDireto(13, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        CD_CARGO=scan.nextInt();
        if (CD_CARGO != cargo) {
            scan.nextLine();
            return null;
        }
        scan.useDelimiter(";");
        passaDireto(3, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        NR_CANDIDATO=scan.nextInt();
        scan.useDelimiter(";");
        passaDireto(2, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        NM_URNA_CANDIDATO=scan.next();
        scan.useDelimiter(";");
        passaDireto(9, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        NR_PARTIDO=scan.nextInt();
        scan.useDelimiter(";");
        passaDireto(1, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        SG_PARTIDO=scan.next();
        scan.useDelimiter(";");
        passaDireto(2, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        NR_FEDERACAO=scan.nextInt();
        scan.useDelimiter(";");
        passaDireto(12, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        DT_NASCIMENTO=scan.next();
        scan.useDelimiter(";");
        passaDireto(3, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        CD_GENERO=scan.nextInt();
        scan.useDelimiter(";");
        passaDireto(11, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        CD_SIT_TOT_TURNO=scan.nextInt();
        scan.useDelimiter(";");
        passaDireto(11, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        NM_TIPO_DESTINACAO_VOTOS=scan.next();
        scan.useDelimiter(";");
        passaDireto(1, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        CD_SITUACAO_CANDIDATO_TOT=scan.nextInt();
        scan.nextLine();
        /*
        if (CD_SITUACAO_CANDIDATO_TOT!=2 && CD_SITUACAO_CANDIDATO_TOT!=16) {
            return null;
        }
        */
        Deputado novoDeputado = new Deputado(CD_CARGO, NR_CANDIDATO, NM_URNA_CANDIDATO, NR_PARTIDO, SG_PARTIDO, NR_FEDERACAO, DT_NASCIMENTO, CD_GENERO, CD_SIT_TOT_TURNO, NM_TIPO_DESTINACAO_VOTOS, CD_SITUACAO_CANDIDATO_TOT);
        //System.out.println(novoDeputado);
        return novoDeputado;
    }

    public void preencheVotosDeputados(String nomeArquivo, String sigla, int cargo) throws FileNotFoundException, IOException {
        try(FileInputStream in = new FileInputStream(nomeArquivo);
            Reader r = new InputStreamReader(in, "ISO-8859-1");
            BufferedReader br = new BufferedReader(r);) 
        {
            String linha=br.readLine();
            linha=br.readLine();

            while(linha!=null) {
                try (Scanner scan = new Scanner(linha)) {
                    preencheVotosDeputado(scan, cargo);
                    linha=br.readLine();
                } catch(NoSuchElementException e) {
                    System.out.println(e);
                }
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void preencheVotosDeputado(Scanner scan, int cargo) {
        int CD_CARGO;    //6 para deputado federal e 7 para deputado estadual;
        int NR_VOTAVEL;  //o número do candidato no caso de voto nominal ou o número do partido se for voto na legenda. Os números 95, 96, 97, 98 representam casos de votos em branco, nulos ou anulados, e, como só estamos nos concentrando nos votos válidos, linhas com esses números devem ser ignoradas. Atenção, pois há casos em que NR_VOTAVEL é um número de candidato, porém este candidato tem seus votos nominais redirecionados para a legenda como indicado acima para NM_TIPO_DESTINACAO_VOTOS no arquivo de candidatos igual a “Válido (legenda)”
        int QT_VOTOS;    //número de votos (no candidato ou no partido) na sessão.

        scan.useDelimiter(";");
        passaDireto(17, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        CD_CARGO=scan.nextInt();
        if (CD_CARGO != cargo) {
            scan.nextLine();
            return;
        }
        scan.useDelimiter(";");
        passaDireto(2, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        NR_VOTAVEL=scan.nextInt();
        if (NR_VOTAVEL >= 95 && NR_VOTAVEL <= 98) {
            scan.nextLine();
            return;
        }
        scan.useDelimiter(";");
        passaDireto(2, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        QT_VOTOS=scan.nextInt();
        scan.useDelimiter(";");
        passaDireto(4, scan);
        scan.nextLine();

        if(NR_VOTAVEL >= 100){ // votou em deputado
            int NR_PARTIDO;
            if(cargo == 6){ // 1234
                NR_PARTIDO = NR_VOTAVEL/100; // obtem partido
            }
            else{ // 12345
                NR_PARTIDO = NR_VOTAVEL/1000; // obtem partido
            }

            //System.out.println(NR_PARTIDO + " | " + NR_VOTAVEL);

            Partido partidoTemp = partidos.get(NR_PARTIDO);
            Deputado deputadoTemp = partidoTemp.retornaDeputado(NR_VOTAVEL);
            if(deputadoTemp != null && (deputadoTemp.getSituacaoCandidato() == 2 || deputadoTemp.getSituacaoCandidato() == 16)){
                deputadoTemp.adicionaVotos(QT_VOTOS);
                partidoTemp.adicionaVotos(QT_VOTOS);
            }
        }
        else{
            Partido partidoTemp = partidos.get(NR_VOTAVEL);
            partidoTemp.adicionaVotos(QT_VOTOS);
        }
    }

    public void imprimePartidos(){
        for(Partido p : partidos.values()){
            System.out.println(p);
            System.out.println();
        }
    }
    
    private void passaDireto(int i, Scanner scan) {
        for (int aux=0;aux<i;aux++) {
            scan.next();
        }
    }
}