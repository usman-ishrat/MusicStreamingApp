package com.example.musicstreamingapp.presentation.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicstreamingapp.databinding.ItemEntityCardBinding
import com.example.musicstreamingapp.domain.model.Entity

class EntityAdapter(
    private val onItemClick: (Entity) -> Unit,
) : ListAdapter<Entity, EntityAdapter.EntityViewHolder>(EntityDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val binding = ItemEntityCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return EntityViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class EntityViewHolder(
        private val binding: ItemEntityCardBinding,
        private val onItemClick: (Entity) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: Entity) {
            binding.entityTitle.text = entity.title
            binding.entitySubtitle.text = entity.subtitle
            binding.root.setOnClickListener { onItemClick(entity) }
        }
    }

    private class EntityDiffCallback : DiffUtil.ItemCallback<Entity>() {
        override fun areItemsTheSame(oldItem: Entity, newItem: Entity): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Entity, newItem: Entity): Boolean =
            oldItem == newItem
    }
}
