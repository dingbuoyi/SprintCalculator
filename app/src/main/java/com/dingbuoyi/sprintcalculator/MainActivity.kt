package com.dingbuoyi.sprintcalculator

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.dingbuoyi.sprintcalculator.core.SprintCalculator
import com.dingbuoyi.sprintcalculator.core.SprintDate
import com.dingbuoyi.sprintcalculator.model.SprintModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*
import kotlin.collections.HashSet
import android.os.HandlerThread
import android.os.Looper


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

            if (startDate!!.isWeekend) {
                Snackbar.make(view, getString(R.string.start_date_should_not_be_weekend), Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (startDate!!.isPublicHoliday) {
                Snackbar.make(view, getString(R.string.start_date_should_not_be_public_holiday), Snackbar.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            if (endDate == null) {
                Snackbar.make(view, getString(R.string.please_select_end_date), Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            var sprintNumber = Integer.valueOf(sprintNumberEditText.text.toString())
            if (sprintNumber < 1) {
                Snackbar.make(view, getString(R.string.sprint_number_should_bigger_than_0), Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            sprintModel.weeks = periodSpinner.selectedItemPosition + 1
            sprintModel.retrospectiveDays = retrospectiveMeetingSpinner.selectedItemPosition + 1
            sprintModel.innovationDays = innovationDaysSpinner.selectedItemPosition + 1
            sprintModel.planDays = planMeetingSpinner.selectedItemPosition + 1
            sprintModel.demoDays = demoSpinner.selectedItemPosition + 1

            sprintCalculator =
                    SprintCalculator(sprintModel, startDate, endDate, holidays)

            val handlerThread = HandlerThread("SprintCalculator Thread")
            handlerThread.start()
            Handler(handlerThread.looper).post {
                val intent = Intent(MainActivity@ this, SprintScheduleActivity::class.java)
                intent.putExtra(ActivityExtras.SPRINT_SCHEDULE_LIST, sprintCalculator.execute())
                intent.putExtra(ActivityExtras.SPRINT_NUMBER, sprintNumber)
                startActivity(intent)
            }
        }

        startDateBtn.setOnClickListener {
            selectStartDate()
        }

        periodSpinner.setSelection(2)// default selected 3 weeks
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
