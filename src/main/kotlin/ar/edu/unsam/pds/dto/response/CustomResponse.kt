package ar.edu.unsam.pds.dto.response

data class CustomResponse(
    val message: String,
    val data: Any? = null,
)
