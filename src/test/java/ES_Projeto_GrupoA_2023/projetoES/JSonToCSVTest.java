package ES_Projeto_GrupoA_2023.projetoES;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import softwareeng.project.JSonToCSV;
import softwareeng.project.Session;


import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static softwareeng.project.JSonToCSV.convertJsonToArray;

/**
 * Classe de testes unitários da classe JSonToCSV
 */
public class JSonToCSVTest {


    private JSonToCSV jsonToCsv;

    @Before
    public void setUp() throws Exception {
        jsonToCsv = new JSonToCSV("horario.json");
    }
    @Test
    public  void convertFile(){
        try {
            jsonToCsv.convertFile();
            ObjectMapper mapper = new ObjectMapper();
            File arquivo1 = new File("data123.csv");
            File arquivo2 = new File("dataTest.csv");
            Object obj1;
            try {
                obj1 = mapper.readValue(arquivo1, Object.class);
                Object obj2 = mapper.readValue(arquivo2, Object.class);
                assertEquals(obj1, obj2);
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void convertJsonToArrayTest(){
        List<Session> session = convertJsonToArray("horario.json");
        assertEquals("ME", session.get(0).getCurso());

    }


}
