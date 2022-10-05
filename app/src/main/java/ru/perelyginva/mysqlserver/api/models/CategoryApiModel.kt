package ru.perelyginva.mysqlserver.api.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CategoryApiModel(
    @SerializedName("id") @Expose
    var id: Int? = null,

    @SerializedName("name") @Expose
    var name: String? = null
)
