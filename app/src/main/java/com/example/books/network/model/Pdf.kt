package com.example.books

import com.google.gson.annotations.SerializedName


data class Pdf (

  @SerializedName("isAvailable" ) var isAvailable : Boolean? = null

)