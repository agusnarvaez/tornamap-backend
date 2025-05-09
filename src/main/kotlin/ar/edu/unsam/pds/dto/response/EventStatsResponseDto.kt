package ar.edu.unsam.pds.dto.response

data class EventStatsResponseDto (
    val id: String,
    var isApproved: Boolean,
    val name: String,
    val status: String,
    val schedule: ScheduleResponseDto,
    val users : MutableSet<UserResponseDto>
)
