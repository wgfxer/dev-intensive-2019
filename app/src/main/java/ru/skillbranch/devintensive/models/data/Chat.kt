package ru.skillbranch.devintensive.models.data

import ru.skillbranch.devintensive.extensions.shortFormat
import ru.skillbranch.devintensive.models.BaseMessage
import ru.skillbranch.devintensive.models.ImageMessage
import ru.skillbranch.devintensive.models.TextMessage
import ru.skillbranch.devintensive.utils.Utils
import java.lang.IllegalStateException
import java.util.*

data class Chat(
    val id: String,
    val title: String,
    val members: List<User> = listOf(),
    var messages: MutableList<BaseMessage> = mutableListOf(),
    var isArchived: Boolean = false
) {

    fun unreadableMessageCount(): Int {
        return messages.filter { !it.isReaded }.size
    }

    fun lastMessageDate(): Date? {
        val sortedList = messages.sortedByDescending { it.date }
        return if (sortedList.isNullOrEmpty()) {
            null
        } else {
            sortedList[0].date
        }
    }

    fun lastMessageShort(): Pair<String, String> {
        val sortedList = messages.sortedByDescending { it.date }
        if (sortedList.isNullOrEmpty()) {
            return "null" to "null"
        }
        val lastMessage: BaseMessage = sortedList[0]
        return when (lastMessage) {
            is TextMessage -> {
                val body: String = lastMessage.text ?: "Сообщений еще нет"
                val user: String = lastMessage.from.firstName ?: "No name"
                body to user
            }
            is ImageMessage -> {
                val user: String = lastMessage.from.firstName ?: "No name"
                val body = "$user - отправил фото"
                body to user
            }
            else -> throw IllegalStateException("not yet implemented")
        }
    }

    fun isSingle(): Boolean = members.size == 1

    fun toChatItem(): ChatItem {
        return if (isSingle()) {
            val user = members.first()
            ChatItem(
                id,
                user.avatar,
                Utils.toInitials(user.firstName, user.lastName) ?: "??",
                "${user.firstName ?: ""} ${user.lastName ?: ""}",
                lastMessageShort().first,
                unreadableMessageCount(),
                lastMessageDate()?.shortFormat(),
                user.isOnline
            )
        } else {
            ChatItem(
                id,
                null,
                "",
                title,
                lastMessageShort().first,
                unreadableMessageCount(),
                lastMessageDate()?.shortFormat(),
                false,
                ChatType.GROUP,
                lastMessageShort().second
            )
        }
    }
}


enum class ChatType {
    SINGLE,
    GROUP,
    ARCHIVE
}