import Core.Pesquisa;
import Enums.TiposPesquisa;
import Exeption.ExceptionMinimo;
import LeituraFicheiros.LeituraDocumentos;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PesquisaTest {

    //Testar se a remoção de caracteres de pontuação do método eliminarCaracteresPontuacao funciona
    @Test
    public void testId_TP3() throws IOException, NullPointerException {

        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        String input = "olá tudo bem?";
        String output_esperado = "ola tudo bem?";
        String output = p.eliminarCaracteresPontuacao(input);
        assertEquals(output_esperado, output);
    }
    //Testar se a string é transformada em minusculas no método eliminarCaracteresPontuacao
    @Test
    public void testId_TP4() throws IOException, NullPointerException {

        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        String input = "Ola tudo bem? Sim";
        String output_esperado = "ola tudo bem? sim";
        String output = p.eliminarCaracteresPontuacao(input);
        assertEquals(output_esperado, output);
    }

    //Testar se os digitos são removidos no método eliminarDigitos
    @Test
    public void testId_TP5() throws NullPointerException {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        String input = "Eu sou 5vezes mais forte";
        String output = p.eliminarDigitos(input);
        assertEquals("Eu sou vezes mais forte", output);
    }

    //Testar se o método contarNumDocsContemPalavra devolve o número de documentos q contem uma palavra especifica
    @Test
    public void testId_TP6() throws IOException, NullPointerException {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        LeituraDocumentos ld = new LeituraDocumentos("files/filesTestes/TP/");
        String[] conteudos = ld.lerDocumentos();
        String palavra = "Testar";
        assertEquals(p.contarNumDocsContemPalavra(conteudos, palavra), 2);
    }

    //Testar se o método definirMatrizM() guarda a primeira palavra do primeiro documento no indice certo
    @Test
    public void testId_TP7() throws IOException, NullPointerException {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        p.definirMatrizM();
        String palavra = "teste";
        assertEquals(palavra, p.getM()[2][0].getPalavra());
    }

    //Testar se o método definirMatrizM() guarda a contagem da primeira palavra do primeiro documento no indice certo
    @Test
    public void testId_TP8() throws IOException, NullPointerException {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        p.definirMatrizM();
        assertEquals(1, p.getM()[0][0].getContagem());
    }

    // Testar se o método definirMatrizM() tem a matriz com o número de colunas igual ao máximo de palavras diferentes
    @Test
    public void testId_TB4() throws IOException, NullPointerException {
        Pesquisa p = new Pesquisa("files/filesTestes/TB5/");
        p.definirMatrizM();
        assertEquals(3, p.getM()[0].length);
    }

    // Testar se o método definirMatrizM() tem a matriz com o número de colunas igual a 1 com a documento com 3 palavras iguais
    @Test
    public void testId_TB5() throws IOException, NullPointerException {
        Pesquisa p = new Pesquisa("files/filesTestes/TB6/");
        p.definirMatrizM();
        assertEquals(1, p.getM()[0].length);
    }

    //Testar se o método definirMatrizM() guarda a contagem de mais que uma palavra igual
    @Test
    public void testId_TB6() throws IOException, NullPointerException {
        Pesquisa p = new Pesquisa("files/filesTestes/TB6/");
        p.definirMatrizM();
        assertEquals(3, p.getM()[0][0].getContagem());
    }

    //Testar se o método definirMatrizQ() guarda as palavras pesquisadas no array
    @Test
    public void testId_TP9() throws IOException, NullPointerException {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        p.definirMatrizQ("ola ola tudo bem");
        String[] expected = {"ola", "tudo", "bem"};
        assertEquals(expected[0], p.getQ()[0].getPalavra());
        assertEquals(expected[1], p.getQ()[1].getPalavra());
        assertEquals(expected[2], p.getQ()[2].getPalavra());
    }


    //Testar se o método definirMatrizQ() guarda a quantidade certa de palavras iguais pesquisadas no array
    @Test
    public void testId_TP10() throws IOException, NullPointerException {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        p.definirMatrizQ("ola ola tudo bem");
        int[] expected = {2, 1, 1};
        assertEquals(expected[0], p.getQ()[0].getContagem());
        assertEquals(expected[1], p.getQ()[1].getContagem());
        assertEquals(expected[2], p.getQ()[2].getContagem());
    }

    //Testar metodo de verificacaoSemelhanca, verificar o calculo de equaçao
    @Test
    public void testId_TP11() throws IOException, NullPointerException {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        p.definirMatrizM();
        p.verificacaoSemelhanca();
        double calculo = p.getM()[0][0].getContagem() * (1.0 + java.lang.Math.log10((3.0) / (2.0)));
        assertEquals(calculo, p.getM()[0][0].getValor());
    }

    //Verificar se o grau de semelhança é superior a 0 se ouver grau de semelhança entre os ficheiros e a pesquisa
    @Test
    public void testId_TP12() throws IOException, NullPointerException {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        p.definirMatrizM();
        p.definirMatrizQ("teste teste teste");
        p.verificacaoSemelhanca();
        p.grauSemelhanca();
        assertTrue(0 < p.getGrauSim()[0]);
    }

    //Verificar se o grau de semelhança é 0 se não ouver grau de semelhança entre os ficheiros e a pesquisa
    @Test
    public void testId_TP13() throws IOException, NullPointerException {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        p.definirMatrizM();
        p.definirMatrizQ("adoro");
        p.verificacaoSemelhanca();
        p.grauSemelhanca();
        assertEquals(0 , p.getGrauSim()[2]);
    }

    //Verificar se o grau de semelhança é ordenado
    @Test
    public void testId_TP14() throws IOException, NullPointerException {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        p.definirMatrizM();
        p.definirMatrizQ("teste");
        p.verificacaoSemelhanca();
        p.grauSemelhanca();
        assertEquals(p.getLd().getNomesFicheiros()[0] , "ficheiro 4.txt");
    }

    //Verificar se a limitação de ficheiros foi realizada
    @Test
    public void testId_TP15() throws IOException, NullPointerException, ExceptionMinimo {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        p.definirMatrizM();
        p.definirMatrizQ("teste");
        p.verificacaoSemelhanca();
        p.grauSemelhanca();
        String[] values = p.retornarFicheirosPorLimite(2);
        assertEquals(2, values.length);
    }

    //Verificar se método retornaFicheirosLimiteGrau retorna apenas os ficheiros com grau superior ao indicado
    @Test
    public void testId_TP16() throws IOException, NullPointerException, ExceptionMinimo {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        p.definirMatrizM();
        p.definirMatrizQ("teste teste teste");
        p.verificacaoSemelhanca();
        p.grauSemelhanca();
        assertEquals(p.retornarFicheirosLimiteGrau(100).length, 1);
    }

    //Testar se o metodo construtor da class Pesquisa não aceita valor null
    @Test
    public void testID_TP17(){
        assertThrows(NullPointerException.class,
                ()->{
                    Pesquisa p = new Pesquisa(null);
                });
    }

    //Testar se o metodo pesquisar não aceita null no valor pesquisa
    @Test
    public void testID_TP18(){
        assertThrows(NullPointerException.class,
                ()->{
                    Pesquisa p = new Pesquisa("files/filesTestes/TP/");
                    String[] list = p.pesquisar(null, TiposPesquisa.NORMAL, 0);
                });
    }

    //Testar se o metodo pesquisar não aceita null no valor tipoPesquisa
    @Test
    public void testID_TP19(){
        assertThrows(NullPointerException.class,
                ()->{
                    Pesquisa p = new Pesquisa("files/filesTestes/TP/");
                    p.pesquisar("null", null, 0);
                });
    }

    //Testar se o metodo pesquisar não aceita -2 no valor input
    @Test
    public void testID_TP20(){
        assertThrows(ExceptionMinimo.class,
                ()->{
                    Pesquisa p = new Pesquisa("files/filesTestes/TP/");
                    p.pesquisar("null", TiposPesquisa.NORMAL, -2);
                });
    }

    //Testar se o metodo pesquisar aceita -1 no valor input
    @Test
    public void testID_TP21() throws IOException, ExceptionMinimo {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        String[] list  = p.pesquisar("null", TiposPesquisa.NORMAL, -1);
        String[] compare = new String[]{"ficheiro 4.txt","ficheiro 2.docx","ficheiro 1.txt"};
        assertEquals(compare[0], list[0]);
        assertEquals(compare[1], list[1]);
        assertEquals(compare[2], list[2]);
    }

    //Testar se o metodo retornarFicheirosLimiteGrau não aceita -1 no valor input
    @Test
    public void testID_TP22(){
        assertThrows(ExceptionMinimo.class,
                ()->{
                    Pesquisa p = new Pesquisa("files/filesTestes/TP/");
                    p.definirMatrizQ("teste");
                    p.definirMatrizM();
                    p.verificacaoSemelhanca();
                    p.grauSemelhanca();
                    p.retornarFicheirosLimiteGrau(-1);
                });
    }

    //Testar se o metodo retornarFicheirosLimiteGrau aceita 0 no valor input
    @Test
    public void testID_TP23() throws IOException, ExceptionMinimo {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        p.definirMatrizQ("teste");
        p.definirMatrizM();
        p.verificacaoSemelhanca();
        p.grauSemelhanca();
        String[] list = p.retornarFicheirosLimiteGrau(0);
        String[] esperado = new String[]{"ficheiro 4.txt (Grau de similaridade: 100,00%)", "ficheiro 2.docx (Grau de similaridade: 0,00%)", "ficheiro 1.txt (Grau de similaridade: 0,00%)"};
        System.out.println(list[0]);
        System.out.println(list[1]);
        System.out.println(list[2]);
        assertEquals(esperado[0], list[0]);
        assertEquals(esperado[1], list[1]);
        assertEquals(esperado[2], list[2]);
    }

    //Testar se o metodo retornarFicheirosPorLimite não aceita -1 no valor input
    @Test
    public void testID_TP24(){
        assertThrows(ExceptionMinimo.class,
                ()->{
                    Pesquisa p = new Pesquisa("files/filesTestes/TP/");
                    p.definirMatrizQ("teste");
                    p.definirMatrizM();
                    p.verificacaoSemelhanca();
                    p.grauSemelhanca();
                    p.retornarFicheirosPorLimite(-1);
                });
    }

    //Testar se o metodo retornarFicheirosPorLimite aceita 0 no valor input
    @Test
    public void testID_TP25() throws IOException, ExceptionMinimo {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        p.definirMatrizQ("teste");
        p.definirMatrizM();
        p.verificacaoSemelhanca();
        p.grauSemelhanca();
        String[] list = p.retornarFicheirosPorLimite(0);
        String[] esperado = new String[]{};
        assertEquals(esperado.length, list.length);

    }

    //Testar se o metodo LeituraDocumentos não aceita null no valor path
    @Test
    public void testID_TP26(){
        assertThrows(NullPointerException.class,
                ()->{
                    LeituraDocumentos l = new LeituraDocumentos(null);
                });
    }
}