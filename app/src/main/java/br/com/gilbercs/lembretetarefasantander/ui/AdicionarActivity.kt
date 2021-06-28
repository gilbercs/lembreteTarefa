package br.com.gilbercs.lembretetarefasantander.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.gilbercs.lembretetarefasantander.databinding.ActivityAdicionarBinding
import br.com.gilbercs.lembretetarefasantander.datasource.DataSourceTarefa
import br.com.gilbercs.lembretetarefasantander.extensions.format
import br.com.gilbercs.lembretetarefasantander.extensions.text
import br.com.gilbercs.lembretetarefasantander.model.ModelTarefa
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AdicionarActivity : AppCompatActivity(){
    private lateinit var binding: ActivityAdicionarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       binding = ActivityAdicionarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(TAREFA_ID)) {
            val tarefaId = intent.getIntExtra(TAREFA_ID, 0)
            DataSourceTarefa.findById(tarefaId)?.let {
                binding.idTitulo.text = it.title
                binding.idData.text = it.date
                binding.idHora.text = it.hour
            }
        }
        //chamar metodo
        insertListeners()

    }
    private fun insertListeners() {
        //Abrir alert dialog calendario
        binding.idData.editText?.setOnClickListener {
            val datePicker=MaterialDatePicker.Builder.datePicker().build()
            //adicionar data
            datePicker.addOnPositiveButtonClickListener {
                //time zone da maquina / padrão
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                //utilizar java util / chamar extension/ set data
                binding.idData.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager,"Date Picker")

        }//data
        //hora
        binding.idHora.editText?.setOnClickListener {
            val horaPicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            horaPicker.addOnPositiveButtonClickListener {
                val minuto = if (horaPicker.minute in 0..9) "0${horaPicker.minute}" else horaPicker.minute
                val hora = if (horaPicker.hour in 0..9) "0${horaPicker.hour}" else horaPicker.hour
                binding.idHora.text = "$hora : $minuto"
            }
            horaPicker.show(supportFragmentManager, null)
        }// Fim hora
        //cancelar
        binding.btnCancel.setOnClickListener {
            finish()
        }
        //adicionar
        binding.btnAdd.setOnClickListener {
            val modelTarefa = ModelTarefa(
                title = binding.idTitulo.text,
                date = binding.idData.text,
                hour = binding.idHora.text,
                id = intent.getIntExtra(TAREFA_ID,0)
            )
            DataSourceTarefa.insertTarefa(modelTarefa)
            //teste lista
            //Log.e("Tag","adicionar"+ DataSourceTarefa.getList())
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
    companion object {
        const val TAREFA_ID = "tarefa_id"
    }
}
