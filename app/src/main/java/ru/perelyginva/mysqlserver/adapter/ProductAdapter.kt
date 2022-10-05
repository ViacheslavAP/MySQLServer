package ru.perelyginva.mysqlserver.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.perelyginva.mysqlserver.api.models.ProductApiModel
import ru.perelyginva.mysqlserver.databinding.ProductItemBinding

class ProductAdapter(
    private val productList: ArrayList<ProductApiModel>,
    private val deleteProduct: (Int) -> Unit,
    private val editProduct: (ProductApiModel) -> Unit,
) : RecyclerView.Adapter<ProductAdapter.ProductHolder>() {

    class ProductHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            products: ProductApiModel,
            deleteProduct: (Int) -> Unit,
            editProduct: (ProductApiModel) -> Unit,
        ) {

            val idProducts = products.id

            binding.idProduct.text = idProducts.toString()
            binding.nameProduct.text = products.name
            binding.categoryProduct.text = products.category
            binding.priceProduct.text = products.price

            binding.editProduct.setOnClickListener(View.OnClickListener {
                editProduct(products)
            })

            binding.deleteProduct.setOnClickListener(View.OnClickListener {
                deleteProduct(idProducts!!)
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val binding: ProductItemBinding =
            ProductItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)

        return ProductHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
       holder.bind(productList[position], deleteProduct, editProduct)
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}