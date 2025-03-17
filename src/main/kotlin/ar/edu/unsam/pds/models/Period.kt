package ar.edu.unsam.pds.models

import jakarta.persistence.*
import java.io.Serializable
import java.time.LocalDate
import java.util.*

@Entity @Table(name = "APP_PERIOD")
class Period(
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
): Timestamp(),Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID
}