package com.example.koakptutorial2.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koakptutorial2.R
import com.example.koakptutorial2.adapters.CategoryAdapter
import com.example.koakptutorial2.adapters.ToDoAdapter
import com.example.koakptutorial2.db.dao.CategoryDao
import com.example.koakptutorial2.db.entities.Category
import com.example.koakptutorial2.db.entities.Todo
import com.example.koakptutorial2.db.provider.DatabaseProvider
import java.security.Provider
import java.util.concurrent.Executors

class CategoryActivity : AppCompatActivity(),CategoryAdapter.OnTapView {

     private val adapter by lazy {
         CategoryAdapter(this)
     }
    private val executor = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        title = "Category"
        val rvCategory = findViewById<RecyclerView>(R.id.rvCategory)
        val adapter = adapter
        val layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)
        rvCategory.adapter = adapter
        rvCategory.layoutManager = layoutManager
        queryDb()

    }
    fun queryDb() {
        executor.execute {
            val database = DatabaseProvider.instance(this)
            val categoryDao = database.categoryDao()
            val categoryList = categoryDao.selectAll()
            runOnUiThread{
                adapter.submitList(categoryList)
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_category, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

    if (item.itemId == R.id.category_add_item){
        Log.i("MainActivity","Add Item")

        val editText = EditText(this)
        editText.isSingleLine = true
        val dialog = AlertDialog.Builder(this)
            .setTitle("Type new Category")
            .setView(editText)
            .setPositiveButton("OK"){ dialog, _ ->
               val textItem = editText.text.toString()
                if (textItem.isNotEmpty()){
                    val addItem = Category(
                        id = 0,
                        name = textItem
                    )
                    executor.execute{
                        DatabaseProvider.instance(this).categoryDao().insert(addItem)
                        queryDb()
                    }

                }
                dialog.dismiss()
            }.create()
        dialog.show()
        return true
    }
        return super.onOptionsItemSelected(item)
    }


    override fun onTapCategory(category: Category) {
        val intent = MainActivity.newIntent(this,category.id)
        startActivity(intent)
    }
}