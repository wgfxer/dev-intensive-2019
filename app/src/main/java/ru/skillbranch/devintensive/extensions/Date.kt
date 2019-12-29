package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.utils.Utils
import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time
    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    var diff = date.time - this.time
    var result: String = ""
    when {
        diff in 0 until SECOND -> result = "только что"
        diff in SECOND until 45 * SECOND -> result = "несколько секунд назад"
        diff in 45 * SECOND until 75 * SECOND -> result = "минуту назад"
        diff in 75 * SECOND until 45 * MINUTE -> result =
            "${TimeUnits.MINUTE.plural((diff / MINUTE).toInt())} назад"
        diff in 45 * MINUTE until 75 * MINUTE -> result = "час назад"
        diff in 75 * MINUTE until 22 * HOUR -> result =
            "${TimeUnits.HOUR.plural((diff / HOUR).toInt())} назад"
        diff in 22 * HOUR until 26 * HOUR -> result = "день назад"
        diff in 26 * HOUR until 360 * DAY -> result =
            "${TimeUnits.DAY.plural((diff / DAY).toInt())} назад"
        diff > 360 * DAY -> result = "более года назад"
    }
    return result
}

enum class TimeUnits{
    SECOND {
        override fun plural(value: Int): String {
            return Utils.getPlural(value, "секунду", "секунды", "секунд")
        }
    },
    MINUTE {
        override fun plural(value: Int): String {
            return Utils.getPlural(value, "минуту", "минуты", "минут")
        }
    },
    HOUR {
        override fun plural(value: Int): String {
            return Utils.getPlural(value, "час", "часа", "часов")
        }
    },
    DAY {
        override fun plural(value: Int): String {
            return Utils.getPlural(value, "день", "дня", "дней")
        }
    };

    abstract fun plural(value: Int): String
}