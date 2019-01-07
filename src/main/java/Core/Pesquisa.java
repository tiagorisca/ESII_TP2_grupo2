package Core;

import LeituraFicheiros.LeituraDocumentos;

import java.io.IOException;
import java.text.Normalizer;

public class Pesquisa {
    private int m[][];
    private String q[];

    public Pesquisa(){
        try {
            definirMatrizM();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void definirMatrizM() throws IOException {
        LeituraDocumentos ld = new LeituraDocumentos("files/documentos/");
        String[] conteudosDocs = ld.lerDocumentos();
        int numDoc = 0;
        for(String conteudo : conteudosDocs){
            conteudo = eliminarCaracteresPontuacao(conteudo);
            conteudo = eliminarDigitos(conteudo);
        }
    }

    public int contarNumDocsContemPalavra(String[] conteudosDocs, String palavra){
        int numDocs = 0;
        for(int i = 0; i<conteudosDocs.length; i++){
            if ( conteudosDocs[i].toLowerCase().indexOf(palavra.toLowerCase()) != -1 ) {
                numDocs++;
            }
        }
        return numDocs;
    }

    public String eliminarCaracteresPontuacao(String texto){

        String string = Normalizer.normalize(texto, Normalizer.Form.NFD);
        string = string.toLowerCase();
        string = string.replaceAll("[^\\p{ASCII}]", "");
        return string;
    }

    public String eliminarDigitos(String text){
        String texto = text.replaceAll("[0-9]+","");
        return texto;
    }

    public int[][] getM() {
        return m;
    }

    public void setM(int[][] m) {
        this.m = m;
    }

    public String[] getQ() {
        return q;
    }

    public void setQ(String[] q) {
        this.q = q;
    }
}
