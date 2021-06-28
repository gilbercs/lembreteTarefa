package br.com.gilbercs.lembretetarefasantander

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.com.gilbercs.lembretetarefasantander.databinding.ActivityMainBinding
import br.com.gilbercs.lembretetarefasantander.datasource.DataSourceTarefa
import br.com.gilbercs.lembretetarefasantander.ui.AdapterTarefa
import br.com.gilbercs.lembretetarefasantander.ui.AdicionarActivity

class MainActivity : AppCompatActivity() {
    //variavel global
    private lateinit var binding: ActivityMainBinding
    private val adapterTarefa by lazy { AdapterTarefa()}
    //oncreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.idItemTarefa.adapter = adapterTarefa
        adapterUpdateLista()
        //chamado activity criar tarefa
        adicionarTarefa()
    }
    private fun adicionarTarefa(){
       binding.floatingActionButton.setOnClickListener {
          // startActivity(Intent(this, AdicionarActivity::class.java))
           startActivityForResult(Intent(this, AdicionarActivity::class.java), CRIA_NOVA_TAREFA)
       }
        adapterTarefa.listenerEditar ={
            val intent = Intent(this, AdicionarActivity::class.java)
            intent.putExtra(AdicionarActivity.TAREFA_ID, it.id)
            startActivityForResult(intent, CRIA_NOVA_TAREFA)
        }
        adapterTarefa.listenerDeletar={
            DataSourceTarefa.deleteTarefa(it)
            adapterUpdateLista()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CRIA_NOVA_TAREFA && resultCode == RESULT_OK){
            //implentar recyclear view
            binding.idItemTarefa.adapter = adapterTarefa
            //adapterTarefa.submitList(DataSourceTarefa.getList())
            adapterUpdateLista()
        }

    }

    private fun adapterUpdateLista() {
        val list = DataSourceTarefa.getList()
        binding.idStateBranco.idEmptyState.visibility = if (list.isEmpty()) View.VISIBLE
        else View.GONE
        adapterTarefa.submitList(list)
    }

    companion object {
        private const val CRIA_NOVA_TAREFA = 100
    }
}