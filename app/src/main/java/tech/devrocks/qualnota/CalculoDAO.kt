package tech.devrocks.qualnota

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import java.util.*
import tech.devrocks.qualnota.Contrato.Resultado as contratoDB

class CalculoDAO(context: Context?) {
  private var dbHelper: DatabaseHelper = DatabaseHelper(context!!)


  fun inserir(calculo: Calculo): Boolean {
    val valores = ContentValues()
    val database = dbHelper!!.writableDatabase

    valores.put(contratoDB.DESCRICAO, calculo.nome)
    valores.put(contratoDB.NOTA_EFICIENCIA, calculo.notaProvaEficiencia)
    valores.put(contratoDB.NOTA_CONTEXTUALIZADA, calculo.notaProvaContextualziada)
    valores.put(contratoDB.NOTA_ESPECIFICA, calculo.notaProvaEspecifica)
    valores.put(contratoDB.NOTA_MEDIA, calculo.notaMedia)
    valores.put(contratoDB.NOTA_EXAME, calculo.notaExameEspecial)
    valores.put(contratoDB.RESULTADO, calculo.resultado)
    valores.put(contratoDB.NOTA_PROVA_ESPECIFICA_INFORMADA, calculo.notaProvaEspecificaInformada)
    valores.put(
      contratoDB.DATA_CALCULO,
      calculo.data.formatarData("yyyy-MM-dd hh:mm:ss")
    )

    val resultado = database.insert(contratoDB.TABELA, null, valores)
    database.close()

    return resultado > -1

  }

  fun atualizar(calculo: Calculo): Boolean {

    val valores = ContentValues()
    val database = dbHelper!!.writableDatabase
    valores.put(contratoDB.DESCRICAO, calculo.nome)
    valores.put(contratoDB.NOTA_EFICIENCIA, calculo.notaProvaEficiencia)
    valores.put(contratoDB.NOTA_CONTEXTUALIZADA, calculo.notaProvaContextualziada)
    valores.put(contratoDB.NOTA_ESPECIFICA, calculo.notaProvaEspecifica)
    valores.put(contratoDB.NOTA_MEDIA, calculo.notaMedia)
    valores.put(contratoDB.NOTA_EXAME, calculo.notaExameEspecial)
    valores.put(contratoDB.RESULTADO, calculo.resultado)
    valores.put(contratoDB.NOTA_PROVA_ESPECIFICA_INFORMADA, calculo.notaProvaEspecificaInformada)
    valores.put(
      contratoDB.DATA_CALCULO,
      calculo.data.formatarData("yyyy-MM-dd hh:mm:ss")
    )

    val resultado =
      database.update(
        contratoDB.TABELA,
        valores,
        contratoDB.ID.toString() + "=" + calculo.id,
        null
      )
    database.close()
    return resultado > -1

  }

  fun getPorCodigo(): Calculo? {
    val database = dbHelper!!.readableDatabase
    val calculo = Calculo()
    val cursor: Cursor
    val campos = arrayOf<String>(
      contratoDB.ID,
      contratoDB.DESCRICAO,
      contratoDB.NOTA_EFICIENCIA,
      contratoDB.NOTA_CONTEXTUALIZADA,
      contratoDB.NOTA_ESPECIFICA,
      contratoDB.NOTA_MEDIA,
      contratoDB.NOTA_EXAME,
      contratoDB.RESULTADO,
      contratoDB.DATA_CALCULO
    )

    cursor = database.query(
      contratoDB.TABELA,
      campos,
      null,
      null,
      null,
      null,
      contratoDB.DATA_CALCULO + " DESC"
    )
    calculo.id = cursor.getInt(cursor.getColumnIndexOrThrow(contratoDB.ID))
    calculo.nome = cursor.getString(cursor.getColumnIndexOrThrow(contratoDB.DESCRICAO))
    calculo.notaProvaEficiencia =
      cursor.getDouble(cursor.getColumnIndexOrThrow(contratoDB.NOTA_EFICIENCIA))
    calculo.notaProvaContextualziada =
      cursor.getDouble(cursor.getColumnIndexOrThrow(contratoDB.NOTA_CONTEXTUALIZADA))
    calculo.notaProvaEspecifica =
      cursor.getDouble(cursor.getColumnIndexOrThrow(contratoDB.NOTA_ESPECIFICA))
    calculo.notaMedia = cursor.getDouble(cursor.getColumnIndexOrThrow(contratoDB.NOTA_MEDIA))
    calculo.notaExameEspecial =
      cursor.getDouble(cursor.getColumnIndexOrThrow(contratoDB.NOTA_EXAME))
    calculo.resultado = cursor.getString(cursor.getColumnIndexOrThrow(contratoDB.RESULTADO))

    calculo.data = cursor.getString(cursor.getColumnIndexOrThrow(contratoDB.DATA_CALCULO))
      .converterParaData("yyyy-MM-dd hh:mm:ss")


    database.close()
    return calculo
  }


