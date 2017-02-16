package ufcg.edu.br.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ListaTarefas {


    private String nome;

    @Id @GeneratedValue
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarefas> tarefas;

    public ListaTarefas() {
        this.tarefas = new ArrayList<>();
    }

    public void addTarefa(Tarefas tarefa){
        this.tarefas.add(tarefa);
    }

    public Tarefas procuraTarefa(Tarefas tarefa){
        int indiceTarefa = this.tarefas.indexOf(tarefa);

        if(indiceTarefa >= 0){
            return this.tarefas.get(indiceTarefa);
        }
        else{
            return null;
        }
    }

    public Tarefas procuraTarefasIndice(Integer tarefaId){
        for (Tarefas tarefa : this.tarefas){

            if(tarefa.getId().equals(tarefaId)){
                return tarefa;
            }
        }
        return null;
    }

    public boolean excluirTarefa(Integer tarefaId){
        Tarefas tarefaEncontrada = procuraTarefasIndice(tarefaId);

        if(tarefaEncontrada != null){
            this.tarefas.remove(tarefaEncontrada);
            return true;
        }
        else{
            return false;
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Tarefas> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefas> tarefas) {
        this.tarefas = tarefas;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((tarefas == null) ? 0 : tarefas.hashCode());
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
        ListaTarefas other = (ListaTarefas) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (tarefas == null) {
            if (other.tarefas != null)
                return false;
        } else if (!tarefas.equals(other.tarefas))
            return false;
        return true;
    }
}
