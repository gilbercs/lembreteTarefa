package br.com.gilbercs.lembretetarefasantander.model

data class ModelTarefa(
    val title: String,
    val hour: String,
    val date: String,
    val id: Int = 0
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ModelTarefa

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}