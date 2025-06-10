package submarine.com.service

import jakarta.enterprise.context.ApplicationScoped
import submarine.com.model.dto.TableDto

@ApplicationScoped
class TableService {

    fun getName(tableDto: TableDto): String {
        val schema = tableDto.schema.ifEmpty { DEFAULT_SCHEMA }
        val tableName = tableDto.tableName.ifEmpty { throw IllegalArgumentException("Table name cannot be empty") }

        return "$schema.$tableName"
    }

    fun addPrimaryKeyConstraint(columns: MutableMap<String, String>): String {
        columns[SYS_ID] = "UUID PRIMARY KEY DEFAULT gen_random_uuid()"
        return ""
    }

    companion object {
        const val DEFAULT_SCHEMA = "public"
        const val SYS_ID = "sys_id"
    }
}
