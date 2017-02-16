angular.module("listaDeTarefas").controller("listaDeTarefasCtrl", function ($scope,$http){

    $scope.app = "Lista de Tarefas";

    $scope.listaMomento = [{nome:"Lista de Tarefas:", tarefas:[]}];

    $scope.tarefaMomento = [{nome:"Tarefa:", subTarefas:[]}]

    $scope.tarefas = [];

    $scope.listaTarefas = [];

    $scope.listaSelecionada = [];

    $scope.categorias =[];

    $scope.tarefaAparente =[];

    $scope.prioridades = ["ALTA", "MEDIA", "BAIXA"];


    $scope.mudaListaTarefas = function(listaDeTarefas){
        $scope.listaMomento = listaDeTarefas;
    }

    $scope.adicionarTarefa = function (tarefa){
        $scope.tarefas.push(angular.copy(tarefa));
        delete $scope.tarefa;
    };

    $scope.classe = "selecionado";

    $scope.retornaTarefas = function (){
        return $scope.listaMomento.tarefas.length;
    }

    $scope.tarefasRealizadas = function () {
        var tarefasRealizadas = 0;
        for(var i = 0; i < $scope.listaMomento.tarefas.length; i++){
            console.log($scope.listaMomento.tarefas[i].feito);
            if($scope.listaMomento.tarefas[i].feito) {
                console.log(tarefasRealizadas);
                tarefasRealizadas++;
            }
        }
        console.log(tarefasRealizadas);
        return tarefasRealizadas;
    };

    $scope.barraDePorcentagem = function (){
        var percentual = ($scope.tarefasRealizadas() * 100) / ($scope.listaMomento.tarefas.length);
        return parseInt(percentual);
    };

    $scope.addNaLista = function(listaDeTarefas) {
        $http({method:'POST', url:'http://localhost:8080/listas', data: listaDeTarefas})
            .then(function(response){

                $scope.listaSelecionada = response.data;
                $scope.listaTarefas.push(response.data);
                delete $scope.listaDeTarefas;

                console.log(response.status);
                console.log(response.data);

            }, function(response){
                console.log(response.data);
                console.log(response.status);
            })
    }

    $scope.ExcluirTodasAsListas = function() {
        $http({method:'DELETE', url:'http://localhost:8080/listas'})
            .then(function(response){

                $scope.listaTarefas = [];
                $scope.listaSelecionada = {nome:"Lista de Tarefas", tarefas:[]};

                console.log(response.status);

            }, function(response){
                console.log(response.data);
                console.log(response.status);
            })
    }

    $http({method:'GET', url:'http://localhost:8080/listas'})
        .then(function(response){

            $scope.listaTarefas = response.data;

        }, function(response){
            console.log(response.data);
            console.log(response.status);
        });

    $scope.excluirListaTarefas = function(listaDeTarefas) {
        $http({method:'DELETE', url:'http://localhost:8080/listas/' + listaDeTarefas.id})
            .then(function(response){

                var index = $scope.listaTarefas.indexOf(listaDeTarefas);

                $scope.listaTarefas.splice(index, 1);
                $scope.listaDeTarefasSelecionada = {nome:"Agenda de Tarefas", tarefas:[]};

                console.log(response.status);

            }, function(response){
                console.log(response.data);
                console.log(response.status);
            })
    }

    $scope.addTarefa = function(tarefa) {
        $http({method:'POST', url:'http://localhost:8080/listas/' + $scope.listaMomento.id, data:tarefa})
            .then(function(response){

                $scope.listaMomento.tarefas.push(response.data);

                if ($scope.categorias.indexOf(response.data.categoria) == -1 && response.data.categoria !== null) {
                    $scope.categorias.push(response.data.categoria);
                } else if (response.data.categoria === null) {
                    response.data.categoria = '';
                }

            }, function(response){
                console.log(response.data);
                console.log(response.status);
            })
    }

    $scope.limparTarefas = function () {

        $scope.listaMomento.tarefas = [];
        $scope.excluirTodasTarefas($scope.listaMomento);
    }

    $scope.excluirTodasTarefas = function(listaTarefas) {
        $http({method:'DELETE', url:'http://localhost:8080/listas/tarefas/' + listaTarefas.id})
            .then(function(response){

                console.log(response.status);

            }, function(response){
                console.log(response.data);
                console.log(response.status);
            })
    }

    $scope.excluirTarefa = function(tarefa) {
        $http({method:'DELETE', url:'http://localhost:8080/listas/' + $scope.listaMomento.id + "/" + tarefa.id})
            .then(function(response){

                console.log(response.status);

            }, function(response){
                console.log(response.data);
                console.log(response.status);
            })
    }

    $scope.mudaTarefa = function(tarefa) {
        $http({method:'PUT', url:'http://localhost:8080/listas/tarefa', data:tarefa})
            .then(function(response){
                console.log(response.status);
            }, function(response){
                console.log(response.data);
                console.log(response.status);
            })
    }

    $scope.contemLista = function (){
        return $scope.listaTarefas.length > 0;
    }

    $scope.marcarConcluida = function (tarefa) {

        if (tarefa.feito) {
            tarefa.feito = false;
        } else {
            tarefa.feito = true;
        }
        $scope.mudaTarefa(tarefa);
    }

    $scope.adicionarSubTarefa = function(subtarefa) {

        subtarefa.concluida = false;

        $http({method:'POST', url:'http://localhost:8080/listas/tarefa/' + tarefaMomento.id, data: subtarefa})
            .then(function(response){

                tarefaMomento.subTarefas.push(response.data);
                delete $scope.subtarefa;

            }, function(response){
                console.log(response.data);
                console.log(response.status);
            })
    }

    var download = document.getElementById('baixarLista');

    download.onclick = function () {
        var docDefinition = { content: criaStringPdf() };
        pdfMake.createPdf(docDefinition).download($scope.listaMomento.nome + '.pdf');
    }

    function criaStringPdf() {
        var saida = "Lista de Tarefas: " + $scope.listaMomento.nome + "\n"
            + "Tarefas:\n";
        for (var i = 0; i < $scope.listaMomento.tarefas.length; i++) {
            var tarefa = $scope.listaMomento.tarefas[i];
            saida += "    Nome: " + tarefa.nome + "  /  Descrição: " + tarefa.descricao + "  /  Prioridade: " + tarefa.prioridade + "\n"
        }
        return saida;
    }
});
