````mermaid
classDiagram
    class DatabaseManager {
        -DatabaseManager instance
        -DatabaseProvider provider
        -DatabaseManager(provider)
        +getInstance()
        +getInstance(provider)
        +getConnection()
        +closeConnection()
    }
    
    class DatabaseProvider {
        <<interface>>
        -getConnection() Connection
        -closeConnection()
    }
    
    class SQLiteProvider {
        
    }
````