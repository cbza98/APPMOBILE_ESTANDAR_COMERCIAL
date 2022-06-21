package com.unosoft.unoappventas.models.clientes

import com.google.gson.annotations.SerializedName

data class ClientListResponse( @SerializedName("Ruc")  val ruc: String,
@SerializedName("IdPersona") val idpersona: String,
                              @SerializedName("Nombre") val nombre: String
)