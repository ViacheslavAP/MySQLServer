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
import ru.perelyginva.mysqlserver.adapter.CategoryAdapter
import ru.perelyginva.mysqlserver.api.ApiClient
import ru.perelyginva.mysqlserver.api.models.CategoryApiModel
import ru.perelyginva.mysqlserver.databinding.FragmentTabCategoriesBinding


class TabCategories : Fragment() {

    private var binding: FragmentTabCategoriesBinding? = null
    private var categoriesAdapter: CategoryAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentTabCategoriesBinding.inflate(inflater, container, false)

        loadCategories()


        binding?.deleteAllCategories?.setOnClickListener(View.OnClickListener {

            clearAllCategories()

        })

        return binding?.root
    }

    private fun loadCategories() {

        val callCategories = ApiClient.instance?.api?.getCategory()
        callCategories?.enqueue(object : Callback<ArrayList<CategoryApiModel>> {
            override fun onResponse(
                call: Call<ArrayList<CategoryApiModel>>,
                response: Response<ArrayList<CategoryApiModel>>,
            ) {

                val loadCategories = response.body()

                binding?.recyclerCategories?.layoutManager = LinearLayoutManager(context)

                categoriesAdapter = loadCategories?.let {
                    CategoryAdapter(
                        it, { idCategory: Int -> deleteCategory(idCategory) },
                        { categoriesApiModel: CategoryApiModel -> editCategory(categoriesApiModel) })
                }
                binding?.recyclerCategories?.adapter = categoriesAdapter

                Toast.makeText(context, "ЗАГРУЗКА", Toast.LENGTH_SHORT).show()


            }

            override fun onFailure(call: Call<ArrayList<CategoryApiModel>>, t: Throwable) {
                Toast.makeText(context, "ОШИБКА! ВКЛЮЧИТЕ ИНТЕРНЕТ!", Toast.LENGTH_SHORT).show()

            }
        })

    }

    private fun deleteCategory(id: Int) {

        val callDeleteCategory: Call<ResponseBody?>? = ApiClient.instance?.api?.deleteCategory(id)

        callDeleteCategory?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(
                    context,
                    "КАТЕГОРИЯ УДАЛЕНА",
                    Toast.LENGTH_SHORT
                ).show()

                loadCategories()
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


    private fun clearAllCategories() {

        val callClearAllCat: Call<ResponseBody?>? = ApiClient.instance?.api?.clearCategories()

        callClearAllCat?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(
                    context,
                    "КАТЕГОРИИ УДАЛЕНЫ",
                    Toast.LENGTH_SHORT
                ).show()

                loadCategories()
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

    private fun editCategory(categoriesApiModel: CategoryApiModel) {
        val panelCategory = PanelEditCategoryFragment()
        val parameters = Bundle()
        parameters.putString("idCategory", categoriesApiModel.id.toString())
        parameters.putString("nameCategory", categoriesApiModel.name)
        panelCategory.arguments = parameters

        panelCategory.show((context as FragmentActivity).supportFragmentManager, "editCategory")
    }


}