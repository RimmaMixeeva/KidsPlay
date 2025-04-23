package rimma.mixeeva.kidsplay.data.objects

object ListOfGifts {
    var gifts = listOf(
        Gift(
            id = 1,
            title = "Повышение навыков",
            condition = "Заверши первое задание",
            description = "Получить +2 интеллекта",
            obtained = true,
            opened = true,
            used = false,
            intelligence = 2
        ),
        Gift(
            id = 2,
            title = "Золотая головоломка",
            condition = "Реши 10 задач без ошибок",
            description = "Получить +10 Внимательности",
            obtained = true,
            opened = true,
            used = false,
            attentiveness = 10
        ),
        Gift(
            id = 3,
            title = "Мягкая игрушка",
            condition = "Завершить игру в цвета",
            description = "Выбери любую мягкую игрушку в магазине и тебе её купят",
            obtained = true,
            opened = true,
            executor = "мама",
            used = false
        ),
        Gift(
            id = 4,
            title = "Вкусная еда",
            condition = "Закончи прохождение 1го уровня игры в Цвета",
            description = "Получи шоколадку от родителя!",
            obtained = true,
            opened = true,
            executor = "папа",
            used = false
        )
    )
}