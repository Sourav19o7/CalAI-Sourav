package domain

import com.google.gson.annotations.SerializedName


data class TaskResponse(
    val id: Int,
    val message: String,
    val data: List<TaskData> = emptyList(),
)

data class TaskData(
    val id: Int,
    @SerializedName("created_at") val createdAt: String,
    val title: String,
    val description: String?,
    val completed: Boolean,
    val timeline : String?,
    val assignee : String?,
)