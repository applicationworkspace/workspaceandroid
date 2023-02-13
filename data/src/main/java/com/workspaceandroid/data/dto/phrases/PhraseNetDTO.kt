package com.workspaceandroid.data.dto.phrases

import com.google.gson.annotations.SerializedName

data class PhraseNetDTO(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("phraseText")
    val phraseText: String?,
    @SerializedName("phraseId")
    val phraseId: Int?,
    @SerializedName("phraseImgUrl")
    val phraseImgUrl: String?,
    @SerializedName("phraseExamples")
    val phraseExamples: List<String>?,
    @SerializedName("phraseDefinition")
    val phraseDefinition: String?,
    @SerializedName("phraseUserId")
    val phraseUserId: Int?
)