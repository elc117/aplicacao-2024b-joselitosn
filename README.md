# User Story
**Como um usuário**, eu quero agendar notificações para datas e horários futuros, para que eu possa receber lembretes ou informações importantes no momento apropriado, através de diferentes plataformas de notificação, como e-mail e notificações push no celular.

# Classes
## Singleton
````mermaid
classDiagram
    class DatabaseManager {
        -DatabaseManager instance
        -DatabaseProvider provider
        -Connection connection
        -EventManager events
        
        -DatabaseManager(provider)
        +getInstance()
        +getInstance(provider)
        +getConnection()
        +closeConnection()
    }
    
    class LogManager {
        -LogManager instance
        -Logger logger
        
        -LogManager(logger)
        +getInstance()
        +getInstance(logger)
        +getLogger()
        +log(message)
    }
````

## Builder
````mermaid
classDiagram
    class DatabaseProvider {
        #String url
        #Properties properties
        
        #DatabaseProvider(Builder builder)
        +getConnection() Connection
        +closeConnection()
    }
    
    class Builder {
        #String url
        #Properties properties
        
        url(url) Builder
        database(database) Builder
        username(username) Builder
        password(password) Builder
        build() DatabaseProvider
    }
    
    class SQLiteProvider {
         -SQLiteProvider(builder)
         +toString()
    }
    
    class MySQLProvider {
        -MySQLProvider(builder)
        +toString()
    }
    
    DatabaseProvider --o Builder : possui
    DatabaseProvider <|-- SQLiteProvider : Herança
    DatabaseProvider <|-- MySQLProvider : Herança
````

## Chain of Responsibility
````mermaid
classDiagram
    class NotificationHandler {
        -NotificationHandler nextHandler
        
        +setNextHandler(nextHandler)
        +handle(notification)
    }
    
    class SMTPNotification {
        -String destination
        
        +SMTPNotification(destination)
        +handle(notification)
    }

    class NtfyNotification {
        -String destination
        -String topic

        +NtfyNotification(destination, topic)
        +handle(notification)
    }
    
    NotificationHandler <|-- SMTPNotification : Herança
    NotificationHandler <|-- NtfyNotification : Herança
````

## Observer
````mermaid
classDiagram
    class EventListener {
        <<interface>>
        update(eventType, message)
    }
    
    class EventManager {
        -Map < String, List < EventListener >> listeners
        
        +EventManager(String... operations)
        +subscribe(eventType, listener)
        +unsubscribe(eventType, listener)
        +notify(eventType, message)
        +getListeners() Map < String, List < EventListener >>
    }
    
    class LogEventListener {
        -Logger logger
        
        +LogEventListener(logger)
        +update(eventType, message)
    }
    
    EventListener <-- LogEventListener : estende
    EventManager "1" --o "0..*" LogEventListener : possui
````
## Facade
````mermaid
classDiagram
    class DatabaseManager {
        -DatabaseManager instance
        -DatabaseProvider provider
        -Connection connection
        -EventManager events

        -DatabaseManager(provider)
        +getInstance()
        +getInstance(provider)
        +getConnection()
        +closeConnection()
    }

    class LogManager {
        -LogManager instance
        -Logger logger
        
        -LogManager(logger)
        +getInstance()
        +getInstance(logger)
        +getLogger()
        +log(message)
    }
````
## Outros
````mermaid
classDiagram
    class App {
        +main(String[] args)
        +createTables(connection)
    }
    
    class NotificationScheduleGUI {
        -JTextField titleField
        -JTextArea messageArea
        -JSpinner dateTimeSpinner
        
        +NotificationScheduleGUI()
    }
````
# Diagramas de Sequência
## Builder
````mermaid
sequenceDiagram
    participant Main
    participant DatabaseManager
    participant SQLiteProvider.Builder
    participant SQLiteProvider

    Main->>+DatabaseManager: getInstance()
    DatabaseManager->>+SQLiteProvider.Builder: new SQLiteProvider.Builder()
    SQLiteProvider.Builder-->>-DatabaseManager: builder
    DatabaseManager->>+SQLiteProvider.Builder: url(url)
    SQLiteProvider.Builder-->>-DatabaseManager: builder
    DatabaseManager->>+SQLiteProvider.Builder: build()
    SQLiteProvider.Builder->>+SQLiteProvider: new SQLiteProvider(this)
    SQLiteProvider-->>-SQLiteProvider.Builder: provider
    SQLiteProvider.Builder-->>-DatabaseManager: provider
    DatabaseManager->>+DatabaseManager: getInstance(provider)
    DatabaseManager-->>-Main: instance
````
## Observer
````mermaid
sequenceDiagram
    participant Main
    participant DatabaseManager
    participant EventManager
    participant LogEventListener
    participant LogManager

    Main->>+DatabaseManager: getInstance()
    DatabaseManager->>+EventManager: new EventManager("connect","disconnect")
    EventManager-->>-DatabaseManager: events
    DatabaseManager-->>-Main: instance

    Main->>+LogManager: getInstance()
    LogManager-->>-Main: logManager
    Main->>+LogManager: getLogger()
    LogManager-->>-Main: logger
    Main->>+LogEventListener: new LogEventListener(logger)
    LogEventListener-->>-Main: logEventListener
    Main->>+DatabaseManager: registerSubscriber(logEventListener)
    DatabaseManager->>EventManager: subscribe("connect", logEventListener)
    DatabaseManager->>EventManager: subscribe("disconnect", logEventListener)

    Main-->+DatabaseManager: getConnection()
    DatabaseManager->>EventManager: notify("connect","Conectado ao banco")
    DatabaseManager->>-Main: connection
````