package submarine.com.resource

import jakarta.inject.Inject
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import submarine.com.model.dto.TableDto
import submarine.com.service.CreateTableService

@Path("/create")
class CreateResource @Inject constructor(private val createTableService: CreateTableService) {

    @POST
    @Path("/table")
    fun createTable(tableDto: TableDto) {
        val threadName = Thread.currentThread()
        println("Thread name: ${threadName.name}")
        // Logic to create a table
        println("Creating table: ${tableDto.tableName}")
        createTableService.createTable(tableDto)
    }
}