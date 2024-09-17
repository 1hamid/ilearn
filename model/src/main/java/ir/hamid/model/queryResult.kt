package ir.hamid.model

data class QueryResult(
    val word: String,
    val code: String,
    val pronunciation: String,
    val sample: String,
    val definition: String,
    val translate: String,
    val review: Int
)
