package com.artemissoftware.githubprofile.ui.profile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artemissoftware.domain.models.Repository
import com.artemissoftware.domain.models.UserProfile
import com.artemissoftware.githubprofile.databinding.ItemRepositoryBinding

class RepositoryListAdapter (
    ) : ListAdapter<Repository, RepositoryListAdapter.RepositoryViewHolder>(RepositoryDiffCallback()) {

    lateinit var userProfile: UserProfile

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {

        val bind = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryViewHolder(bind)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RepositoryViewHolder(private val binding: ItemRepositoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Repository) {
            with(binding) {
                user = userProfile
                repository = item
                executePendingBindings()
            }
        }
    }

}