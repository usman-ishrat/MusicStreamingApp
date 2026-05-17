package com.example.musicstreamingapp.presentation.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.musicstreamingapp.R
import com.example.musicstreamingapp.UiTheme
import com.example.musicstreamingapp.databinding.ActivityDetailBinding
import com.example.musicstreamingapp.databinding.ViewDetailFieldRowBinding
import com.example.musicstreamingapp.domain.model.Entity
import com.example.musicstreamingapp.domain.model.formatLabel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UiTheme.applyLightStatusBar(this)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val entity = intent.getParcelableExtra(EXTRA_ENTITY, Entity::class.java)
        if (entity == null) {
            finish()
            return
        }

        binding.detailToolbar.title = entity.title
        binding.detailToolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val nonDescriptionFields = entity.allFields.filterKeys { it != "description" }
        binding.fieldsContainer.removeAllViews()
        nonDescriptionFields.forEach { (key, value) ->
            val rowBinding = ViewDetailFieldRowBinding.inflate(layoutInflater, binding.fieldsContainer, false)
            rowBinding.fieldLabel.text = formatLabel(key)
            rowBinding.fieldValue.text = value
            binding.fieldsContainer.addView(rowBinding.root)
        }

        binding.descriptionText.text = entity.description ?: getString(R.string.dashboard_empty)
    }

    companion object {
        const val EXTRA_ENTITY = "extra_entity"
    }
}
