package ufcg.edu.br.BancoDeDados;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ufcg.edu.br.Model.ListaTarefas;
import ufcg.edu.br.Model.SubTarefas;
import ufcg.edu.br.Model.Tarefas;
import ufcg.edu.br.Repository.ListaRepository;
import ufcg.edu.br.Repository.TarefaRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class BancoDeDados {

    @Autowired
    private ListaRepository listaRepositorio;

    @Autowired
    private TarefaRepository tarefaRepositorio;

    private static BancoDeDados bancoDados;

    private BancoDeDados(){
    }

    public static BancoDeDados getBanco(){
        if(bancoDados == null){
            bancoDados = new BancoDeDados();
        }
        return bancoDados;
    }

    public List<ListaTarefas> procuraTodasAsListas(){
        return listaRepositorio.findAll();
    }

    public ListaTarefas procuraListaTarefasId(Integer id){
        return listaRepositorio.findOne(id);
    }

    public Tarefas procuraTarefaId(Integer id) { return tarefaRepositorio.findOne(id); }

    public ListaTarefas salvarListaTarefas(ListaTarefas lista){
        return listaRepositorio.save(lista);
    }

    public Tarefas salvarTarefa(Integer listaId, Tarefas tarefa){

        ListaTarefas encontraLista = procuraListaTarefasId(listaId);

        if(encontraLista != null){
            encontraLista.addTarefa(tarefa);
            ListaTarefas salvaLista = salvarListaTarefas(encontraLista);
            Tarefas salvaTerefa = salvaLista.procuraTarefa(tarefa);

            return salvaTerefa;
        }
        else{
            return null;
        }
    }

    public SubTarefas salvarSubTarefa(Integer tarefaId, SubTarefas subtarefa){

        Tarefas encontraTarefa = this.tarefaRepositorio.findOne(tarefaId);

        if(encontraTarefa != null){
            encontraTarefa.addSubTarefas(subtarefa);
            Tarefas salvaTarefa = mudaTarefas(encontraTarefa);
            SubTarefas salvaSub = salvaTarefa.procuraSubTarefa(subtarefa);

            return salvaSub;
        }
        else{
            return null;
        }
    }

    public boolean excluirListaTarefas(Integer id){
        ListaTarefas lista = listaRepositorio.findOne(id);

        if(lista == null){
            return false;
        }
        else{
            listaRepositorio.delete(id);
            return true;
        }
    }

    public boolean excluirTarefa(Integer listaId, Integer tarefaId){
        ListaTarefas lista = procuraListaTarefasId(listaId);

        if(lista == null){
            return false;
        }
        else{
            boolean excluiu = lista.excluirTarefa(tarefaId);

            if(excluiu){
                salvarListaTarefas(lista);
                return true;
            }
            else{
                return false;
            }
        }
    }

    public boolean excluirSubTarefa(Integer tarefaId, Integer SubTarefaId){
        Tarefas encontraTarefa = tarefaRepositorio.findOne(tarefaId);

        if(encontraTarefa == null){
            return false;
        }
        else{
            boolean excluiu = encontraTarefa.excluirSubTarefa(SubTarefaId);

            if(excluiu){
                mudaTarefas(encontraTarefa);
                return true;
            }
            else{
                return false;
            }
        }
    }

    public void excluirTodasListas(){
        listaRepositorio.deleteAll();
    }

    public boolean excluirTodasasTarefas(Integer listaId){
        ListaTarefas encontraLista = listaRepositorio.findOne(listaId);

        if(encontraLista != null){
            encontraLista.getTarefas().clear();
            listaRepositorio.save(encontraLista);

            return true;
        }
        else{
            return false;
        }
    }

    public ListaTarefas mudaNomedaLista(Integer listaId, String nomeNovo){
        ListaTarefas encontraLista = listaRepositorio.findOne(listaId);

        if(encontraLista != null){
            encontraLista.setNome(nomeNovo);
            ListaTarefas salvaLista = listaRepositorio.save(encontraLista);

            return salvaLista;
        }
        else{
            return null;
        }
    }

    public Tarefas mudaTarefas(Tarefas tarefa){
        return tarefaRepositorio.save(tarefa);
    }

    public SubTarefas mudaSubTarefa(Integer idTarefa, SubTarefas subtarefa){
        Tarefas tarefa = procuraTarefaId(idTarefa);
        if(tarefa != null) {
            tarefa.mudaSubTarefa(subtarefa);
            //precisa salvar a tarefa, mas precisa do id da lista
            return subtarefa;
        }
        return null;
    }
}
