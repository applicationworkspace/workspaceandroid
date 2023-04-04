package com.workspaceandroid.domain.models.phrase

data class PhraseInput(
    var text: String = "",
    var examples: Map<Int, String> = emptyMap(),
    var definition: String = ""
)