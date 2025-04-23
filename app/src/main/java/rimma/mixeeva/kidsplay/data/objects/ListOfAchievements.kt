package rimma.mixeeva.kidsplay.data.objects

object ListOfAchievements {
    var achievements = listOf(
        Achievement(
            title = "Юный исследователь",
            condition = "Пройди 10 разных уровней или мини-игр.",
            description = "Ты открыл для себя много нового! Продолжай в том же духе!",
            obtained = true
        ),
        Achievement(
            title = "Мастер головоломок",
            condition = "Реши 15 задач на логику без ошибок.",
            description = "Твой ум острый, как меч! Ни одна загадка не устоит перед тобой!",
            obtained = true
        ),
        Achievement(
            title = "Быстрый как молния",
            condition = "Выполни задание на время с 3 звездами.",
            description = "Никто не может догнать тебя! Ты просто реактивный!",
            obtained = true
        ),
        Achievement(
            title = "Добрый помощник",
            condition = "Помоги 5 персонажам в игре (например, выполни их просьбы).",
            description = "Ты не только умный, но и очень отзывчивый! Мир стал лучше благодаря тебе!",
            obtained = true
        ),
        Achievement(
            title = "Коллекционер знаний",
            condition = " Собери все обучающие карточки (или предметы) в одном разделе игры.",
            description = "Теперь ты знаешь всё об этом! Может, пора делиться знаниями с друзьями?",
            obtained = true
        ),


    )
}