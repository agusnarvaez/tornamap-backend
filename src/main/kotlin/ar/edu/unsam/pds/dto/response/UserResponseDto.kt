package ar.edu.unsam.pds.dto.response

data class UserResponseDto(
    val name: String,
    val lastName: String,
    val email: String,
    val image: String,
    val id: String,
    val isAdmin: Boolean,
)