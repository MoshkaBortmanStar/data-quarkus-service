package submarine.com.model.dto

data class TableDto(
    val tableName: String,
    val schema: String,
    val columns: Map<String, String>,
)
