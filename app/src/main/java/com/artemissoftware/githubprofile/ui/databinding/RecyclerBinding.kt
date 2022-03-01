package com.artemissoftware.githubprofile.ui.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artemissoftware.domain.models.Repository
import com.artemissoftware.githubprofile.ui.profile.adapters.RepositoryListAdapter


@BindingAdapter("repos")
fun bindItemViewModels(recyclerView: RecyclerView, repos: List<Repository>?) {

    repos?.let {

        val adapter = if (recyclerView.adapter != null && recyclerView.adapter is RepositoryListAdapter) {
            recyclerView.adapter as RepositoryListAdapter
        } else {
            val adapter_ = RepositoryListAdapter()
            recyclerView.adapter = adapter_
            adapter_
        }

        adapter.submitList(it)
    }



}
