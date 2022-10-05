package ru.perelyginva.mysqlserver.tabs

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.perelyginva.mysqlserver.R
import ru.perelyginva.mysqlserver.api.ApiClient
import ru.perelyginva.mysqlserver.databinding.FragmentTabPanelBinding


class TabPanel : Fragment(), View.OnKeyListener, View.OnClickListener {

    private var binding: FragmentTabPanelBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

       binding = FragmentTabPanelBinding.inflate(layoutInflater, container, false)

        binding?.enterNameProduct?.setOnKeyListener(this)
        binding?.enterCategoryProduct?.setOnKeyListener(this)
        binding?.enterPriceProduct?.setOnKeyListener(this)

        binding?.buttonAddCategoryClothes?.setOnClickListener(this)
        binding?.buttonAddCategoryShoes?.setOnClickListener(this)
        binding?.buttonAddCategoryAccessories?.setOnClickListener(this)
        binding?.buttonAddProduct?.setOnClickListener(this)

        return binding?.root
    }

    override fun onKey(view: View, i: Int, keyEvent: KeyEvent): Boolean {
        when (view.id) {

            R.id.enterNameProduct -> {
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                    binding?.resEnterNameProduct?.text = binding?.enterNameProduct?.text
                    binding?.enterNameProduct?.setText("")
                    return true
                }

            }

            R.id.enterCategoryProduct -> {
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                    binding?.resEnterCategoryProduct?.text = binding?.enterCategoryProduct?.text
                    binding?.enterCategoryProduct?.setText("")
                    return true
                }

            }

            R.id.enterPriceProduct -> {
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                    binding?.resEnterPriceProduct?.text = binding?.enterPriceProduct?.text
                    binding?.enterPriceProduct?.setText("")
                    return true
                }

            }
        }

        return false
    }

    override fun onClick(view: View) {

        when(view.id) {

            R.id.buttonAddCategoryClothes -> {

                insertCategory(binding?.buttonAddCategoryClothes?.text?.toString())

            }

            R.id.buttonAddCategoryShoes -> {

                insertCategory(binding?.buttonAddCategoryShoes?.text?.toString())

            }

            R.id.buttonAddCategoryAccessories -> {

                insertCategory(binding?.buttonAddCategoryAccessories?.text?.toString())

            }

            R.id.buttonAddProduct -> {

                insertProduct(
                    binding?.resEnterNameProduct?.text?.toString(),
                    binding?.resEnterCategoryProduct?.text?.toString(),
                    binding?.resEnterPriceProduct?.text?.toString())

                clearResEnterProduct()

            }


        }

    }

    private fun clearResEnterProduct() {
        binding?.resEnterNameProduct?.text = ""
        binding?.resEnterCategoryProduct?.text = ""
        binding?.resEnterPriceProduct?.text = ""

    }


    private fun insertCategory(name: String?) {
        val callInsertCategory: Call<ResponseBody?>? = ApiClient.instance?.api?.insertCategory(name)
        callInsertCategory?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(context, "ДОБАВЛЕНА КАТЕГОРИЯ", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(context, "ОШИБКА", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun insertProduct(name: String?, category: String?, price: String?) {
        val callInsertProduct: Call<ResponseBody?>? = ApiClient.instance?.api?.insertProduct(name, category, price)
        callInsertProduct?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(context, "ТОВАР ДОБАВЛЕН", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(context, "ОШИБКА", Toast.LENGTH_SHORT).show()
            }
        })
    }


}