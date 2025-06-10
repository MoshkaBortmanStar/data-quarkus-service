package submarine.com.resource

import jakarta.inject.Inject
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import mu.KLogging
import submarine.com.model.dto.TableDto
import submarine.com.service.DMLService

@Path("/data")
class GetDataResource @Inject constructor(private val dmlService: DMLService) {

    @POST
    @Path("/get-all")
    @Produces(MediaType.APPLICATION_JSON)
    fun getData(tableDto: TableDto): TableDto {
        logger.info { "Getting data from table: ${tableDto.tableName}" }
        return dmlService.getAllRowsFromTable(tableDto)
    }

    companion object : KLogging()
}
