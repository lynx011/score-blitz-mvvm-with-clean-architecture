import com.lynx.scoreblitz.domain.model.SmModel.SmLeagueList

data class SmLeaguesDTO(
    val `data`: List<SmLeagueListDTO>
)

data class SmLeagueListDTO(
    val active: Boolean,
    val category: Int,
    val country_id: Int,
    val has_jerseys: Boolean,
    val id: Int,
    val image_path: String,
    val last_played_at: String,
    val name: String,
    val short_code: String,
    val sport_id: Int,
    val sub_type: String,
    val type: String
) {
    fun toSmLeagueList(): SmLeagueList {
        return SmLeagueList(
            active,
            category,
            country_id,
            has_jerseys,
            id,
            image_path,
            last_played_at,
            name,
            short_code,
            sport_id,
            sub_type,
            type
        )
    }
}