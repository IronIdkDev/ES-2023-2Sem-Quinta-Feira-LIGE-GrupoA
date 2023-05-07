package ES_Projeto_GrupoA_2023.projetoES;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;
import softwareeng.project.IcsToJson;

/**
 * Classe de testes unitarios para a class IcsToJson
 */


public class IcsToJsonTest {
    private IcsToJson converter;

    @Before
    public void setUp() throws Exception {
        String filePath = "C:\\Users\\carol\\OneDrive\\Documentos\\GitHub\\ES-2023-2Sem-Quinta-Feira-LIGE-GrupoAasd\\rafetelvino@gmail.com.ics";
        converter = new IcsToJson(filePath);
    }

    @Test
    public void testConvertFile() throws IOException, ParseException {
        boolean success = converter.convertFile();

        assertTrue(success);
        System.out.println("aqui1");

        String jsonFilePath = "C:\\Users\\carol\\OneDrive\\Documentos\\GitHub\\ES-2023-2Sem-Quinta-Feira-LIGE-GrupoAasd\\";
        System.out.println("aqui1");
        File jsonFile = new File(jsonFilePath);

        assertTrue(jsonFile.exists());
        assertTrue(jsonFile.isFile());
        assertTrue(jsonFile.length() > 0);
    }
}