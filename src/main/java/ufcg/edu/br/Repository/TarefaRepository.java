package ufcg.edu.br.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ufcg.edu.br.Model.Tarefas;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefas, Integer>{
}
