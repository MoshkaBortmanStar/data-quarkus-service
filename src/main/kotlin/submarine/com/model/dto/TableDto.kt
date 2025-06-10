package submarine.com.model.dto

data class TableDto(
    val tableName: String,
    val schema: String,
    val columns: MutableMap<String, String>,
    var values: List<Map<String, Any?>>?
)
