package ru.skillbranch.devintensive.extensions

fun String.truncate(length: Int = 16): String {
    return if (this.trim().length > length) {
        this.substring(0, length).trim().plus("...")
    } else {
        this.trim()
    }
}

fun String.stripHtml(): String {
    var result = this
    while (result.indexOf('<') != -1) {
        if (result.indexOf('>') != -1) {
            result =
                result.replace(result.substring(result.indexOf('<'), result.indexOf('>') + 1), "")
        }
    }
    while (result.indexOf("  ") != -1) {
        result = result.replace("  ", " ")
    }
    return result
}