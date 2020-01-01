package ru.skillbranch.devintensive.models

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = question.question

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        return if (question.isInputCorrect(answer)) {
            if (question.answers.contains(answer.toLowerCase())) {
                question = question.nextQuestion()
                "Отлично - ты справился\n${question.question}" to status.color
            } else {
                if (question == Question.IDLE) {
                    question.question to status.color
                } else if (status != Status.CRITICAL) {
                    status = status.nextStatus()
                    "Это неправильный ответ\n${question.question}" to status.color
                } else {
                    status = Status.NORMAL
                    question = Question.NAME
                    "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
                }
            }
        } else {
            question.getCondition() + "\n${question.question}" to status.color
        }
    }


    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun getCondition(): String = "Имя должно начинаться с заглавной буквы"

            override fun isInputCorrect(input: String): Boolean =
                input.matches(Regex("[A-Z]{1}.*")) || input.matches(Regex("[А-Я]{1}.*"))

            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun getCondition(): String = "Профессия должна начинаться со строчной буквы"

            override fun isInputCorrect(input: String): Boolean =
                input.matches(Regex("[a-z]{1}.*")) || input.matches(Regex("[а-я]{1}.*"))

            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun getCondition(): String = "Материал не должен содержать цифр"

            override fun isInputCorrect(input: String): Boolean =
                input.matches(Regex("[a-zA-Z]+")) || input.matches(Regex("[а-яА-Я]+"))

            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun getCondition(): String = "Год моего рождения должен содержать только цифры"

            override fun isInputCorrect(input: String): Boolean = input.matches(Regex("[0-9]+"))

            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun getCondition(): String = "Серийный номер содержит только цифры, и их 7"

            override fun isInputCorrect(input: String): Boolean = input.matches(Regex("[0-9]{7}"))

            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun getCondition(): String = ""

            override fun isInputCorrect(input: String): Boolean = true

            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question

        abstract fun isInputCorrect(input: String): Boolean

        abstract fun getCondition(): String
    }

}