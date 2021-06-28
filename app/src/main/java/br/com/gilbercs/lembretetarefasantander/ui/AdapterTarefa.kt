package br.com.gilbercs.lembretetarefasantander.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.gilbercs.lembretetarefasantander.R
import br.com.gilbercs.lembretetarefasantander.databinding.ItensTarefaBinding
import br.com.gilbercs.lembretetarefasantander.model.ModelTarefa

class AdapterTarefa: ListAdapter<ModelTarefa, AdapterTarefa.TarefaViewHolder>(DiffCallback()) {
    //menu
    var listenerEditar : (ModelTarefa) -> Unit = {}
    var listenerDeletar : (ModelTarefa) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItensTarefaBinding.inflate(inflater, parent,false)
        return TarefaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    inner  class TarefaViewHolder(private val idTarefa: ItensTarefaBinding): RecyclerView.ViewHolder(idTarefa.root){
        fun bind(item: ModelTarefa) {
            idTarefa.idAdpTituloTarefa.text = item.title
            idTarefa.idAdpDataHora.text = "${item.date} ${item.hour}"
            idTarefa.idAdpImage.setOnClickListener {
                showPoup(item)
            }
        }
        private fun showPoup(item: ModelTarefa) {
            val img = idTarefa.idAdpImage
            val popupMenu = PopupMenu(img.context, img)
            popupMenu.menuInflater.inflate(R.menu.menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.idMenuEdit -> listenerEditar(item)
                    R.id.idMenuDelete -> listenerDeletar(item)
                    }
                return@setOnMenuItemClickListener  true
                }
            popupMenu.show()
        }

    }
}
class DiffCallback : DiffUtil.ItemCallback<ModelTarefa>() {
    override fun areItemsTheSame(oldItem: ModelTarefa,novoItem: ModelTarefa) = oldItem == novoItem
    override fun areContentsTheSame(oldItem: ModelTarefa, novoItem: ModelTarefa) = oldItem.id == novoItem.id
}