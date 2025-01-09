Для работы требуется аутентифицироваться через base authentication. 
В файлах конфигурации указаны данные user.username user.password

Для ветки main:

Приложения принимает POST запрос по адресу "/pay".
В теле запроса должна быть запись PaymentDTO, структура и пример:
PaymentDTO (JSON) :
{
    "from": "me",
    "to": "you",
    "amount": 1
}
Если данные в запросе корректны идет запись в базу:
ответ Payment (XML):
<Payment>
    <id>6a17af50-4ecd-4d1d-abff-42ec8f88bee4</id>
    <source>me</source>
    <destination>you</destination>
    <amount>1</amount>
    <operationDate>2025-01-09T21:08:52.1826158</operationDate>
</Payment>

Так же можно сделать GET запрос по адресу "/pay/{id}" например c указанным выше id будет "/pay/6a17af50-4ecd-4d1d-abff-42ec8f88bee4"

Если данные не верны на этапе валидации клиенту возвращается Exception (XML). Например для:
{
    "to": "you",
    "amount": -1
}
Ответ будет
<Map>
    <msg>There is an error</msg>
    <code>400</code>
    <time>2025-01-09 21:13:09</time>
    <errors>
        <amount>Amount should be more than zero!</amount>
        <from>Sender of payment should be provided!</from>
    </errors>
</Map>

Содержащий имена полей, которые не прошли валидацию через jakarta.validation.constraints и сообщений к ним

Для ветки dev:

1. Запись PaymentDTO в теле не должна содержать пункта "from", так как это информация берется из данных аутентификации
{
    "to": "you",
    "amount": 1
}

2. Пункт "to" должен существовать в базе, иначе будет исключение "ReceiverOfPaymentNotFound"



Можно указать operationDate
{
    "to": "Alice",
    "amount": 1,
    "operationDate": "2019-01-09T21:30:27"
}

Но если дата не будет указана, запишется текущая дата.
