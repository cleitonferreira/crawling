package br.com.crawling;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MainTxt {

    static final String CAMINHO_ARQUIVO = "dados.txt";

    public static void main(String args[]) throws Exception {
            // Carrega o caminho do arquivo em um objeto do tipo Path
            Path p = Paths.get(CAMINHO_ARQUIVO);
            System.out.println("Leitura 1: lendo os bytes");
            byte[] bytesArquivo = Files.readAllBytes(p);
            String textoArquivo = new String(bytesArquivo);
            System.out.println(textoArquivo);
            System.out.println("Leitura 2: convertendo as linhas do arquivo em lista");
            List<String> linhasArquivo = Files.readAllLines(p);
            for (String l : linhasArquivo) {
                    System.out.println("Linha: " + l); 
            }   
            System.out.println("Leitura 3: convertendo as linhas do arquivo em Stream");
            Files.lines(p).forEach(System.out::println);
            System.out.println("Escrita 1: Escrevendo os bytes em um arquivo");
            String linhaNova = "Linha criada em " + new java.util.Date().toString();
            Files.write(p, linhaNova.getBytes());
    } 
	
}
