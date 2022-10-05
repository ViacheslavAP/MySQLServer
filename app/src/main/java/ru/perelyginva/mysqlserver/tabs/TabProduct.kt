package ru.perelyginva.mysqlserver.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.perelyginva.mysqlserver.adapter.ProductAdapter
import ru.perelyginva.mysqlserver.api.ApiClient
import ru.perelyginva.mysqlserver.api.models.ProductApiModel
import ru.perelyginva.mysqlserver.databinding.FragmentTabProductBinding


class TabProduct : Fragment(), View.OnClickListener {

    private var binding: FragmentTabProductBinding? = null
    private var productAdapter: ProductAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentTabProductBinding.inflate(layoutInflater, container, false)

        loadProducts()

        binding?.deleteAllProducts?.setOnClickListener(this)


        return  binding?.root
    }
    private fun loadProducts () {

        val callProducts = ApiClient.instance?.api?.getProduct()
        callProducts?.enqueue(object: Callback<ArrayList<ProductApiModel>> {
            override fun onResponse(
                call: Call<ArrayList<ProductApiModel>>,
                response: Response<ArrayList<ProductApiModel>>
            ) {

                val loadProducts = response.body()

                binding?.recyclerProducts?.layoutManager = LinearLayoutManager(context)
                productAdapter = loadProducts?.let {
                    ProductAdapter(
                        it, { idProduct:Int->deleteProduct(idProduct)},
                        {productsApiModel:ProductApiModel->editProduct(productsApiModel)})
                }
                binding?.recyclerProducts?.adapter = productAdapter

                Toast.makeText(context, "ЗАГРУЗКА", Toast.LENGTH_SHORT).show()


            }

            override fun onFailure(call: Call<ArrayList<ProductApiModel>>, t: Throwable) {
                Toast.makeText(context, "ОШИБКА! ВКЛЮЧИТЕ ИНТЕРНЕТ!", Toast.LENGTH_SHORT).show()

            }
        })

    }

    override fun onClick(v: View?) {

        clearAllProducts()

    }

    private fun deleteProduct(id:Int) {

        val callDeleteProduct: Call<ResponseBody?>? = ApiClient.instance?.api?.deleteProduct(id)

        callDeleteProduct?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(
                    context,
                    "ТОВАР УДАЛЕН",
                    Toast.LENGTH_SHORT
                ).show()

                loadProducts()
            }



            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(
                    context,
                    "ОШИБКА! ВКЛЮЧИТЕ ИНТЕРНЕТ!",
                    Toast.LENGTH_SHORT
                ).show()
            }


        })

    }

    private fun editProduct(productsApiModel: ProductApiModel) {
        val panelEditProduct = PanelEditProductFragment()
        val parameters = Bundle()
        parameters.putString("idProduct", productsApiModel.id.toString())
        parameters.putString("nameProduct", productsApiModel.name)
        parameters.putString("categoryProduct", productsApiModel.category)
        parameters.putString("priceProduct", productsApiModel.price)
        panelEditProduct.arguments = parameters

        panelEditProduct.show((context as FragmentActivity).supportFragmentManager, "editProduct")
    }

    private fun clearAllProducts() {

        val callClearAllProducts: Call<ResponseBody?>? = ApiClient.instance?.api?.clearProducts()

        callClearAllProducts?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(
                    context,
                    "ТОВАРЫ УДАЛЕНЫ",
                    Toast.LENGTH_SHORT
                ).show()

                loadProducts()
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(
                    context,
                    "ОШИБКА! ВКЛЮЧИТЕ ИНТЕРНЕТ!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })

    }

}