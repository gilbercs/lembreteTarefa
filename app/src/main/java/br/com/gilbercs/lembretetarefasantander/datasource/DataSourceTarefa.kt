package br.com.gilbercs.lembretetarefasantander.datasource

import br.com.gilbercs.lembretetarefasantander.model.ModelTarefa

object DataSourceTarefa {
    private val list = arrayListOf<ModelTarefa>()

    fun getList() = list.toList()
    //popular lista
    fun insertTarefa(modelTarefa: ModelTarefa) {
        if (modelTarefa.id == 0) {
            list.add(modelTarefa.copy(id = list.size + 1))
        } else {
            list.remove(modelTarefa)
            list.add(modelTarefa)
        }
    }

    fun findById(tarefaId: Int) = list.find { it.id == tarefaId }

    fun deleteTarefa(tareFa: ModelTarefa) {
        list.remove(tareFa)
    }
}
