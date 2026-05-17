package com.example.musicstreamingapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Entity(
    val id: Int,
    val title: String,
    val subtitle: String,
    val summaryFields: Map<String, String>,
    val allFields: Map<String, String>,
    val description: String?,
) : Parcelable {
    val summaryLines: List<String>
        get() = summaryFields.entries.map { (key, value) ->
            "${formatLabel(key)}: $value"
        }
}

fun formatLabel(key: String): String {
    return key
        .replace(Regex("([a-z])([A-Z])"), "$1 $2")
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}
