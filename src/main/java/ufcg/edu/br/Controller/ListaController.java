package ufcg.edu.br.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufcg.edu.br.BancoDeDados.BancoDeDados;
import ufcg.edu.br.Model.ListaTarefas;
import ufcg.edu.br.Model.Tarefas;

import java.util.*;

@RestController
public class ListaController {

    @Autowired
    private BancoDeDados bancodedados;

    public ListaController(){}

    @RequestMapping(method = RequestMethod.GET, value = "/listas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ListaTarefas>> procurarTodasasListas(){
        List<ListaTarefas> listas = bancodedados.procuraTodasAsListas();

        return new ResponseEntity<>(listas, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/listas", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListaTarefas> salvaLista(@RequestBody ListaTarefas listadetarefas){
        ListaTarefas salvaLista = bancodedados.salvarListaTarefas(listadetarefas);

        return new ResponseEntity<>(salvaLista, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/listas/{idLista}/{novoNome}")
    public ResponseEntity<ListaTarefas> mudaNomeLista(@PathVariable Integer idLista, @PathVariable String novoNome){
        ListaTarefas mudaLista = bancodedados.mudaNomedaLista(idLista,novoNome);

        if(mudaLista != null){
            return new ResponseEntity<>(mudaLista, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/listas/tarefas/{idLista}")
    public ResponseEntity<ListaTarefas> excluirTodasTarefas(@PathVariable Integer idLista){
        boolean excluiu = bancodedados.excluirTodasasTarefas(idLista);

        if(excluiu){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/listas/{id}")
    public ResponseEntity<ListaTarefas> excluirLista(@PathVariable Integer id){
        boolean excluiu = bancodedados.excluirListaTarefas(id);

        if(excluiu){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/listas")
    public ResponseEntity<ListaTarefas> excluirTodasasLista(){
        bancodedados.excluirTodasListas();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
