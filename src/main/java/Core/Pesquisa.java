package Core;

import Enums.TiposPesquisa;
import LeituraFicheiros.LeituraDocumentos;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;

public class Pesquisa {
    LeituraDocumentos ld;
    private ContagemPalavra m[][];
    private ContagemPalavra q[];

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
        String[] parts = pesquisa.split(" ");

        ArrayList<String> palavrasEncontradas = new ArrayList<>();
        ContagemPalavra[] temp = new ContagemPalavra[parts.length];
        int indiceQ = 0;
        for(String s : parts){
            if(!palavrasEncontradas.contains(s)){
                temp[indiceQ] = new ContagemPalavra(s, 1);
                indiceQ++;
                palavrasEncontradas.add(s);
            }else{
                int indicePalavra = findIndicePalavra(s, temp);
                temp[indicePalavra].setContagem(temp[indicePalavra].getContagem() + 1);
            }
        }
        q = new ContagemPalavra[palavrasEncontradas.size()];
        for(int i = 0; i < q.length; i++){
            q[i] = temp[i];
        }
    }

    private int findIndicePalavra(String s, ContagemPalavra[] temp) {
        for(int i = 0; i < temp.length; i++){
            if(temp[i] != null && s.equals(temp[i].getPalavra())){
                return i;
            }
        }
        return -1;
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
        verificacaoSemelhanca();
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

    public void verificacaoSemelhanca() throws IOException {

        int n = ld.getNumDocs();
        for(int i = 0; i<n; i++){
            for (int j = 0; j < m[i].length; j++) {
                if(m[i][j] != null) {
                    int np = contarNumDocsContemPalavra(ld.lerDocumentos(), m[i][j].getPalavra());
                    if(np == 0){
                        m[i][j].setValor(0);
                    }else{
                        m[i][j].setValor(m[i][j].getContagem() * (1 + java.lang.Math.log10(n / np)));
                        System.out.println(m[i][j].getValor());
                    }
                }
            }
        }
    }

    public ContagemPalavra[][] getM() {
        return m;
    }

    public void setM(ContagemPalavra[][] m) {
        this.m = m;
    }

    public ContagemPalavra[] getQ() {
        return q;
    }

    public void setQ(ContagemPalavra[] q) {
        this.q = q;
    }

    public LeituraDocumentos getLd() {
        return ld;
    }

    public void setLd(LeituraDocumentos ld) {
        this.ld = ld;
    }
}
