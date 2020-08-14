package tech.devrocks.qualnota

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import tech.devrocks.qualnota.R

import java.util.*

class DialogFactory(private var contexto: Context) {

  val dialogPergunta: Dialog
    @SuppressLint("InflateParams")
    get() {
      val viewDialog = LayoutInflater.from(contexto).inflate(R.layout.dialog_pergunta, null)
      val dialogBuilder = AlertDialog.Builder(contexto)
        .setView(viewDialog)


      return dialogBuilder.create()
    }

    val dialogCalculo: Dialog
    @SuppressLint("InflateParams")
    get() {
      val viewDialog = LayoutInflater.from(contexto).inflate(R.layout.dialog_calculo, null)
      val dialogBuilder = AlertDialog.Builder(contexto)
        .setView(viewDialog)


      return dialogBuilder.create()
    }
  val dialogResultado: Dialog
    @SuppressLint("InflateParams")
    get() {
      val viewDialog = LayoutInflater.from(contexto).inflate(R.layout.dialog_resultado, null)
      val dialogBuilder = AlertDialog.Builder(contexto)
        .setView(viewDialog)


      return dialogBuilder.create()
    }

  val dialogInformacoesApp: Dialog
    @SuppressLint("InflateParams")
    get() {
      val viewDialog = LayoutInflater.from(contexto).inflate(R.layout.dialog_sobre, null)
      val dialogBuilder = AlertDialog.Builder(contexto)
        .setView(viewDialog)


      return dialogBuilder.create()
    }

  val dialogSalvarCalculo: Dialog
    @SuppressLint("InflateParams")
    get() {
      val viewDialog = LayoutInflater.from(contexto).inflate(R.layout.dialog_salvar_calculo, null)
      val dialogBuilder = AlertDialog.Builder(contexto)
        .setView(viewDialog)


      return dialogBuilder.create()
    }

}