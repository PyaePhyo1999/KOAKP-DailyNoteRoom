package com.example.koakptutorial2.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koakptutorial2.R
import com.example.koakptutorial2.Utils
import com.example.koakptutorial2.adapters.ToDoAdapter
import com.example.koakptutorial2.db.entities.Todo
import com.example.koakptutorial2.db.provider.DatabaseProvider
import java.security.Provider
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity(),ToDoAdapter.OnTapView {

      companion object{
          fun newIntent(context: Context,categoryId : Int):Intent {
              val intent = Intent(context,MainActivity::class.java)
              intent.putExtra(Utils.CATEGORY_ID,categoryId)
              return intent

          }
      }
      private val categoryId by lazy{
          intent.getIntExtra(Utils.CATEGORY_ID,-1)
      }



     private val adapter by lazy {
         ToDoAdapter(this)
     }
    private val executor = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        getCategoryTitle()

//        val ivBackPres = findViewById<ImageView>(R.id.ivBackPress)
//        ivBackPres.setOnClickListener {
//            onBackPressed()
//            finish()
//        }



        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val rvTodo = findViewById<RecyclerView>(R.id.rvToDo)
        val adapter = adapter
        val layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)
        rvTodo.adapter = adapter
        rvTodo.layoutManager = layoutManager
        queryDb()

    }

    private fun getCategoryTitle() {

        executor.execute{
            val category = DatabaseProvider.instance(this).categoryDao().getCategoryId(categoryId)
            setTitle(category.name)
        }

    }

    fun queryDb() {
        executor.execute {
            val database = DatabaseProvider.instance(this)
            val todoDao = database.todoDao()
            val todoList = todoDao.selectCategoryId(categoryId)
            runOnUiThread{
                adapter.submitList(todoList)
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

    if (item.itemId == R.id.add_item){
        Log.i("MainActivity","Add Item")

        val editText = EditText(this)
        editText.isSingleLine = true
        val dialog = AlertDialog.Builder(this)
            .setTitle("Type new")
            .setView(editText)
            .setPositiveButton("OK"){ dialog, _ ->
               val textItem = editText.text.toString()
                if (textItem.isNotEmpty()){
                    val addItem = Todo(
                        id = 0,
                        text = textItem,
                        categoryId = categoryId
                    )
                    executor.execute{
                        DatabaseProvider.instance(this).todoDao().insert(addItem)
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

    override fun onTapDelete(todo: Todo) {
        executor.execute{
            DatabaseProvider.instance(this).todoDao().delete(todo)
            queryDb()
        }
    }
}