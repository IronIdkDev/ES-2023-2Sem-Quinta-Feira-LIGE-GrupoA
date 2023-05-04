package softwareeng.project;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Classe que representa um Aluno
 * O Aluno é identificado pelo seu nome, número, curso a que pertence e aulas que frequenta
 */

public class Aluno {

    private String nome;
    private int num;
    private String curso;

    private String turma;
    private List<String> aulas;

    /**
     * Construtor de Aluno
     * @param nome
     * @param num
     */
    public Aluno(String nome, int num) {
        this.nome = nome;
        this.num = num;
    }

    /**
     * Construtor de Aluno
     * @param nome
     * @param num
     * @param curso
     */
    public Aluno(String nome, int num, String curso) {
        this.nome = nome;
        this.num = num;
        this.curso = curso;
    }

    /**
     * Construtor de Aluno
     * @param nome
     * @param num
     * @param curso
     * @param aulas
     */
    public Aluno(String nome, int num, String curso, List<String> aulas) {
        this.nome = nome;
        this.num = num;
        this.curso = curso;
        this.aulas = aulas;
    }

    /**
     * Retorna o nome do Aluno
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Muda o nome do Aluno
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o número do Aluno
     * @return num
     */
    public int getNum() {
        return num;
    }

    /**
     * Muda o número do aluno
     * @param num
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     * Retorna o curso do Aluno
     * @return curso
     */
    public String getCurso() {
        return curso;
    }

    /**
     * Muda o curso do Aluno
     * @param curso
     */
    public void setCurso(String curso) {
        this.curso = curso;
    }

    /**
     * Retorna a lista de UCs que o Aluno frequenta
     * @return aulas
     */
    public List<String> getAulas() {
        return aulas;
    }

    /**
     * Muda a lista de UCs do Aluno
     * @param aulas
     */
    public void setAulas(List<String> aulas) {
        this.aulas = aulas;
    }

    /**
     * Adiciona à lista aulas de Ucs as Ucs que o aluno irá frequentar através de um ficheiro
     * @param path caminho do ficheiro
     */
    private void addUcCurso(String path){
        List<Session> sessions = converFileToArray(path);
        Iterator<Session> iterator= sessions.iterator();
        while(iterator.hasNext()) {
            Session aux = iterator.next();
            if (!aux.getCurso().contains(curso) || !aux.getTurma().contains(turma)) {
                iterator.remove();
            }
        }
        for(Session s: sessions){
            addUc(s.getUc());
            }
        }
    private void addUc(String s) {
        if (aulas.indexOf(s) == -1) {
            aulas.add(s);
        }
    }

    /**
     * Converte um ficheiro para List<Session>
     * @param path caminho do ficheiro
     * @return list com todas as aulas do ficheiro
     */
    private List<Session> converFileToArray(String path){
        File file = new File(path);
        List<Session> list = new ArrayList<>();
        if(path.endsWith("csv")){
            CSVToJson csv1 = new CSVToJson();
            list = csv1.convertCSVToArray(path);

        }else if(path.endsWith("json")){
            list = JSonToCSV.convertJsonToArray(path);
        }
        return list;
    }

    /**
     * Retorna a turma do aluno
     * @return turma
     */
    public String getTurma() {
        return turma;
    }

    /**
     * Muda a turma do aluno
     * @param turma
     */
    public void setTurma(String turma) {
        this.turma = turma;
    }
}
