package LeituraFicheiros;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LeituraDocumentos {
    private final String FORMATO_TXT = "txt";
    private final String FORMATO_DOCX = "docx";
    private final String FORMATO_PDF = "pdf";

    int numDocs ;
    String path ;
    int maxPalavras;

    public LeituraDocumentos(String path) {
        this.path = path;
        File folder = new File(this.path);
        File[] list = folder.listFiles();
        numDocs = list.length;
        maxPalavras = -1;
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
                case FORMATO_DOCX:
                    conteudosDocumentos[i] = lerDoc(listaFicheiros[i].getName());
                    break;
                case FORMATO_PDF:
                    conteudosDocumentos[i] = lerPdf(listaFicheiros[i].getName());
                    break;
                default:
                    conteudosDocumentos[i] = "";
            }
        }

        return conteudosDocumentos;
    }

    private String lerTxt(String name) throws IOException {
        InputStream is = new FileInputStream(path + name);
        BufferedReader buf = new BufferedReader((new InputStreamReader(is)));

        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();
        boolean first = true;

        while(line != null){
            if(first){
                first = false;
                sb.append(line);
            }else{
                sb.append(" "+line);
            }
            line = buf.readLine();
        }

        vefiricarMaxPalavras(sb.toString());
        return sb.toString();
    }
    private String lerDoc(String name) {
        try {
            File file = new File(path + name);
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());

            XWPFDocument document = new XWPFDocument(fis);

            List<XWPFParagraph> paragraphs = document.getParagraphs();

            String result = "";
            boolean first = true;
            for (XWPFParagraph para : paragraphs) {
                if(first){
                    first = false;
                    result += para.getText();
                }else{
                    result += " " + para.getText();
                }
            }
            fis.close();
            vefiricarMaxPalavras(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String lerPdf(String name) throws IOException {
        PDDocument document = PDDocument.load(new File(path + name));
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        document.close();
        vefiricarMaxPalavras(text);
        return text;
    }

    private void vefiricarMaxPalavras(String text) {
        int contador = 0;
        ArrayList<String> palavrasEncontradas = new ArrayList<>();
        String[] parts = text.split(" ");
        for(String s: parts){
            if(!palavrasEncontradas.contains(s)) {
                contador++;
                palavrasEncontradas.add(s);
            }
        }
        if(this.maxPalavras < contador){
            maxPalavras = contador;
        }
    }

    public int getMaxPalavras() {
        return maxPalavras;
    }


}
