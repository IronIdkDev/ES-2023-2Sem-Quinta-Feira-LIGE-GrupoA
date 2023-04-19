package softwareeng.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CSVToJSon {
    @JsonProperty("Curso")
    private String curso;

    @JsonProperty("Unidade Curricular")
    private String uc;

    @JsonProperty("Turno")
    private String turno;

    @JsonProperty("Turma")
    private String turma;

    @JsonProperty("Inscritos no turno")
    private int inscritos;

    @JsonProperty("Dia da semana")
    private String diaSemana;

    @JsonProperty("Hora início da aula")
    private String horaInicio;

    @JsonProperty("Hora fim da aula")
    private String horaFim;

    @JsonProperty("Data da aula")
    private String dataAula;

    @JsonProperty("Sala atribuída à aula")
    private String salaAtribuida;

    @JsonProperty("Lotação da sala")
    private int lotacao;
	
    private static final Logger LOGGER = Logger.getLogger("CSVToJSON");

	public CSVToJSon() { /* This is a default constructor with no paramerters */ }	

	/* Método que converte um ficheiro CSV para um ArrayList */
	public List<CSVToJSon> convertCSVToArray(String path) {
		ArrayList<CSVToJSon> array = new ArrayList<>();
		try (CSVReader reader = new CSVReaderBuilder(new FileReader(path))
				.withCSVParser(new CSVParserBuilder().withSeparator(';').build())
				.build()) {
			reader.skip(2);
			String[] linha;
			while ((linha = reader.readNext()) != null) {
				CSVToJSon csv = new CSVToJSon();
				csv.setCurso(linha[0]);
				csv.setUc(linha[1]);
				csv.setTurno(linha[2]);
				csv.setTurma(linha[3]);
				csv.setInscritos(Integer.parseInt(linha[4]));
				csv.setDiaSemana(linha[5]);
				csv.setHoraInicio(linha[6]);
				csv.setHoraFim(linha[7]);
				csv.setDataAula(linha[8]);
				csv.setSalaAtribuida(linha[9]);
	
				if (linha[10].equals("")) {
					linha[10] = "0";
				} else {
					csv.setLotacao(Integer.parseInt(linha[10]));
					LOGGER.severe("Erro: não foi possível converter o valor para inteiro");
				}
				array.add(csv);
			}
		} catch (FileNotFoundException e) {
			LOGGER.severe("Erro: O ficheiro não foi encontrado! Verifique se o path está correto");
		} catch (CsvValidationException e) {
			LOGGER.severe("Erro: problemas na validação CSV");
		} catch (IOException e) {
			LOGGER.severe("Erro: Não foi possível ler o ficheiro");
		}
		return array;
	}		
	
	public void convertArrayToJson(List<CSVToJSon> array) {
		//Cria um mapeamento novo para mapear dados json em java
		ObjectMapper mapa = new ObjectMapper();
		//Seleciona a forma como o ficheiro será escrito tornando-o mais claro para ser lido
        mapa.enable(SerializationFeature.INDENT_OUTPUT);
        //Escreve os dados json num ficheiro
        ObjectWriter writer = mapa.writerWithDefaultPrettyPrinter();

        File file = new File("horario.json");
        try {
			writer.writeValue(file, array);
		} catch (JsonGenerationException e) {
			LOGGER.severe("Erro: problemas na escrita do ficheiro ");
		} catch (JsonMappingException e) {
			LOGGER.severe("Erro: problemas no mapeamento do ficheiro");
		} catch (IOException e) {
			LOGGER.severe("Erro: Não foi possível encontrar o ficheiro ou ficheiro inválido");
		}		
        
        LOGGER.info("Sucesso: ficheiro criado com sucesso");
        
	}

	public void convertCSVToJson(String path) {
		
		ArrayList<CSVToJSon> array = (ArrayList<CSVToJSon>) convertCSVToArray(path);
		convertArrayToJson(array);
		
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
	public int getInscritos() {
		return inscritos;
	}
	public void setInscritos(int inscritos) {
		this.inscritos = inscritos;
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