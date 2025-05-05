package submarine.com.service

import io.agroal.api.AgroalDataSource
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import submarine.com.model.dto.TableDto

@ApplicationScoped
class CreateTableService @Inject constructor(private val agroalDataSource: AgroalDataSource) {

    fun createTable(tableDto: TableDto) {
        val schema = tableDto.schema.ifEmpty { DEFAULT_SCHEMA }
        val tableName = tableDto.tableName.ifEmpty { throw IllegalArgumentException("Table name cannot be empty") }
        val columns = tableDto.columns.ifEmpty { throw IllegalArgumentException("Columns cannot be empty") }

        val columnDefs = columns.entries.joinToString(", ") { (name, type) -> "$name $type" }
        val fullTableName = "$schema.$tableName"
        val sql = createTableSqlString(fullTableName, columnDefs)

        agroalDataSource.connection.use { conn ->
            conn.createStatement().use { stmt ->
                stmt.execute(sql)
            }
        }

        println("Executed SQL: $sql")
    }

    private fun createTableSqlString(tableName: String, columnDefs: String): String {
        return "CREATE TABLE IF NOT EXISTS $tableName ($columnDefs);"
    }

    companion object {
        const val DEFAULT_SCHEMA = "public"
    }

}