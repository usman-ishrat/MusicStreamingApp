package com.example.musicstreamingapp.data.mapper

import com.example.musicstreamingapp.data.remote.dto.LanguageEntityDto
import com.example.musicstreamingapp.domain.model.Entity
import java.text.NumberFormat
import java.util.Locale

object EntityMapper {

    fun toDomain(dto: LanguageEntityDto, index: Int): Entity {
        val allFields = linkedMapOf<String, String>()
        dto.name?.let { allFields["name"] = it }
        dto.family?.let { allFields["family"] = it }
        dto.branch?.let { allFields["branch"] = it }
        dto.speakers?.let { allFields["speakers"] = formatSpeakers(it) }
        dto.writingSystem?.let { allFields["writingSystem"] = it }
        dto.officialIn?.takeIf { it.isNotEmpty() }?.let {
            allFields["officialIn"] = it.joinToString(", ")
        }
        dto.description?.let { allFields["description"] = it }

        val summaryFields = allFields.filterKeys { it != "description" }
        val title = dto.name ?: summaryFields.values.firstOrNull() ?: "Item ${index + 1}"
        val subtitle = dto.family ?: summaryFields.entries
            .firstOrNull { it.key != "name" }
            ?.value
            ?: ""

        return Entity(
            id = index,
            title = title,
            subtitle = subtitle,
            summaryFields = summaryFields,
            allFields = allFields,
            description = dto.description,
        )
    }

    private fun formatSpeakers(count: Long): String {
        return NumberFormat.getNumberInstance(Locale.US).format(count)
    }
}
