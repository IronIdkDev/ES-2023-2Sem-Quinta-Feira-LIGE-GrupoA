package softwareeng.project;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.opencsv.CSVWriter;
import java.util.logging.Logger;
import java.util.logging.Level;

import java.io.*;
import java.util.Iterator;


public class JSonToCSV{

    private static final Logger LOGGER = Logger.getLogger(JSonToCSV.class.getName());
    private final Iterator<JsonNode> elements;
    private final CSVWriter writer;

    public JSonToCSV(String fileName) throws IOException {
        JsonNode rootNode = new ObjectMapper().readTree(new File(fileName));
        elements =  rootNode.elements();
        writer = new CSVWriter(new FileWriter("data_temp.csv"));
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
        changeCommas();
    }

    private void changeCommas() throws IOException {
        final String inputFileName = "data_temp.csv";
        final String outputFileName = "data.csv";
    
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
             BufferedWriter writer2 = new BufferedWriter(new FileWriter(outputFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replace(",", ";");
                writer2.write(line);
                writer2.newLine();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Exception occurred", e);
        } finally {
            boolean deleted = java.nio.file.Files.deleteIfExists(java.nio.file.Paths.get(inputFileName));
            if (!deleted) {
                LOGGER.info("Failed to delete temporary file");
            }
        }
    }    
}