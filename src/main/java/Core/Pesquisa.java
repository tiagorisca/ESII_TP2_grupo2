package Core;

import Enums.TiposPesquisa;
import LeituraFicheiros.LeituraDocumentos;

import java.io.IOException;
import java.text.Normalizer;

public class Pesquisa {
    LeituraDocumentos ld;
    private ContagemPalavra m[][];
    private String q[];

    public Pesquisa(String path){
        ld = new LeituraDocumentos(path);
    }

    public String[] pesquisar(String pesquisa, TiposPesquisa tipo_pesquisa, int input) throws IOException {
        definirMatrizQ(pesquisa);
        definirMatrizM();
        return new String[1]; //temporario
    }

    public void definirMatrizQ(String pesquisa) {
        pesquisa = this.eliminarCaracteresPontuacao(pesquisa);
        pesquisa = this.eliminarDigitos(pesquisa);
        q = pesquisa.split(" ");
    }


    public void definirMatrizM() throws IOException {
        String[] conteudosDocs = ld.lerDocumentos();
        m = new ContagemPalavra[ld.getNumDocs()][ld.getMaxPalavras()];
        int numDoc = 0;
        for(String conteudo : conteudosDocs){
            conteudo = eliminarCaracteresPontuacao(conteudo);
            conteudo = eliminarDigitos(conteudo);
            String[] parts = conteudo.split(" ");
            int indPalavra = 0;
            for(String palavra : parts){
                if(!contemPalavra(palavra, numDoc)){
                    m[numDoc][indPalavra] = new ContagemPalavra(palavra, 1);
                }else{
                    int indiceExistente = getIndicePalavra(palavra, numDoc);
                    m[numDoc][indiceExistente].setContagem(m[numDoc][indiceExistente].getContagem() + 1);
                }
                indPalavra++;
            }
            numDoc++;
        }
    }

    private int getIndicePalavra(String palavra, int numDoc) {
        for(int i=0; i<m[numDoc].length; i++){
            if(m[numDoc][i] != null && m[numDoc][i].getPalavra().equals(palavra)){
                return i;
            }
        }
        return -1;
    }

    private boolean contemPalavra(String palavra, int numDoc) {
        for(int i=0; i<m[numDoc].length; i++){
            if(m[numDoc][i] != null && m[numDoc][i].getPalavra().equals(palavra)){
                return true;
            }
        }
        return false;
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

    public ContagemPalavra[][] getM() {
        return m;
    }

    public void setM(ContagemPalavra[][] m) {
        this.m = m;
    }

    public String[] getQ() {
        return q;
    }

    public void setQ(String[] q) {
        this.q = q;
    }

    public LeituraDocumentos getLd() {
        return ld;
    }

    public void setLd(LeituraDocumentos ld) {
        this.ld = ld;
    }
}
