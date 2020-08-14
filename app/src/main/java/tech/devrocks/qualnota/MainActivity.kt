package tech.devrocks.qualnota

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_calculo.*
import kotlinx.android.synthetic.main.dialog_resultado.*
import kotlinx.android.synthetic.main.dialog_resultado.btn_salvar_resultado
import kotlinx.android.synthetic.main.dialog_salvar_calculo.*
import kotlinx.android.synthetic.main.dialog_sobre.*
import kotlinx.android.synthetic.main.item_calculo.*

class MainActivity : AppCompatActivity() {
  private lateinit var mInterstitialAd: InterstitialAd
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    btn_iniciar.setOnClickListener {
      startActivity(Intent(this, CalculosSalvos::class.java))
    }

    btn_sobre.setOnClickListener {
      exibirInformacoesSobreApp()
    }

  }

  private fun exibirInformacoesSobreApp() {
    val dialogInformacoes = DialogFactory(this).dialogInformacoesApp
    with(dialogInformacoes) {
      this.show()

      this.btn_compartilhar_app.setOnClickListener {
        compartilharApp()
      }

      this.btn_avaliar_app.setOnClickListener {
        avaliarApp()
      }

      this.btn_fechar.setOnClickListener {
        this.dismiss()
      }

    }
  }

  private fun compartilharApp() {
    val sendIntent: Intent = Intent().apply {
      action = Intent.ACTION_SEND
      putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.texto_compartilhamento_app))
      type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
  }

  private fun avaliarApp() {
    val intent = Intent(Intent.ACTION_VIEW).apply {
      data = Uri.parse(
        "https://play.google.com/store/apps/details?id=" + packageName
      )
      setPackage("com.android.vending")
    }
    startActivity(intent)
  }




}

