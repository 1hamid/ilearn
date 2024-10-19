package ir.hamid.model

data class QueryResult(
    val id: Int,
    val word: String,
    val code: String,
    val pronunciation: String,
    val sample: String,
    val definition: String,
    val translate: String,
)
