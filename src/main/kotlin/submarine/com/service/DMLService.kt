package submarine.com.service

import io.agroal.api.AgroalDataSource
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import kotlin.math.round
import submarine.com.model.dto.TableDto

@ApplicationScoped
class DMLService @Inject constructor(
    private val agroalDataSource: AgroalDataSource,
    private val tableService: TableService
) {
    fun getAllRowsFromTable(tableDto: TableDto): TableDto {
        val listOfData = mutableListOf<Map<String, Any?>>()
        val tableName = tableService.getName(tableDto)
        val sql = "SELECT * FROM $tableName"
        agroalDataSource.connection.use { conn ->
            conn.createStatement().use { stmt ->
                stmt.executeQuery(sql).use { rs ->
                    val meta = rs.metaData
                    while (rs.next()) {
                        val row = mutableMapOf<String, Any?>()
                        for (i in 1..meta.columnCount) {
                            row[meta.getColumnName(i)] = rs.getObject(i)
                        }
                        listOfData.add(row)
                    }
                }
            }
        }
        tableDto.values = listOfData
        return tableDto
    }
}

