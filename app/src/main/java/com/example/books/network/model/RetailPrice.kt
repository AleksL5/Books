package com.example.books

import com.google.gson.annotations.SerializedName


data class RetailPrice (

  @SerializedName("amountInMicros" ) var amountInMicros : Long?    = null,
  @SerializedName("currencyCode"   ) var currencyCode   : String? = null

)