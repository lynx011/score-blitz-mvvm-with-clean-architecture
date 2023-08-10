import com.lynx.scoreblitz.domain.model.SmModel.SmFixtureList

data class SmFixturesDTO(
    val `data`: List<SmFixtureListDTO>
)

data class SmFixtureListDTO(
    val aggregate_id: Any,
    val details: Any,
    val group_id: Any,
    val has_odds: Boolean,
    val id: Int,
    val league_id: Int,
    val leg: String,
    val length: Int,
    val name: String,
    val placeholder: Boolean,
    val result_info: String,
    val round_id: Int,
    val season_id: Int,
    val sport_id: Int,
    val stage_id: Int,
    val starting_at: String,
    val starting_at_timestamp: Int,
    val state_id: Int,
    val venue_id: Int
) {
    fun toSmFixtureList(): SmFixtureList {
        return SmFixtureList(
            aggregate_id,
            details,
            group_id,
            has_odds,
            id,
            league_id,
            leg,
            length,
            name,
            placeholder,
            result_info,
            round_id,
            season_id,
            sport_id,
            stage_id,
            starting_at,
            starting_at_timestamp,
            state_id,
            venue_id
        )
    }
}