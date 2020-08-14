package tech.devrocks.qualnota

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatarData(format: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"): String? {
  val formatador = SimpleDateFormat(format)
  return formatador.format(this)
}