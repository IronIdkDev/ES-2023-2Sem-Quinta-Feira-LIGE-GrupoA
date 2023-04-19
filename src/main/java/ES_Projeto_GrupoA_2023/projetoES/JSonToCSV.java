package ES_Projeto_GrupoA_2023.projetoES;

import java.io.*;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.opencsv.CSVWriter;


public class JSonToCSV{

    private Iterator<JsonNode> elements;
    private CSVWriter writer;

    public JSonToCSV(File file) throws IOException {
        JsonNode rootNode = new ObjectMapper().readTree(file);
        elements =  rootNode.elements();
        writer = new CSVWriter(new FileWriter("src/main/resources/data_temp.csv"));
        createSchema();
    }

    private void createSchema(){
        String[] line = new String[11];
        line[0] = "Curso";
        line[1] = "Unidade Curricular";
        line[2] = "Turno";
        line[3] = "Turma";
        line[4] = "Inscritos no turno";
        line[5] = "Dia da semana";
        line[6] = "Hora início da aula";
        line[7] = "Hora fim da aula";
        line[8] = "Data da aula";
        line[9] = "Sala atribuída à aula";
        line[10] = "Lotação da sala";
        writer.writeNext(line);
    }

    public void convertFile() throws IOException {
        while (elements.hasNext()) {
            ObjectNode object = (ObjectNode) elements.next();
            String[] line = new String[11];
            line[0] = object.get("Curso").asText();
            line[1] = object.get("Unidade Curricular").asText();
            line[2] = object.get("Turno").asText();
            line[3] = object.get("Turma").asText();
            line[4] = object.get("Inscritos no turno").asText();
            line[5] = object.get("Dia da semana").asText();
            line[6] = object.get("Hora início da aula").asText();
            line[7] = object.get("Hora fim da aula").asText();
            line[8] = object.get("Data da aula").asText();
            line[9] = object.get("Sala atribuída à aula").asText();
            line[10] = object.get("Lotação da sala").asText();
            writer.writeNext(line);
        }
        writer.close();
        changeCommas(new File("src/main/resources/data_temp.csv"));
    }

    private void changeCommas(File csvFile){
        try{
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data_temp.csv"));
            BufferedWriter writer2 = new BufferedWriter(new FileWriter("src/main/resources/data.csv"));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll(",", ";");
                writer2.write(line);
                writer2.newLine();
            }
            reader.close();
            writer2.close();

            new File("src/main/resources/data_temp.csv").delete();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        try {
            JSonToCSV x = new JSonToCSV(new File("src/main/resources/example-schedule.json"));
           x.convertFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}