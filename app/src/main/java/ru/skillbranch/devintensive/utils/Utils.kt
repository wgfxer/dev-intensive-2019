package ru.skillbranch.devintensive.utils

object Utils {
    val map = mapOf(
        "а" to "a",
        "б" to "b",
        "в" to "v",
        "г" to "g",
        "д" to "d",
        "е" to "e",
        "ё" to "e",
        "ж" to "zh",
        "з" to "z",
        "и" to "i",
        "й" to "i",
        "ё" to "i",
        "к" to "k",
        "л" to "l",
        "м" to "m",
        "н" to "n",
        "о" to "o",
        "п" to "p",
        "р" to "r",
        "с" to "s",
        "т" to "t",
        "у" to "u",
        "ф" to "f",
        "х" to "h",
        "ц" to "c",
        "ч" to "ch",
        "ш" to "sh",
        "щ" to "sh",
        "ъ" to "",
        "ы" to "i",
        "ь" to "",
        "э" to "e",
        "ю" to "yu",
        "я" to "ya"
    )


    fun parseFullName(fullName: String?): Pair<String?, String?> {
        var fullName = fullName
        if (fullName?.trim().isNullOrEmpty()) {
            fullName = null
        }
        val parts: List<String>? = fullName?.trim()?.split(" ")
        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        return firstName to lastName
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        var initials: String? = null
        if (!firstName?.trim().isNullOrEmpty()) {
            initials = firstName?.getOrNull(0).toString().toUpperCase()
        }
        if (!lastName?.trim().isNullOrEmpty()) {
            if (initials.isNullOrEmpty()) {
                initials = ""
            }
            initials += lastName?.getOrNull(0).toString().toUpperCase()
        }
        return initials
    }

    fun transliteration(payload: String, divider: String = " "): String {
        var text: String = payload
        for ((k, v) in map) {
            text = text.replace(k, v)
            text = text.replace(k.toUpperCase(), v.capitalize())
            text = text.replace(" ", divider)
        }
        return text
    }

    fun getPlural(value: Int, one: String, few: String, many: String): String {
        var result: String = when {
            value % 10 == 1 && value % 100 != 11 -> "$value $one"
            value % 10 == 2 && value % 100 != 12
                    || value % 10 == 3 && value % 100 != 13
                    || value % 10 == 4 && value % 100 != 14 -> "$value $few"
            else -> "$value $many"
        }
        return result
    }
}