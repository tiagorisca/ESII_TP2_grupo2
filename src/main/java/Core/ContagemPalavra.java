package Core;

public class ContagemPalavra {
    private String palavra;
    private int contagem;
    private double valor;

    public ContagemPalavra(String palavra, int contagem) {
        this.palavra = palavra;
        this.contagem = contagem;
    }

    public ContagemPalavra() { }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public int getContagem() {
        return contagem;
    }

    public void setContagem(int contagem) {
        this.contagem = contagem;
    }
}
