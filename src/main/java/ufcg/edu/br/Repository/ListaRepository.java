package ufcg.edu.br.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ufcg.edu.br.Model.ListaTarefas;

@Repository
public interface ListaRepository extends JpaRepository<ListaTarefas, Integer> {

}
