require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /

    state: Start
        q!: $regex</start>
        a: Здравствуйте! 
        a: Купим билеты?
        
        state: Accepted
            q: (да/давай/хорошо)
            a: Ура!
            
        state: Rejected
            q: (нет/не надо)
            a: Ничего другого я не умею :(
            

    state: Hello
        intent!: /привет
        a: Привет привет

    state: Bye
        intent!: /пока
        a: Пока пока

    state: NoMatch
        event!: noMatch
        a: Я не понял. Вы сказали: {{$request.query}}

    state: Match
        event!: match
        a: {{$context.intent.answer}}