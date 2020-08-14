package tech.devrocks.qualnota

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log

object Contrato {
  // Table contents are grouped together in an anonymous object.
  object Resultado : BaseColumns {
    const val NOME_BANCO = "qual_e_a_nota.db"
    const val TABELA = "calculos"

    const val ID = "id"
    const val DESCRICAO = "descricao"
    const val NOTA_EFICIENCIA = "nota_prova_eficiencia"
    const val NOTA_CONTEXTUALIZADA = "nota_prova_contextualizada"
    const val NOTA_ESPECIFICA = "nota_prova_especifica"
    const val NOTA_MEDIA = "nota_media"
    const val NOTA_EXAME = "nota_exame_especial"
    const val RESULTADO = "resultado"
    const val DATA_CALCULO = "data_hora"
    const val NOTA_PROVA_ESPECIFICA_INFORMADA = "nota_prova_especifica_informada"


    const val VERSAO = 6
  }
}


class DatabaseHelper(context: Context) : SQLiteOpenHelper(
  context,
  Contrato.Resultado.NOME_BANCO,
  null,
  Contrato.Resultado.VERSAO
) {


  override fun onCreate(db: SQLiteDatabase) {
    val sql = ("CREATE TABLE " + Contrato.Resultado.TABELA + "("
        + Contrato.Resultado.ID + " integer primary key autoincrement,"
        + Contrato.Resultado.DESCRICAO + " text,"
        + Contrato.Resultado.NOTA_EFICIENCIA + " real,"
        + Contrato.Resultado.NOTA_CONTEXTUALIZADA + " real,"
        + Contrato.Resultado.NOTA_ESPECIFICA + " real,"
        + Contrato.Resultado.NOTA_PROVA_ESPECIFICA_INFORMADA + " bool,"
        + Contrato.Resultado.NOTA_MEDIA + " real,"
        + Contrato.Resultado.NOTA_EXAME + " real,"
        + Contrato.Resultado.RESULTADO + " real,"
        + Contrato.Resultado.DATA_CALCULO + " text)")
    try {
      db.execSQL(sql)
    } catch (e: Exception) {
      Log.i("ERRO", "Erro ao criar banco de dados")
    }
  }

  override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    db.execSQL("DROP TABLE IF EXISTS ${Contrato.Resultado.TABELA}")
    onCreate(db)
  }
}