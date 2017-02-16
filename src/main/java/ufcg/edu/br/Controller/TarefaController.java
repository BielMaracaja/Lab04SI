package ufcg.edu.br.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufcg.edu.br.BancoDeDados.BancoDeDados;
import ufcg.edu.br.Model.SubTarefas;
import ufcg.edu.br.Model.Tarefas;

@RestController
public class TarefaController {

    @Autowired
    private BancoDeDados bancodedados;

    @RequestMapping(method = RequestMethod.POST, value= "/listas/{idLista}", consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tarefas> salvaTarefa(@PathVariable Integer idLista, @RequestBody Tarefas tarefa){
        Tarefas salvaTarefa = bancodedados.salvarTarefa(idLista,tarefa);

        if(salvaTarefa != null){
            return new ResponseEntity<>(salvaTarefa, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/listas/tarefa", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tarefas> mudaTarefa(@RequestBody Tarefas tarefa){
        Tarefas mudaTarefa = bancodedados.mudaTarefas(tarefa);

        return new ResponseEntity<>(mudaTarefa, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value= "/listas/{idLista}/{idTarefa}")
    public ResponseEntity<Tarefas> excluirTarefa(@PathVariable Integer idLista, @PathVariable Integer idTarefa){
        boolean excluiu = bancodedados.excluirTarefa(idLista, idTarefa);

        if(excluiu){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value= "/listas/tarefa/{idTarefa}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubTarefas> salvarSubTarefa(@PathVariable Integer idTarefa, @RequestBody SubTarefas subTarefa){
        SubTarefas salvasubTarefa = bancodedados.salvarSubTarefa(idTarefa, subTarefa);

        if(salvasubTarefa != null){
            return new ResponseEntity<>(salvasubTarefa, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value= "/listas/tarefa/{idTarefa}/{idSubtarefa")
    public ResponseEntity<SubTarefas> excluirSubTarefa(@PathVariable Integer idTarefa, @PathVariable Integer idsubTarefa){
        boolean excluiu = bancodedados.excluirSubTarefa(idTarefa, idsubTarefa);

        if(excluiu){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value="listas/{idTarefa}/subtarefa", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubTarefas> mudaSubTarefa(@PathVariable Integer idTarefa, @RequestBody SubTarefas subtarefa) {
        SubTarefas mudaSubTarefa = bancodedados.mudaSubTarefa(idTarefa, subtarefa);
        return new ResponseEntity<>(mudaSubTarefa, HttpStatus.OK);
    }
}
