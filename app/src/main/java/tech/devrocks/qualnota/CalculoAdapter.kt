package tech.devrocks.qualnota

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.item_calculo.view.*

class CalculoAdapter(private val myDataset: ArrayList<Calculo>?) :
  RecyclerView.Adapter<CalculoAdapter.CalculoViewHolder>() {

  class CalculoViewHolder(val calculoView: View) : RecyclerView.ViewHolder(calculoView) {
    private val tvNomeCalculo = calculoView.tv_nome_calculo
    private val tvDataCalculo = calculoView.tv_data_calculo
    private val tvNota = calculoView.tv_valor_nota_media
    private val btnExcluirCalculo = calculoView.btn_excluir_calculo
    private val context = itemView.context


    fun configurarView(calculo: Calculo) {

      tvNota.text = "%.2f".format(calculo.notaMedia)
      tvNomeCalculo.text = calculo.nome
      tvDataCalculo.text = calculo.data.formatarData("dd/MM/yyyy")

      btnExcluirCalculo.setOnClickListener {
        CalculoDAO(context).deletar(calculo.id)
        itemView.visibility = View.GONE
      }

      calculoView.setOnClickListener {
        Snackbar.make(it,calculo.nome,Snackbar.LENGTH_LONG).show()
      }
    }
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): CalculoAdapter.CalculoViewHolder {
    // create a new view
    val calculoView = LayoutInflater.from(parent.context)
      .inflate(R.layout.item_calculo, parent, false)
    // set the view's size, margins, paddings and layout parameters

    return CalculoViewHolder(calculoView)
  }

  override fun onBindViewHolder(holder: CalculoViewHolder, position: Int) {
    holder.configurarView(myDataset!!.get(position))
  }

  override fun getItemCount(): Int = myDataset?.size!!
}
