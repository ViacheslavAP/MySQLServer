package ru.perelyginva.mysqlserver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.perelyginva.mysqlserver.databinding.ActivityMainBinding
import ru.perelyginva.mysqlserver.tabs.TabCategories
import ru.perelyginva.mysqlserver.tabs.TabFilter
import ru.perelyginva.mysqlserver.tabs.TabPanel
import ru.perelyginva.mysqlserver.tabs.TabProduct

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
        supportFragmentManager.beginTransaction().replace(R.id.content, TabPanel()).commit()

        binding?.bottomNav?.setOnItemSelectedListener { item ->

            when (item.itemId) {
                R.id.panelItemBottomNav -> supportFragmentManager.beginTransaction()
                    .replace(R.id.content, TabPanel()).commit()
                R.id.catalogProductsItemBottomNav -> supportFragmentManager.beginTransaction()
                    .replace(R.id.content, TabProduct()).commit()
                R.id.catalogClothesItemBottomNav -> supportFragmentManager.beginTransaction()
                    .replace(R.id.content, TabFilter()).commit()
                R.id.catalogCategoriesItemBottomNav -> supportFragmentManager.beginTransaction()
                    .replace(R.id.content,  TabCategories()).commit()
            }

            return@setOnItemSelectedListener true
        }

        binding?.bottomNav?.selectedItemId = R.id.panelItemBottomNav
    }
}
