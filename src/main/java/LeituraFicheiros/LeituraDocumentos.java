package LeituraFicheiros;

import java.io.*;

public class LeituraDocumentos {
    private final String FORMATO_TXT = "txt";
    private final String FORMATO_DOC = "doc";
    private final String FORMATO_DOCX = "docx";
    private final String FORMATO_PDF = "pdf";

    int numDocs ;
    String path ;

    public LeituraDocumentos(String path) {
        this.path = path;
        File folder = new File(this.path);
        File[] list = folder.listFiles();
        numDocs = list.length;
    }

    public int getNumDocs() {
        return numDocs;
    }

    public String[] lerDocumentos() throws IOException {

        if(numDocs == 0){
            return null;
        }
        //Lista de ficheiros
        File folder = new File(this.path);
        File[] listaFicheiros = folder.listFiles();

        //Lista de strings com os conteudos dos ficheiros
        String[] conteudosDocumentos = new String[this.numDocs];

        for(int i=0; i<listaFicheiros.length; i++){
            String name = listaFicheiros[i].getName();
            String formato = name.split("\\.")[1];
            switch(formato){
                case FORMATO_TXT:
                    conteudosDocumentos[i] = lerTxt(listaFicheiros[i].getName());
                    break;
                case FORMATO_DOC:
                    conteudosDocumentos[i] = lerDoc(listaFicheiros[i].getName());
                    break;
                case FORMATO_DOCX:
                    conteudosDocumentos[i] = lerDocx(listaFicheiros[i].getName());
                    break;
                case FORMATO_PDF:
                    conteudosDocumentos[i] = lerPdf(listaFicheiros[i].getName());
                    break;
            }
        }

        return conteudosDocumentos;
    }

    private String lerTxt(String name) throws IOException {
        InputStream is = new FileInputStream(path + name);
        BufferedReader buf = new BufferedReader((new InputStreamReader(is)));

        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();

        while(line != null){
            sb.append(line);
            line = buf.readLine();
        }

        return sb.toString();
    }
    private String lerDoc(String name) {

        return "";
    }
    private String lerDocx(String name) {
        return "";
    }
    private String lerPdf(String name) {
        return "";
    }
}
