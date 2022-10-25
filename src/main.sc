require: slotfilling/slotFilling.sc
  module = sys.zb-common
  
require: localPatterns.sc
theme: /

    state: Start
        q!: $regex</start>
        q!: $hi
        random:
            a: Здравствуйте! 
            a: Привет! 
            a: Приветствую! 
        go!: /Service/SuggestHelp
            

    state: NoMatch
        event!: noMatch
        a: Я не понял. Переформулируйте.
        
        
theme: /Service
    
    state: SuggestHelp
        a: Купим билеты?
        
        state: Accepted
            q: (да/давай/хорошо)
            a: Ура!
            
        state: Rejected
            q: (нет/не надо)
            a: Ничего другого я не умею :(