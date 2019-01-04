package com.dingbuoyi.sprintcalculator

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.HashSet

class MainActivity : AppCompatActivity() {
    private lateinit var sprintCalculator: SprintCalculator
    private var startDate: SprintDate? = null
    private var endDate: SprintDate? = null

    private val holidays = HashSet<SprintDate>()
    private val sprintModel = SprintModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            if (startDate == null) {
                Snackbar.make(view, getString(R.string.please_select_start_date), Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (endDate == null) {
                Snackbar.make(view, getString(R.string.please_select_end_date), Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            sprintModel.weeks = 3
            sprintModel.reviewDays = 1
            sprintModel.innovationDays = 1
            sprintModel.planDays = 1

            sprintCalculator = SprintCalculator(sprintModel, startDate, endDate, holidays)
            sprintCalculator.execute()
        }

        startDateBtn.setOnClickListener {
            selectStartDate()
        }
    }

    private fun selectStartDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val startDateStr = StringBuilder()
        val endDateStr = StringBuilder()
        var datePickerDialog =
            DatePickerDialog(this@MainActivity, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                startDateStr.append(year).append("/").append(month + 1).append("/").append(dayOfMonth)
                startDateTextView.text = startDateStr

                endDateStr.append(year).append("/").append(12).append("/").append(31)
                endDateTextView.text = endDateStr

                startDate = SprintDate(year, month, dayOfMonth)
                endDate = SprintDate(year, 11, 31)

            }, year, month, day)
        datePickerDialog.show()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
