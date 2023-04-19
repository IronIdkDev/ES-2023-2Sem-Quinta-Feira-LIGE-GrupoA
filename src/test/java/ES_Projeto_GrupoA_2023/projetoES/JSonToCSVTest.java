package ES_Projeto_GrupoA_2023.projetoES;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;


import java.io.File;
import java.io.IOException;

public class JSonToCSVTest {


    private JSonToCSV jsonToCsv;

    @Before
    public void setUp() throws Exception {
         jsonToCsv = new JSonToCSV("src/main/resources/example-schedule.json");
    }
    @Test
   public  void convertFile(){
        try {
            JSonToCSV jsonToCsv = new JSonToCSV("src/main/resources/example-schedule.json");
            jsonToCsv.convertFile();
            ObjectMapper mapper = new ObjectMapper();
            File arquivo1 = new File("src/main/resources/data.csv");
            File arquivo2 = new File("src/main/resources/dataTest.csv");
            Object obj1;
            try {
                obj1 = mapper.readValue(arquivo1, Object.class);
                Object obj2 = mapper.readValue(arquivo2, Object.class);
                Assert.assertEquals(obj1, obj2);
            } catch (StreamReadException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (DatabindException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
