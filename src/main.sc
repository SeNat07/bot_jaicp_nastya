require: slotfilling/slotFilling.sc
  module = sys.zb-common
  
require: localPatterns.sc
require: patterns.sc
    module = sys.zb-common
    
theme: /

    state: Start
        q!: $regex</start>
        q!: ($hi/$hello)
        random:
            a: Здравствуйте! 
            a: Привет! 
            a: Приветствую! 
        a: меня зовут {{$injector.botName}}
        script:
            $response.replies = $response.replies || [];
            $response.replies.push({
                type: "image",
                imageUrl: "https://img5.goodfon.ru/wallpaper/nbig/5/c1/devushka-aeroport-samolet-chemodan.jpg",
                text: "Аэропорт"
                })
        go!: /Service/SuggestHelp


    state: Reset
        q!: $regex</reset>
        script:
            $client = {};
            $session = {};
            

    state: NoMatch || noContext = true
        event!: noMatch
        a: Я не понял. Переформулируйте.
        
        
theme: /Service
    
    state: SuggestHelp
        q: отмена || fromState = /Phone/Ask
        a: Купим билеты?
        buttons:
            "Да"
            "Нет"
        
        state: Accepted
            q: (да/давай/хорошо)
            a: Ура!
            if: $client.phone
                go!: /Phone/Confirm
            else:
                go!: /Phone/Ask
            
        state: Rejected
            q: (нет/не надо)
            a: Ничего другого я не умею :(
                
theme: /Phone
    state: Ask || modal = true
        a: Введите номер телефона:
        buttons: 
            "Отмена"
            
        state: GetPhone
            q: $phone
            a: Спасибо!
            script:
                log("-----" + toPrettyString($parseTree));
            a: {{ $parseTree._phone }}
            go!: /Phone/Confirm
            
        state: LocalCatchAll
            event: noMatch
            a: Напишите номер телефона.
            
    state: Confirm
        script: 
            $session.propbablyPhone = $parseTree._phone || $client.phone;
        a: Ваш номер {{$session.propbablyPhone}}, да?
        
        state: Yes
            q: (да/верно)
            script:
                $client.phone = $session.propbablyPhone;
            a: Хорошо
            
        state: No
            q: (нет/не верно)
            go!: /Phone/Ask