package models

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val employeeId: String)
