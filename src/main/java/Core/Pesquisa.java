package Core;

import java.text.Normalizer;

public class Pesquisa {
    private int m[][];
    private String q[];

    public Pesquisa() {

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
