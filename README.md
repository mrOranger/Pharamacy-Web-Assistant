# Pharamacy-Web-Assistant
Questa Web Application è da considerare come la continuazione del progetto
**[Pharmacy Web System](https://github.com/mrOranger/Pharmacy-Management-System/)**, 
sviluppato per un progetto relativo ad un esame accademico. 

Per un riferimento specifico, è possibile rifarsi alla documentazione del progetto,
in questa repository, invece, verrà ripreso questo progetto, quindi
analizzato e rielaborato con tecnologie più adeguate. Dunque
in seguito verrà esposta solamente una panoramica, partendo dal database
che verrà migrato in **SQLite**, il frontend che verrà realizzato con
**AngularJS** ed il backend che precedentemente era stato sviluppato in
**JakartaEE**, verrà rileaborato con il framework **Spring Boot**.

## Rielaborazione del database in SQLite
In precedenza, il database era stato creato usando Oracle, 
completo di business rule realizzate attraverso trigger, e i relativi
indici per ottimizzare le operazioni più comuni.

In questa variante, tuttavia, il database verrà migrato da Oracle,
a SQLite, perdendo alcune caratteristiche fondamentali del primo. La scelta
è riconducibile al fatto che si intente usare un database più _"leggero"_ 
e si spostino le regole di business dal database al backend.
### Modello E-R
Di seguito viene riproposto il diagramma ER del database progettato precedentemente in Oracle:
![Modello E-R del Database](/images/Diagram-ER.png)
per l'analisi delle fasi di progettazione del database, ci si può riferire alla documentazione
del progetto, il cui link alla relativa repository è in questo documento.
### Modello Logico
Successivamente, il modello è stato quindi adattato per il DBMS Oracle,
ed il relativo schema logico che è derivato è il seguente:
![Modello logico del Database](/images/Diagram-Logic.png)

## Rielaborazione del backend con Spring Boot

La rielaborazione del backend prevede l'uso del framework Spring Boot, l'idea dietro questa 
adozione è derivata dalle limitazione di **JakartaEE** precedentemente utilizzato
e dal fatto che **Spring Boot** sia un framework solido, con un ottimo supporto. Inoltre,
in questo progetto si vuole passare dal semplice sviluppo di _Servlet Java_,
alla realizzazione di una _Restful API_. 

### Requisiti funzionali dell'applicazione
Dall'analisi del documento precedente, i requisiti funzionali che l'applicazione
deve essere in grado di esporre sono i seguenti:
- Autenticazione dell'utente mediante credenziali.
- Registrazione di un nuovo utente.
- Inserimento di un nuovo Cosmetico/Medicina.
- Inserimento di una nuova Prescrizione Medica.
- Ricerca di un Prodotto appartenente ad una Casa Farmaceutica.
- Visualizzazione di tutti i Prodotti Medici.
- Registrazione di una nuova vendita.
- Ricerca delle Prescrizioni Mediche di un Paziente.
- Riceca delle Vendite appartenenti ad un range temporale.
- Stampa delle Statistiche delle vendite.

Per i seguenti requisiti funzionali, è necessario che il nostro servizio REST
esponga i seguenti endpoint.
- _/signin_, per autenticare un Utente.
- _/signup_, per registrare un nuovo Utente.
- _/products_, per i Prodotti.
- _/prescriptions_, per le Prescrizioni.
- _/sales_, per le Vendite.
- _/statistics_, per generare le Statistiche.

Per ciascuno di questo endpoint, esponiamo i seguenti metodi HTTP
per le operazioni che definiscono i requisiti funzionali:

|              **URL**             | **HTTP** |                                       **Descrizione**                                      |
|:--------------------------------:|:--------:|:------------------------------------------------------------------------------------------:|
|             _/signin_            |   POST   |               Richiede l'autenticazione di un utente, usando email e password              |
|             _/signup_            |   POST   | Richiede la registrazione di un nuovo utente, specificando nome, cognome, email e password |
|            _/products_           |    GET   |               Richiede di restituire tutti i Prodotti registrati nel sistema               |
|            _/products_           |   POST   |                         Richiede l'inserimento di un nuovo Prodotto                        |
|      _/prescriptions/{uid}_      |    GET   |             Richiede di visionare lo storico delle Prescrizioni di un paziente             |
|         _/prescriptions_         |   POST   |                         Richiede di inserire una nuova Prescrizione                        |
| _/sales/start/{start}/end/{end}_ |    GET   |      Richiede di visionare lo storico delle Vendite appartenenti ad un range temporale     |
|             _/sales_             |   POST   |                         Richiede l'inserimento di una nuova Vendita                        |
|           _/statistics_          |    GET   |                     Richiede di visionare le statistiche delle Vendite                     |


## Rielaborazione del frontend con AngularJS

