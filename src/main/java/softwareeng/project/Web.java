package softwareeng.project;

//TODO: Divide this class into each type of file conversion

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
*Classe para ler, baixar e converter conteúdo de páginas web.
*/
public class Web {

	/**
	*Lê o conteúdo da página da URL fornecida e imprime uma mensagem de sucesso se a leitura for bem sucedida.
	*@param url a URL da página a ser lida
	*/
	public void ReadWeb(URL url) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			in.close();
			System.out.println("URL lido com sucesso.");
		} catch (MalformedURLException e) {
			System.out.println("URL inválido: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Erro ao ler URL: " + e.getMessage());
		}
	}


	/**
	*Faz o download do conteúdo de uma página na web a partir da URL especificada e o converte em um arquivo CSV.
	*@param url a URL da página a ser baixada e convertida em um arquivo CSV
	*@throws IOException se ocorrer um erro de I/O ao baixar, analisar ou gravar o conteúdo da página
	*/
	public void URLToCSV(URL url) throws IOException{

		//usa uma conexão URLConnection para baixar o conteúdo da página
		URLConnection connection = url.openConnection();
		//lê cada linha
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		//para analisar o conteúdo CSV e criar uma lista de objetos
		CSVParser parser = CSVFormat.DEFAULT.parse(in);
		List<CSVRecord> records = parser.getRecords();
		in.close();

		//para gravar cada registro em um arquivo CSV
		CSVPrinter printer = new CSVPrinter(new FileWriter("output.csv"), CSVFormat.DEFAULT);
		for (CSVRecord record : records) {
			printer.printRecord(record);
		}
		printer.close();

		//o arquivo é lido e o seu conteúdo é exibido usando um objeto
		BufferedReader reader = new BufferedReader(new FileReader("output.csv"));
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		reader.close();
	}
	
	/**
	*Converte o conteúdo de um arquivo JSON a partir da URL fornecida e salva-o em um arquivo local.
	*@param url a URL do arquivo JSON a ser convertido e salvo
	*@throws IOException se ocorrer um erro de I/O durante a leitura ou gravação do arquivo JSON
	*/
	public void URLToJson(URL url) throws IOException {
		URLConnection connection = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		//Lê o conteúdo JSON do ficheiro
		BufferedWriter writer = new BufferedWriter(new FileWriter("output.json"));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			writer.write(inputLine);
		}
		in.close();
		writer.close();

		//Lê o ficheiro JSON e imprime
		BufferedReader reader = new BufferedReader(new FileReader("output.json"));
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		reader.close();
	}

	/**
	*Faz o download do conteúdo de uma página web a partir da URL fornecida e salva-o em um arquivo local com o nome "web_content.txt".
	*@param url a URL da página web de onde baixar o conteúdo
	*@throws IOException se ocorrer um erro de I/O durante a leitura ou gravação do conteúdo da página web
	*/
	public void downloadWebContent(URL url) throws IOException {
		URLConnection connection = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		BufferedWriter writer = new BufferedWriter(new FileWriter("web_content.txt"));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			writer.write(inputLine);
		}
		in.close();
		writer.close();
	}

}