package Demo;


import JFrames.PesquisaJFrame;
import Core.Pesquisa;
public class Main {

    public static void main(String[] args) {
        PesquisaJFrame pf = new PesquisaJFrame(new Pesquisa());
        pf.setVisible(true);
    }

}