package ru.perelyginva.mysqlserver.tabs

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import ru.perelyginva.mysqlserver.R
import ru.perelyginva.mysqlserver.api.ApiClient
import ru.perelyginva.mysqlserver.databinding.FragmentPanelEditCategoryBinding


class PanelEditCategoryFragment : BottomSheetDialogFragment(), View.OnKeyListener {

    private var binding: FragmentPanelEditCategoryBinding? = null
    private var idCategory: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPanelEditCategoryBinding.inflate(inflater, container, false)

        idCategory = arguments?.getString("idCategory")?.toInt()
        binding?.editCategory?.setText(arguments?.getString("nameCategory").toString())


        binding?.editCategory?.setOnKeyListener(this)

        return binding?.root
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        when (v?.id) {
            R.id.editCategory -> {
                if (event?.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    updateCategory(binding?.editCategory?.text?.toString()!!)

                    return true
                }

            }
        }
        return false
    }


    private fun updateCategory(name: String) {
        val callUpdateCategory =
            ApiClient.instance?.api?.updateCategory(idCategory.toString().toInt(), name)

        callUpdateCategory?.enqueue(object : retrofit2.Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(
                    context,
                    "КАТЕГОРИЯ ОБНОВЛЕНА",
                    Toast.LENGTH_SHORT
                ).show()

                loadScreen()

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

    private fun loadScreen() {

        binding?.editCategory?.setText("")

        dismiss()

        (context as FragmentActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.content, TabCategories()).commit()

    }


}