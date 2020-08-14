package tech.devrocks.qualnota

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_calculos_salvos.*
import kotlinx.android.synthetic.main.dialog_calculo.*
import kotlinx.android.synthetic.main.dialog_resultado.*
import kotlinx.android.synthetic.main.dialog_resultado.btn_salvar_resultado
import kotlinx.android.synthetic.main.dialog_salvar_calculo.*

class CalculosSalvos : AppCompatActivity() {
  private lateinit var mInterstitialAd: InterstitialAd
  private lateinit var recyclerView: RecyclerView
  private lateinit var viewAdapter: RecyclerView.Adapter<*>
  private lateinit var viewManager: RecyclerView.LayoutManager
  private lateinit var calculoDAO: CalculoDAO

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_calculos_salvos)

    calculoDAO = CalculoDAO(this)

    configurarControles()
    configurarAdmob()
    carregarAnuncio()

  }

  fun configurarControles() {
    configurarToolbar()
    configurarRecyclerview()

    btn_lista_vazia.setOnClickListener {
      abrirFormularioCalculo()
    }
  }

  fun configurarToolbar() {
    toolbar_lista_calculos.title = "Cálculos"
    setSupportActionBar(toolbar_lista_calculos)

    val actionBar = supportActionBar

    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(false)
      actionBar.setHomeButtonEnabled(false)
      actionBar.setHomeAsUpIndicator(R.drawable.ic_voltar)

    }


  }

  private fun configurarRecyclerview() {
    val listaCalculos = calculoDAO.listarTodos()

    viewManager = LinearLayoutManager(this)
    viewAdapter = CalculoAdapter(listaCalculos)

    recyclerView = findViewById<RecyclerView>(R.id.rv_calculos_salvos).apply {
      setHasFixedSize(true)
      layoutManager = viewManager
      adapter = viewAdapter
    }

    if (listaCalculos!!.size == 0) {
      recyclerView.visibility = View.GONE
      cl_lista_vazia.visibility = View.VISIBLE
    }


  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_calculos_salvos, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item?.getItemId()) {

      R.id.acao_menu_calculo_incluir -> {
        abrirFormularioCalculo()
        return true
      }

      R.id.acao_menu_calculo_fechar -> {
        finish()
        return true
      }

      R.id.acao_menu_calculo_deletar_tudo -> {
        calculoDAO.deletarTodos()
        atualizarLista()
        return true
      }

      R.id.acao_menu_calculo_atualizar -> {
        atualizarLista()
        return true
      }

      R.id.acao_menu_calculo_atualizar -> {
        configurarRecyclerview()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  fun atualizarLista() {
    val listaCalculos = calculoDAO.listarTodos()
    recyclerView.apply {
      adapter = CalculoAdapter(listaCalculos)
    }

    if (listaCalculos!!.size == 0) {
      recyclerView.visibility = View.GONE
      cl_lista_vazia.visibility = View.VISIBLE
    } else {
      recyclerView.visibility = View.VISIBLE
      cl_lista_vazia.visibility = View.GONE
    }
  }

  private fun abrirFormularioCalculo() {

    var dialogCalculo = DialogFactory(this).dialogCalculo

    with(dialogCalculo) {
      this.show()
      this.btn_fazer_calculo.setOnClickListener {
        var notaEficiencia = -1.0
        var notaProvaContextualizada = -1.0
        var notaProvaEspecifica = -1.0

        if (!TextUtils.isEmpty(this.et_nota_eficiencia.text)) {
          notaEficiencia = et_nota_eficiencia.text.toString().toDouble()
        }

        if (!TextUtils.isEmpty(this.et_nota_prova_contextualizada.text)) {
          notaProvaContextualizada = this.et_nota_prova_contextualizada.text.toString().toDouble()
        }

        if (!TextUtils.isEmpty(this.et_nota_prova_especifica.text)) {
          notaProvaEspecifica = this.et_nota_prova_especifica.text.toString().toDouble()
        }


        var calculadora = CalculadoraNotas()

        val notaFinal =
          calculadora.calcularNotas(notaProvaContextualizada, notaProvaEspecifica, notaEficiencia)

        val resultado = calculadora.obterResultado(notaFinal)
        val notaExameEspecial = calculadora.calcularNotaExameEspecial(notaFinal)

        var resultadoCalculo = Calculo()
        resultadoCalculo.notaProvaContextualziada = notaProvaContextualizada
        resultadoCalculo.notaProvaEspecifica = notaProvaEspecifica
        resultadoCalculo.notaProvaEficiencia = notaEficiencia
        resultadoCalculo.notaMedia = notaFinal
        resultadoCalculo.resultado = resultado
        resultadoCalculo.notaExameEspecial = notaExameEspecial
        resultadoCalculo.notaProvaEspecificaInformada = notaProvaEspecifica < 0


        exibirResultado(resultadoCalculo)
      }


      this.btn_cancelar.setOnClickListener {
        this.dismiss()
      }
    }
  }

  private fun configurarAdmob() {
    MobileAds.initialize(this) {}
    mInterstitialAd = InterstitialAd(this)
    mInterstitialAd.adUnitId = "ca-app-pub-8532343123320223/1423553924"
  }

  private fun carregarAnuncio() {
    mInterstitialAd.loadAd(AdRequest.Builder().build())
    mInterstitialAd.adListener = object : AdListener() {
      override fun onAdLoaded() {

      }

      override fun onAdFailedToLoad(adError: LoadAdError) {
        // Code to be executed when an ad request fails.
      }

      override fun onAdOpened() {
        // Code to be executed when the ad is displayed.
      }

      override fun onAdClicked() {
        // Code to be executed when the user clicks on an ad.
      }

      override fun onAdLeftApplication() {
        // Code to be executed when the user has left the app.
      }

      override fun onAdClosed() {

      }
    }


  }

  private fun exibirAnuncio() {
    if (mInterstitialAd.isLoaded) {
      mInterstitialAd.show()
    } else {
      Log.i("ANUNCIO", "Não carregado...")
    }
  }

  private fun exibirResultado(resultadoCalculo: Calculo) {
    exibirAnuncio()
    var descricaoResultado = ""
    var exibirNotaExameEspecial = false
    var corTexto = R.color.colorLicoriceDark
    var valorResultado = resultadoCalculo.notaMedia

    when (resultadoCalculo.resultado) {
      "APROVADO" -> {
        descricaoResultado = "\\0/ Parabéns!! Você foi aprovado com nota:"
        corTexto = R.color.emeerald

      }
      "REPROVADO" -> {
        descricaoResultado = ":( Que pena, estudo mais na próxima!! Você foi reprovado com nota:"
        corTexto = R.color.colorAlizarin
      }
      "EXAME" -> {
        descricaoResultado = ":/ Quase lá!! Você ainda não foi reprovado com a nota:"
        corTexto = R.color.colorPrimary
        exibirNotaExameEspecial = true
      }
    }

    if (resultadoCalculo.notaProvaEspecificaInformada) {
      descricaoResultado =
        "Para ficar com média 7.0, você precisa conseguir na prova específica uma nota:"
      corTexto = R.color.amethist
      exibirNotaExameEspecial = false
      valorResultado = CalculadoraNotas().calcularNotaProvaEspecifica(
        resultadoCalculo.notaProvaEficiencia,
        resultadoCalculo.notaProvaContextualziada
      )
    }

    val dialogResulado = DialogFactory(this).dialogResultado
    with(dialogResulado) {
      this.show()
      this.tv_resultado.text = descricaoResultado
      this.tv_resultado.setTextColor(resources.getColor(corTexto))

      this.tv_valor_nota_final.text = "%.2f".format(valorResultado)
      this.tv_valor_nota_final.setTextColor(resources.getColor(corTexto))

      this.info_exame_especial.visibility = if (exibirNotaExameEspecial) View.VISIBLE else View.GONE
      this.tv_valor_nota_exame_especial.text = "%.2f".format(resultadoCalculo.notaExameEspecial)

      this.btn_salvar_resultado.setOnClickListener {
        salvarResultado(resultadoCalculo)
      }

      this.btn_fechar_resultado.setOnClickListener {
        carregarAnuncio()
        this.dismiss()
      }

    }

  }

  private fun salvarResultado(resultadoCalculo: Calculo) {
    val dialogSalvarResultado = DialogFactory(this).dialogSalvarCalculo
    val calculoDAO = CalculoDAO(this)

    with(dialogSalvarResultado) {
      this.show()
      this.btn_salvar_resultado.setOnClickListener {
        if (!TextUtils.isEmpty(this.et_nome_calculo.text)) {
          resultadoCalculo.nome = this.et_nome_calculo.text.toString()
          val resultado = calculoDAO.inserir(resultadoCalculo)

          if (!resultado) {
            Snackbar.make(
              it, // Parent view
              "Erro ao salvar cálculo.", // Message to show
              Snackbar.LENGTH_LONG // How long to display the message.
            ).show()
          } else {
            this.dismiss()
            atualizarLista()
          }
        } else {
          Snackbar.make(
            it, // Parent view
            "Informe um nome para o cálculo", // Message to show
            Snackbar.LENGTH_LONG // How long to display the message.
          ).show()
        }
      }

      this.btn_cancelar_salvamento.setOnClickListener {
        this.dismiss()
      }
    }
  }

}