package ru.perelyginva.mysqlserver.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.perelyginva.mysqlserver.api.models.CategoryApiModel
import ru.perelyginva.mysqlserver.databinding.CategoryItemBinding

class CategoryAdapter(
    private val categoriesList: ArrayList<CategoryApiModel>,
    private val deleteCategory: (Int) -> Unit,
    private val editCategory: (CategoryApiModel) -> Unit,
) : RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    class CategoryHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            categories: CategoryApiModel, deleteCategory: (Int) -> Unit,
            editCategory: (CategoryApiModel) -> Unit,
        ) {

            val idCategory = categories.id
            binding.idCategory.text = idCategory.toString()
            binding.nameCategory.text = categories.name

            binding.btnEditCategory.setOnClickListener(View.OnClickListener {
                editCategory(categories)
            })
            binding.btnDeleteCategory.setOnClickListener(View.OnClickListener {
                deleteCategory(idCategory!!)
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val binding: CategoryItemBinding =
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)
        return CategoryHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind(categoriesList[position],deleteCategory, editCategory)

    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }
}