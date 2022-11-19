import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class App {

    //números 95, 96, 97, 98 representam casos de votos em branco, nulos ou anulados

    public static void geraArquivoDeputados(String nomeArquivo, String sigla, LinkedList<Politico> deputadosFederais, LinkedList<Politico> deputadosEstaduais) throws FileNotFoundException, IOException {
        try(FileInputStream in = new FileInputStream(nomeArquivo);
            Reader r = new InputStreamReader(in, "ISO-8859-1");
            BufferedReader br = new BufferedReader(r);) 
        {
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter("testesDeputados/teste"+sigla+".txt"));

            String linha=br.readLine();
            linha=br.readLine();

            while(linha!=null) {
                try (Scanner scan = new Scanner(linha)) {
                    String line=fazLinhaDeputados(scan, deputadosFederais, deputadosEstaduais);
                    if (line!=null)
                        buffWrite.append(line+"\n");
                    linha=br.readLine();
                } catch(NoSuchElementException e) {
                    System.out.println("a");
                }
            }
            buffWrite.close();
        } catch (UnsupportedEncodingException e) {
            System.out.println("b");
        } catch (IOException e) {
            System.out.println("c");
        }
    }

    public static void geraArquivoVotos(String nomeArquivo, String sigla, LinkedList<Politico> deputadosFederais, LinkedList<Politico> deputadosEstaduais) throws FileNotFoundException, IOException {
        try(FileInputStream in = new FileInputStream(nomeArquivo);
            Reader r = new InputStreamReader(in, "ISO-8859-1");
            BufferedReader br = new BufferedReader(r);) 
        {
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter("testesVotos/teste"+sigla+".txt"));
            
            String linha=br.readLine();
            linha=br.readLine();

            while(linha!=null) {
                try (Scanner scan = new Scanner(linha)) {
                    String line=fazLinhaVotos(scan, deputadosFederais, deputadosEstaduais);
                    if (line!=null)
                        buffWrite.append(line+"\n");
                    linha=br.readLine();
                } catch(NoSuchElementException e) {
                    System.out.println("aa");
                }
            }
            buffWrite.close();
        } catch (UnsupportedEncodingException e) {
            System.out.println("bb");
        } catch (IOException e) {
            System.out.println("cc");
        }
    }

    public static void geraArquivoVotosDeputados(String sigla, LinkedList<Politico> deputadosFederais, LinkedList<Politico> deputadosEstaduais) throws IOException{
        try {
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter("testesVotosDeputados/testeFederais"+sigla+".txt"));
            BufferedWriter buffeWrite = new BufferedWriter(new FileWriter("testesVotosDeputados/testeEstaduais"+sigla+".txt"));

            for (Politico p : deputadosFederais) {
                buffWrite.append(p+"\n");
            }

            for (Politico p : deputadosEstaduais) {
                buffeWrite.append(p+"\n");
            }

            buffWrite.close();
            buffeWrite.close();
        } catch (IOException e) {
            System.out.println("aaa");
        }
    }

    public static void passaDireto(int i, Scanner scan) {
        for (int aux=0;aux<i;aux++) {
            scan.next();
        }
    }

    public static String fazLinhaDeputados(Scanner scan, LinkedList<Politico> deputadosFederais, LinkedList<Politico> deputadosEstaduais) {
        int CD_CARGO;                       //6 para deputado federal e 7 para deputado estadual;
        int NR_CANDIDATO;                   //número do candidato;
        String NM_URNA_CANDIDATO;           //nome do candidato na urna;
        int NR_PARTIDO;                     //número do partido;
        String SG_PARTIDO;                  //sigla do partido;
        int NR_FEDERACAO;                   //número da federação, com -1 representando candidato em partido isolado (que não participa de federação)
        String DT_NASCIMENTO;               //data de nascimento do candidato no formato dd/mm/aaaa;
        int CD_GENERO;                      //2 representando masculino e 4 representando feminino;
        int CD_SIT_TOT_TURNO;               //2 ou 3 representando candidato eleito;
        String NM_TIPO_DESTINACAO_VOTOS;    //quando for “Válido (legenda)” os votos deste candidato vão para a legenda (e devem ser computados para a legenda, mesmo em caso de CD_SITUACAO_CANDIDADO_TOT diferente de 2 ou 16)
        int CD_SITUACAO_CANDIDATO_TOT;      //processar apenas aqueles com os valores 2 ou 16 que representam candidatos com candidatura deferida;

        scan.useDelimiter(";");
        passaDireto(13, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        CD_CARGO=scan.nextInt();
        if (CD_CARGO!=6 && CD_CARGO!=7) {
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
        if (CD_SITUACAO_CANDIDATO_TOT!=2 && CD_SITUACAO_CANDIDATO_TOT!=16) {
            return null;
        }

        if (CD_CARGO==6) {
            Politico politico=new Politico(CD_CARGO,NR_CANDIDATO,NM_URNA_CANDIDATO,NR_PARTIDO,SG_PARTIDO,NR_FEDERACAO,DT_NASCIMENTO,CD_GENERO,CD_SIT_TOT_TURNO,NM_TIPO_DESTINACAO_VOTOS,CD_SITUACAO_CANDIDATO_TOT);
            deputadosFederais.add(politico);
            return ";"+CD_CARGO+";"+NR_CANDIDATO+";"+NM_URNA_CANDIDATO+";"+NR_PARTIDO+";"+SG_PARTIDO+";"+NR_FEDERACAO+";"+DT_NASCIMENTO+";"+CD_GENERO+";"+CD_SIT_TOT_TURNO+";"+NM_TIPO_DESTINACAO_VOTOS+";"+CD_SITUACAO_CANDIDATO_TOT+";";
        }
        else if (CD_CARGO==7) {
            Politico politico=new Politico(CD_CARGO,NR_CANDIDATO,NM_URNA_CANDIDATO,NR_PARTIDO,SG_PARTIDO,NR_FEDERACAO,DT_NASCIMENTO,CD_GENERO,CD_SIT_TOT_TURNO,NM_TIPO_DESTINACAO_VOTOS,CD_SITUACAO_CANDIDATO_TOT);
            deputadosEstaduais.add(politico);
            return ";"+CD_CARGO+";"+NR_CANDIDATO+";"+NM_URNA_CANDIDATO+";"+NR_PARTIDO+";"+SG_PARTIDO+";"+NR_FEDERACAO+";"+DT_NASCIMENTO+";"+CD_GENERO+";"+CD_SIT_TOT_TURNO+";"+NM_TIPO_DESTINACAO_VOTOS+";"+CD_SITUACAO_CANDIDATO_TOT+";";
        }

        return null;
    }

    public static String fazLinhaVotos(Scanner scan, LinkedList<Politico> deputadosFederais, LinkedList<Politico> deputadosEstaduais) {
        int CD_CARGO;    //6 para deputado federal e 7 para deputado estadual;
        int NR_VOTAVEL;  //o número do candidato no caso de voto nominal ou o número do partido se for voto na legenda. Os números 95, 96, 97, 98 representam casos de votos em branco, nulos ou anulados, e, como só estamos nos concentrando nos votos válidos, linhas com esses números devem ser ignoradas. Atenção, pois há casos em que NR_VOTAVEL é um número de candidato, porém este candidato tem seus votos nominais redirecionados para a legenda como indicado acima para NM_TIPO_DESTINACAO_VOTOS no arquivo de candidatos igual a “Válido (legenda)”
        int QT_VOTOS;    //número de votos (no candidato ou no partido) na sessão.

        scan.useDelimiter(";");
        passaDireto(17, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        CD_CARGO=scan.nextInt();
        if (CD_CARGO!=6 && CD_CARGO!=7) {
            scan.nextLine();
            return null;
        }
        scan.useDelimiter(";");
        passaDireto(2, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        NR_VOTAVEL=scan.nextInt();
        scan.useDelimiter(";");
        passaDireto(2, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        QT_VOTOS=scan.nextInt();
        scan.useDelimiter(";");
        passaDireto(4, scan);
        scan.nextLine();

        if (CD_CARGO==6) {
            for (Politico p : deputadosFederais) {
                if (p.getNumeroDoCandidato()==NR_VOTAVEL) {
                    p.adicionaVotos(QT_VOTOS);
                    break;
                }     
            }
            return ";"+CD_CARGO+";"+NR_VOTAVEL+";"+QT_VOTOS+";";
        }
        else if (CD_CARGO==7) {
            for (Politico p : deputadosEstaduais) {
                if (p.getNumeroDoCandidato()==NR_VOTAVEL) {
                    p.adicionaVotos(QT_VOTOS);
                    break;
                }  
            }
            return ";"+CD_CARGO+";"+NR_VOTAVEL+";"+QT_VOTOS+";";
        }
        
        return null;
    }

    public static void main(String[] args) throws IOException, FileNotFoundException {
        try {
            Scanner scan = new Scanner(System.in);
            String sigla=scan.nextLine();
            LinkedList<Politico> deputadosFederais = new LinkedList<Politico>();
            LinkedList<Politico> deputadosEstaduais = new LinkedList<Politico>();
            geraArquivoDeputados("consulta_cand_2022/consulta_cand_2022_"+sigla+".csv", sigla, deputadosFederais, deputadosEstaduais);
            geraArquivoVotos("arquivos_de_votacao/votacao_secao_2022_"+sigla+".csv", sigla, deputadosFederais, deputadosEstaduais);
            geraArquivoVotosDeputados(sigla, deputadosFederais, deputadosEstaduais);
            scan.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("d");
        } catch (IOException e) {
            System.out.println("e");
        }
    }
}
