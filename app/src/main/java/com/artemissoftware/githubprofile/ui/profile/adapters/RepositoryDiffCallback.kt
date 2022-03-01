package com.artemissoftware.githubprofile.ui.profile.adapters

import androidx.recyclerview.widget.DiffUtil
import com.artemissoftware.domain.models.Repository

class RepositoryDiffCallback : DiffUtil.ItemCallback<Repository>() {

    override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem == newItem
    }
}