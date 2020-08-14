package tech.devrocks.qualnota

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.converterParaData(mascara: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"): Date {
  try {
    val parser = SimpleDateFormat(mascara, Locale.getDefault())
    val novaData = parser.parse(this)

    return novaData

  } catch (e: ParseException) {
    e.printStackTrace()
    return Date()
  }

}