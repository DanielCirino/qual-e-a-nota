package tech.devrocks.qualnota

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class Calculo() : Parcelable {
  var id = 0
  var nome = ""
  var notaProvaEficiencia = 0.0
  var notaProvaContextualziada = 0.0
  var notaProvaEspecifica = 0.0
  var notaMedia = 0.0
  var notaExameEspecial = 0.0
  var resultado = ""
  var data = Date()
  var notaProvaEspecificaInformada = false

  constructor(parcel: Parcel) : this() {
    id = parcel.readInt()
    nome = parcel.readString()!!
    notaProvaEficiencia = parcel.readDouble()
    notaProvaContextualziada = parcel.readDouble()
    notaProvaEspecifica = parcel.readDouble()
    notaMedia = parcel.readDouble()
    notaExameEspecial = parcel.readDouble()
    resultado = parcel.readString()!!
    data = parcel.readDate()!!
    notaProvaEspecificaInformada = parcel.readBoolean()!!
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeInt(id)
    parcel.writeString(nome)
    parcel.writeDouble(notaProvaEficiencia)
    parcel.writeDouble(notaProvaContextualziada)
    parcel.writeDouble(notaProvaEspecifica)
    parcel.writeDouble(notaMedia)
    parcel.writeDouble(notaExameEspecial)
    parcel.writeString(resultado)
    parcel.writeDate(data)
    parcel.writeBoolean(notaProvaEspecificaInformada)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<Calculo> {
    override fun createFromParcel(parcel: Parcel): Calculo {
      return Calculo(parcel)
    }

    override fun newArray(size: Int): Array<Calculo?> {
      return arrayOfNulls(size)
    }
  }

}