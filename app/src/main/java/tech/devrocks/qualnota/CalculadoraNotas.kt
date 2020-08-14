package tech.devrocks.qualnota


class CalculadoraNotas {
  val MEDIA_MINIMA_APROVACAO = 7.0
  var notaProvaEspecificaInformada = false

  private fun calcularNotaContextualizada(): Double {
    return 10.0
  }

  private fun calcularMedia(
    notaEficiencia: Double,
    notaProvaContextualizada: Double,
    notaProvaEspecifica: Double
  ): Double {
    return 0.5 * ((notaEficiencia + notaProvaContextualizada) / 2) + 0.5 * notaProvaEspecifica
  }

  private fun calcularMediaFinal(mediaNotas: Double, notaExame: Double): Double {
    return (mediaNotas + notaExame) / 2
  }

  fun calcularNotaExameEspecial(mediaNotas: Double): Double {
    return 2 * 5 - mediaNotas
  }

  fun calcularNotaProvaEspecifica(
    notaEficiencia: Double,
    notaProvaContextualizada: Double
  ): Double {
    return (MEDIA_MINIMA_APROVACAO - 0.5 * (notaEficiencia + notaProvaContextualizada) / 2) / 0.5
  }

  private fun calcularNotaEficiencia(): Double {
    return 10.0
  }

  fun obterResultado(notaFinal: Double): String {
    return if (notaFinal >= 6.5) {
      "APROVADO"
    } else {
      if (notaFinal >= 4) {
        "EXAME"
      } else {
        "REPROVADO"
      }
    }
  }

  fun calcularNotas(
    notaProvaContextualizada: Double,
    notaProvaEspecifica: Double,
    notaEficiencia: Double
  ): Double {

    var notaProvaContextualizadaCalculada = notaProvaContextualizada
    var notaProvaEspecificaCalculada = notaProvaEspecifica
    var notaEficienciaCalculada = notaEficiencia


    if (notaProvaContextualizada < 0) {
      if (notaProvaEspecifica >= 0 && notaEficiencia >= 0) {
        notaProvaEspecificaCalculada = calcularNotaContextualizada()
      }
    }

    if (notaEficiencia < 0) {
      if (notaProvaContextualizada >= 0 && notaProvaEspecifica >= 0) {
        notaEficienciaCalculada = calcularNotaEficiencia()
      }
    }

    if (notaProvaEspecifica < 0) {
      if (notaProvaContextualizada >= 0 && notaEficiencia >= 0) {
        notaProvaEspecificaCalculada =
          calcularNotaProvaEspecifica(notaEficiencia, notaProvaContextualizada)
      }
    }


    return calcularMedia(
      notaEficienciaCalculada,
      notaProvaContextualizadaCalculada,
      notaProvaEspecificaCalculada
    )


  }
}