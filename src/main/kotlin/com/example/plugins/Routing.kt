package com.example.plugins


import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Application.configureRouting() {
    install(Locations) {
    }

    routing {

        get<Profile> {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val customer =
                customerStorage.find { it.id == id } ?: return@get call.respondText(
                    "No customer with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(customer)
        }
        get<MyProfile> {
            call.respond(
                MyP(
                    "Иван Петров",
                    "ИИП-213",
                    "+79130004443",
                    "https://gamebomb.ru/files/galleries/001/0/00/351955.jpg"
                )
            )
        }
        get<MyFriends> {
            call.respond(customerStorage)
        }
        get<NewsList> {
            call.respond(newsStorage)
        }
        get<News> {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val customer =
                customerStorage.find { it.id == id } ?: return@get call.respondText(
                    "No customer with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(customer)

        }
    }
}

@Location("/profile/my")
data class MyProfile(
    val full_name: String,
    val group: String,
    val tel: String,
    val avatar_url: String
)

@Serializable
data class MyP(
    val full_name: String,
    val group: String,
    val tel: String,
    val avatar_url: String
)
@Location("/profile/{id}")
@Serializable
data class Profile(
    val id: String,
    val full_name: String,
    val group: String,
    val tel: String,
    val avatar_url: String
)

@Location("/friends/my")
@Serializable
data class MyFriends(
    val friends: List<Profile>,
)

@Location("/news/last")
@Serializable
data class NewsList(
    val newsList: List<News>,
) {

}

@Location("/news/{id}")
@Serializable
data class News(
    val id: String,
    val title: String,
    val description: String,
    val date_time: String,
    val author: Profile,
    val image_url: String,
) {

}



val customerStorage = listOf<Profile>(
    Profile(
        "1",
        "Вика Иванова",
        "БПИ-111",
        "+79132234565",
        "https://24smi.org/public/media/news/2018/09/13/vh97qktnx1x8-10-zvezd-kotorykh-nevozmozhno-uznat-bez-makiiazha.jpg"
    ),
    Profile(
        "2",
        "Андрей Петров",
        "БПИ-111",
        "+79132234565",
        "https://bgportret.com/wp-content/uploads/2020/09/2.-Andrey-Lukanov-768x1024.jpg"
    ),
    Profile(
        "3",
        "Николай Дроздов",
        "БПИ-111",
        "+79132234565",
        "https://www.pravmir.ru/wp-content/uploads/2016/10/Nikolay_Drozdov2.jpg"
    ),
    Profile(
        "4",
        "Виктор Шпак",
        "БПИ-111",
        "+79132234565",
        "https://img.joinfo.com/i/2018/11/5bfafc146a5bf.jpg"
    ),
    Profile(
        "5",
        "Генайдий Лепс",
        "БПИ-111",
        "+79132234565",
        "https://v.img.com.ua/b/1100x999999/6/83/690c973c00cd0c6b68b00a371db62836.jpg"
    ),
    Profile(
        "6",
        "Филлип Киркоров",
        "БПИ-111",
        "+79132234565",
        "https://images11.graziamagazine.ru/upload/img_cache/1af/1af7530135af3f20503dfa35ff04548c_ce_2750x1443x0x680.jpg"
    ),
    Profile(
        "7",
        "Анджелика Коч",
        "БПИ-111",
        "+79132234565",
        "https://br.web.img3.acsta.net/pictures/19/08/15/23/53/3664249.jpg"
    ),
    Profile(
        "8",
        "Лиза Лизова",
        "БПИ-111",
        "+79132234565",
        "https://www.fkpscorpio.com/ccds_cache_img/a0/a0e8b2aba67288e8bc9192445cf00b68.1000x1000x.jpg"
    ),

    )

val newsStorage = listOf<News>(
    News(
        "1", "ПОДВЕДЕНЫ ИТОГИ ОЛИМПИАДЫ \"СОЦИОЛОГИЧЕСКИЕ СТУПЕНЬКИ\"",
        "15 марта 2022 года Институт информатики и вычислительной техники и кафедра СКТ провели XV онлайн олимпиаду по обществознанию для учеников 10-11 классов «Социологические ступеньки».\n" +
                "С 5 февраля по 15 марта была также проведена XII заочная олимпиада по обществознанию «Социологические ступеньки».\n" +
                "\n" +
                "В онлайн олимпиаде приняли участие 36 человек из 18 учебных заведений г. Новосибирска и Новосибирской области.\n" +
                "В заочной олимпиаде приняли участие 5 человек из 5 учебных заведений г. Новосибирска и Новосибирской области.",
        "01.04.2022",
        customerStorage[1],
        "https://mininuniver.ru/images/news/2021-05-25-%D1%8F-%D0%BF%D1%80%D0%BE%D1%84/2021-05-25_11-05-14_SNA.jpg",
    ),
    News(
        "2",
        "ВИКТОРИНА: 65 ЛЕТ ПЕРВОЙ ТЕЛЕПЕРЕДАЧЕ",
        "В этом году телевещание Новосибирска отмечает юбилей: 65 лет назад в городе провели первую телепередачу.\n" +
                "А знаете ли вы, как это произошло?\n" +
                "Предлагаем вам пройти тест и узнать кое-что новое и неожиданное.",
        "30.03.2022",
        customerStorage[3],
        "https://nation-news.ru/uploads/2021/05/24/orig-1621866207edXEgw6nEgauL5qksLfGcnSXKDxcSOdse56rVQ8y.jpeg"
    ),
    News(
        "2",
        "В СИБГУТИ НАЧИНАЕТСЯ СЕРИЯ ВСТРЕЧ С УСПЕШНЫМИ ВЫПУСКНИКАМИ",
        "Выпускники СибГУТИ проведут серию встреч со студентами. Первая встреча состоится в субботу, 2 апреля в 12:00 на Киберполигоне.\n" +
                "\n" +
                "\n" +
                "Со студентами встретятся:\n" +
                "\n" +
                "Роман Вадимович Токарский – заместитель директора по НИОКР компании ООО «Файбер Трейд»;\n" +
                "\n" +
                "Екатерина Николаевна Васильева – начальник технического бюро контрольно-испытательного комплекса АО «НЗПП Восток»;\n" +
                "\n" +
                "Вадим Сергеевич Худяков – ведущий инженер АО «НПО НИИИН-НЗиК»;\n" +
                "\n" +
                "Владимир Сергеевич Перминов – инженер-электронщик ООО «НПФ Элкуб»;\n" +
                "\n" +
                "Диана Сакеновна Иргибаева – инженер-электронщик 2-ой категории АО «НЗПП Восток», магистрант СибГУТИ по профилю «Сети, системы и устройства телекоммуникаций», участник научных грантов;\n" +
                "\n" +
                "Илья Юрьевич Егоров – руководитель команды, ВКонтакте.\n" +
                "\n" +
                "\n" +
                "Гости научат выбирать работодателя, расскажут, как правильно вести себя на собеседовании, дадут рекомендации по презентации собственных профессиональных навыков. Также, выпускники расскажут о своём пути по карьерной лестнице.\n" +
                "\n" +
                "\n" +
                "Сибирский государственный университет телекоммуникаций и информатики в 2023 году празднует юбилей – 70 лет в вузе готовят специалистов для отрасли связи.",
        "30.03.2022",
        customerStorage[2],
        "https://nation-news.ru/uploads/2021/05/24/orig-1621866207edXEgw6nEgauL5qksLfGcnSXKDxcSOdse56rVQ8y.jpeg"
    ),


    )
