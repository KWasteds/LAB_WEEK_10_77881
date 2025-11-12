package com.example.lab_week_10_77881

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.lab_week_10_77881.database.Total
import com.example.lab_week_10_77881.database.TotalDatabase
import com.example.lab_week_10_77881.database.TotalObject
import com.example.lab_week_10_77881.viewmodels.TotalViewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private val db by lazy { prepareDatabase() }
    private val viewModel by lazy { ViewModelProvider(this)[TotalViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeValueFromDatabase()
        prepareViewModel()
    }

    private fun updateText(total: Int) {
        findViewById<TextView>(R.id.text_total).text =
            getString(R.string.text_total, total)
    }

    private fun prepareViewModel() {
        viewModel.total.observe(this) {
            updateText(it)
        }
        findViewById<Button>(R.id.button_increment).setOnClickListener {
            viewModel.incrementTotal()
        }
    }

    private fun prepareDatabase(): TotalDatabase {
        return Room.databaseBuilder(
            applicationContext,
            TotalDatabase::class.java, "total-database"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    private fun initializeValueFromDatabase() {
        val totalList = db.totalDao().getTotal(ID)
        if (totalList.isEmpty()) {
            db.totalDao().insert(
                Total(
                    id = ID,
                    total = TotalObject(0, Date().toString())
                )
            )
            viewModel.setTotal(0)
        } else {
            viewModel.setTotal(totalList.first().total.value)
        }
    }

    override fun onPause() {
        super.onPause()
        val newTotal = TotalObject(
            value = viewModel.total.value ?: 0,
            date = Date().toString()
        )
        db.totalDao().update(Total(ID, newTotal))
    }

    override fun onStart() {
        super.onStart()
        val total = db.totalDao().getTotal(ID)
        if (total.isNotEmpty()) {
            Toast.makeText(this, "Last updated: ${total.first().total.date}", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        const val ID: Long = 1
    }
}
