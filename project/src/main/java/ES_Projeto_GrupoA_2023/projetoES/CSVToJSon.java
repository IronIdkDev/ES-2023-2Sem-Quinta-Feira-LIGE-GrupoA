package ES_Projeto_GrupoA_2023.projetoES;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.Gson;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
public class CSVToJSon {
	private String curso;
	private String uc;
	private String turno;
	private String turma;
	private int incritos;
	private String diaSemana;
	private String horaInicio;
	private String horaFim;
	private String dataAula;
	private String salaAtribuida;
	private int lotacao;
	private String path;
	
	public CSVToJSon() {

	}
	
	/* Método que converte um ficheiro CSV para um ArrayList */
	public ArrayList<CSVToJSon> convertCSVToArray(String path) {
		ArrayList<CSVToJSon> array = new ArrayList<>();
		
		try (CSVReader reader = new CSVReaderBuilder(new FileReader(path))
		        .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
		        .build()) {
			//Passa duas linhas à frente pois a primeira é não possui texto e a segunda possui as colunas da tabela
			reader.skip(2); 
			String[] linha;
			try {
				while((linha = reader.readNext()) != null) {
					CSVToJSon csv = new CSVToJSon();
					csv.setCurso(linha[0]);
					csv.setUc(linha[1]);
					csv.setTurno(linha[2]);
					csv.setTurma(linha[3]);
					csv.setIncritos(Integer.parseInt(linha[4]));
					csv.setDiaSemana(linha[5]);
					csv.setHoraInicio(linha[6]);
					csv.setHoraFim(linha[7]);
					csv.setDataAula(linha[8]);
					csv.setSalaAtribuida(linha[9]);
					
					if(linha[10] == "" ) {
						linha[10] = "0";
					}else {
						csv.setLotacao(Integer.parseInt(linha[10]));
					}
					array.add(csv);
				}
			} catch (CsvValidationException e) {
				System.err.println("Erro: problemas na validação CSV");
				
			}
			
		} catch (FileNotFoundException e) {
			System.err.println("Erro:O ficheiro não foi encontrado! Verifique se o path está correto");
		} catch (IOException e) {
			System.err.println("Erro: Não foi possível ler o ficheiro");
		}
		return array;
	}	
	
	public void convertArrayToJson(ArrayList<CSVToJSon> array) {
		
		Gson gson = new Gson();
		String json = gson.toJson(array);
		System.out.println(json);
		
	}
	
	
	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}
	public String getUc() {
		return uc;
	}
	public void setUc(String uc) {
		this.uc = uc;
	}
	public String getTurno() {
		return turno;
	}
	public void setTurno(String turno) {
		this.turno = turno;
	}
	public String getTurma() {
		return turma;
	}
	public void setTurma(String turma) {
		this.turma = turma;
	}
	public int getIncritos() {
		return incritos;
	}
	public void setIncritos(int inscritos) {
		this.incritos = inscritos;
	}
	public String getDiaSemana() {
		return diaSemana;
	}
	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}
	public String getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	public String getHoraFim() {
		return horaFim;
	}
	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
	}
	public String getDataAula() {
		return dataAula;
	}
	public void setDataAula(String dataAula) {
		this.dataAula = dataAula;
	}
	public String getSalaAtribuida() {
		return salaAtribuida;
	}
	public void setSalaAtribuida(String salaAtribuida) {
		this.salaAtribuida = salaAtribuida;
	}
	public int getLotacao() {
		return lotacao;
	}
	public void setLotacao(int lotacao) {
		this.lotacao = lotacao;
	}

	
	
	
	

}
