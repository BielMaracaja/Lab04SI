package ufcg.edu.br.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tarefas {

    @Id@GeneratedValue
    private Integer id;

    private String nome;
    private String descricao;
    private boolean feito;
    public String prioridade;
    private String categoria;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubTarefas> subTarefas;

    public Tarefas() {
        this.feito = false;
        this.subTarefas = new ArrayList<SubTarefas>();
    }

    public void addSubTarefas(SubTarefas subTarefa){
        this.subTarefas.add(subTarefa);
    }

    public SubTarefas procuraSubTarefa(SubTarefas subTarefa){
        int indice = this.subTarefas.indexOf(subTarefa);
        return this.subTarefas.get(indice);
    }

    public SubTarefas procuraSubTarefaIndice(Integer subTarefaId){
        for(SubTarefas subTarefa : this.subTarefas){
            if(subTarefa.getId().equals(subTarefaId)){
                return subTarefa;
            }
        }
        return null;
    }

    public boolean excluirSubTarefa(Integer subTarefaid){
        SubTarefas subTarefaEncontrada = procuraSubTarefaIndice(subTarefaid);

        if(subTarefaEncontrada != null){
            this.subTarefas.remove(subTarefaEncontrada);
            return true;
        }
        else{
            return false;
        }
    }

    public SubTarefas mudaSubTarefa(SubTarefas subtarefa){
        SubTarefas alteraSub = procuraSubTarefaIndice(subtarefa.getId());
        alteraSub.setNome(subtarefa.getNome());
        alteraSub.setFeito(subtarefa.concluida());
        return alteraSub;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isFeito() {
        return feito;
    }

    public void setFeito(boolean feito) {
        this.feito = feito;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<SubTarefas> getSubTarefas() {
        return subTarefas;
    }

    public void setSubTarefas(List<SubTarefas> subTarefas) {
        this.subTarefas = subTarefas;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tarefas other = (Tarefas) obj;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        return true;
    }
}
