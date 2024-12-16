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
    SQLiteProvider --o Builder : possui
    MySQLProvider --o Builder : possui
    DatabaseProvider --|> SQLiteProvider : Herança
    DatabaseProvider --|> MySQLProvider : Herança
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
    
    NotificationHandler --|> SMTPNotification : Herança
    NotificationHandler --|> NtfyNotification : Herança
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
