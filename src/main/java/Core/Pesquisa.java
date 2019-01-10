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
    private double grauSim[];

    public Pesquisa(String path){
        ld = new LeituraDocumentos(path);
        grauSim = new double[ld.getNumDocs()];
    }

    public String[] pesquisar(String pesquisa, TiposPesquisa tipo_pesquisa, int input) throws IOException {
        definirMatrizQ(pesquisa);
        definirMatrizM();
        verificacaoSemelhanca();
        grauSemelhanca();
        switch(tipo_pesquisa){
            case NORMAL:
                return retornarTodosOrdemGrau();
            case COM_LIMITE_MAXIMO:
                return retornarFicheirosPorLimite(input);
            case COM_LIMITE_GRAU:
                break;
        }
        return new String[1];
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
                    indPalavra++;
                }else{
                    int indiceExistente = getIndicePalavra(palavra, numDoc);
                    m[numDoc][indiceExistente].setContagem(m[numDoc][indiceExistente].getContagem() + 1);
                }
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

    private int getIndicePalavraQ(String palavra) {
        for(int i=0; i<q.length; i++){
            if(q[i] != null && q[i].getPalavra().equals(palavra)){
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

    private boolean contemPalavraEmArrayQ(String palavra) {
        for(int i=0; i<q.length; i++){
            if(q[i] != null && q[i].getPalavra().equals(palavra)){
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
                        m[i][j].setValor(m[i][j].getContagem() * (1.0 + java.lang.Math.log10(((n*1.0) / (np*1.0)))));
                    }
                }
            }
        }
    }

    public void grauSemelhanca(){

        for(int i=0; i<ld.getNumDocs(); i++) {
            double sum1 = 0;
            double sum2 = 0;
            double sum3 = 0;
            for (int j = 0; j < m[i].length; j++) {
                try {
                    if (contemPalavraEmArrayQ(m[i][j].getPalavra())) {
                        int countPalavrasQ = q[this.getIndicePalavraQ(m[i][j].getPalavra())].getContagem();
                        sum1 += m[i][j].getValor() * countPalavrasQ;
                        sum2 += Math.pow(m[i][j].getValor(), 2);
                        sum3 += Math.pow(countPalavrasQ, 2);
                    } else {
                        sum1 += m[i][j].getValor() * 0;
                        sum2 += Math.pow(m[i][j].getValor(), 2);
                        sum3 += Math.pow(0, 2);
                    }
                } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {

                }
            }
            try {
                grauSim[i] = (sum1 * 1.0) / (Math.sqrt(sum2 * 1.0) * Math.sqrt(sum3 * 1.0));
                if (Double.isInfinite(grauSim[i]) || Double.isNaN(grauSim[i])){
                    throw new ArithmeticException();
                }
            }catch (ArithmeticException ex){
                grauSim[i] = 0;
            }
        }
        ordenarFicheirosPorGrau();
    }

    public void ordenarFicheirosPorGrau(){

        String[] tempNomes = new String[ld.getNumDocs()];
        double[] tempGraus = new double[ld.getNumDocs()];
        ArrayList<Integer> indicesUsados = new ArrayList<>();
        int contador = 0;

        while(contador<ld.getNumDocs()){
            int indiceMaiorValor = 0;
            double maiorValor = 0;
            for(int i=0; i<ld.getNumDocs(); i++){
                if(!indicesUsados.contains(i)){
                    if(grauSim[i] >= maiorValor){
                        maiorValor = grauSim[i];
                        indiceMaiorValor = i;
                    }
                }
            }
            tempNomes[contador] = ld.getNomesFicheiros()[indiceMaiorValor];
            tempGraus[contador] = maiorValor;
            indicesUsados.add(indiceMaiorValor);
            contador++;
        }
        ld.setNomesFicheiros(tempNomes);
        grauSim = tempGraus;
    }


    public String[] retornarTodosOrdemGrau(){
        return ld.getNomesFicheiros();
    }

    public String[] retornarFicheirosPorLimite(int limite){
        String[] nomes = new String[limite];
        for(int i=0; i<limite; i++){
            nomes[i]=ld.getNomesFicheiros()[i];
        }
        return nomes;
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

    public double[] getGrauSim() {
        return grauSim;
    }

    public void setGrauSim(double[] grauSim) {
        this.grauSim = grauSim;
    }
}