  fun listarTodos(): ArrayList<Calculo>? {
    val database = dbHelper!!.readableDatabase
    val calculos: ArrayList<Calculo> = ArrayList<Calculo>()
    val cursor: Cursor
    val campos = arrayOf<String>(
      contratoDB.ID,
      contratoDB.DESCRICAO,
      contratoDB.NOTA_EFICIENCIA,
      contratoDB.NOTA_CONTEXTUALIZADA,
      contratoDB.NOTA_ESPECIFICA,
      contratoDB.NOTA_MEDIA,
      contratoDB.NOTA_EXAME,
      contratoDB.RESULTADO,
      contratoDB.DATA_CALCULO,
      contratoDB.NOTA_PROVA_ESPECIFICA_INFORMADA
    )

    cursor = database.query(
      contratoDB.TABELA,
      campos,
      null,
      null,
      null,
      null,
      contratoDB.DATA_CALCULO + " DESC"
    )
    while (cursor.moveToNext()) {
      val calculo = Calculo()
      calculo.id = cursor.getInt(cursor.getColumnIndexOrThrow(contratoDB.ID))
      calculo.nome = cursor.getString(cursor.getColumnIndexOrThrow(contratoDB.DESCRICAO))
      calculo.notaProvaEficiencia =
        cursor.getDouble(cursor.getColumnIndexOrThrow(contratoDB.NOTA_EFICIENCIA))
      calculo.notaProvaContextualziada =
        cursor.getDouble(cursor.getColumnIndexOrThrow(contratoDB.NOTA_CONTEXTUALIZADA))
      calculo.notaProvaEspecifica =
        cursor.getDouble(cursor.getColumnIndexOrThrow(contratoDB.NOTA_ESPECIFICA))
      calculo.notaMedia = cursor.getDouble(cursor.getColumnIndexOrThrow(contratoDB.NOTA_MEDIA))
      calculo.notaExameEspecial =
        cursor.getDouble(cursor.getColumnIndexOrThrow(contratoDB.NOTA_EXAME))
      calculo.resultado = cursor.getString(cursor.getColumnIndexOrThrow(contratoDB.RESULTADO))

      calculo.data = cursor.getString(cursor.getColumnIndexOrThrow(contratoDB.DATA_CALCULO))
        .converterParaData("yyyy-MM-dd hh:mm:ss")


      calculos.add(calculo)
    }
    database.close()
    return calculos
  }

  fun deletar(id: Int): Boolean {
    val database = dbHelper!!.writableDatabase
    val ret =
      database.delete(contratoDB.TABELA, contratoDB.ID + "=" + id, null) > 0
    database.close()
    return ret
  }

  fun deletarTodos() {
    val database = dbHelper!!.writableDatabase
    database.delete(contratoDB.TABELA, "", null)
    database.close()
  }

  fun getCalculoPorCodigo(codigo: Int): Calculo? {
    val database = dbHelper!!.readableDatabase
    val calculo = Calculo()
    val cursor: Cursor
    val campos = arrayOf<String>(
      contratoDB.ID,
      contratoDB.DESCRICAO,
      contratoDB.NOTA_EFICIENCIA,
      contratoDB.NOTA_CONTEXTUALIZADA,
      contratoDB.NOTA_ESPECIFICA,
      contratoDB.NOTA_MEDIA,
      contratoDB.NOTA_EXAME,
      contratoDB.RESULTADO,
      contratoDB.DATA_CALCULO
    )

    cursor = database.query(
      contratoDB.TABELA,
      campos,
      contratoDB.ID + " = " + codigo,
      null,
      null,
      null,
      contratoDB.DATA_CALCULO + " DESC"
    )
    cursor.moveToFirst()
    calculo.id = cursor.getInt(cursor.getColumnIndexOrThrow(contratoDB.ID))
    calculo.nome = cursor.getString(cursor.getColumnIndexOrThrow(contratoDB.DESCRICAO))
    calculo.notaProvaEficiencia =
      cursor.getDouble(cursor.getColumnIndexOrThrow(contratoDB.NOTA_EFICIENCIA))
    calculo.notaProvaContextualziada =
      cursor.getDouble(cursor.getColumnIndexOrThrow(contratoDB.NOTA_CONTEXTUALIZADA))
    calculo.notaProvaEspecifica =
      cursor.getDouble(cursor.getColumnIndexOrThrow(contratoDB.NOTA_ESPECIFICA))
    calculo.notaMedia = cursor.getDouble(cursor.getColumnIndexOrThrow(contratoDB.NOTA_MEDIA))
    calculo.notaExameEspecial =
      cursor.getDouble(cursor.getColumnIndexOrThrow(contratoDB.NOTA_EXAME))
    calculo.resultado = cursor.getString(cursor.getColumnIndexOrThrow(contratoDB.RESULTADO))

    calculo.data =
      cursor.getString(cursor.getColumnIndexOrThrow(contratoDB.DATA_CALCULO))
        .converterParaData("yyyy-MM-dd hh:mm:ss")


    return calculo
  }

}