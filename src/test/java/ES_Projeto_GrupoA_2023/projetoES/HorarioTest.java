package ES_Projeto_GrupoA_2023.projetoES;

import org.junit.jupiter.api.Test;
import softwareeng.project.CSVToJson;
import softwareeng.project.Horario;
import softwareeng.project.Session;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Classe de testes unitários da classe Horario
 */
class HorarioTest {

    @Test
    void testGetCurso() {
        Horario h = new Horario("LIGE", 1, "C5");
        assertEquals("LIGE", h.getCurso());
    }

    @Test
    void testSetCurso() {
        Horario h = new Horario("LIGE", 1, "C5");
        h.setCurso("LIGE-PL");
        assertEquals("LIGE-PL", h.getCurso());
    }

    @Test
    void testGetHorarioCSV() {
        Horario horario = new Horario("LP", 1, "PA3");
        List<Session> list = horario.getHorario("data.csv");
        assertEquals("LP", list.get(0).getCurso());
        assertEquals("Psicologia Social", list.get(0).getUc());
        assertEquals("PA4; PA3; PA2; PA1", list.get(0).getTurma());
    }

    @Test
    void testGetHorarioJson() {
        Horario horario = new Horario("LP", 1, "PA3");
        List<Session> list = horario.getHorario("horario.json");
        assertEquals("LP", list.get(0).getCurso());
        assertEquals("Psicologia Social", list.get(0).getUc());
        assertEquals("PA4, PA3, PA2, PA1", list.get(0).getTurma());
    }

    //Teste para verificar se a lista retornada contém apenas sessões com a unidade curricular especificada
    @Test
    void testGetHorarioPorUnidadeCurricular() {
        Horario horario = new Horario("LP", 1, "PA3");
        List<Session> list = horario.getHorarioPorUnidadeCurricular("Psicologia Social", "horario.json");
        assertEquals(1, list.size());
        for (Session session : list) {
            assertEquals("Psicologia Social", session.getUc());
        }
    }
}
