package space.lopatkin.spb.helpboardgamecard.domain.model

data class BoardgameRaw(
    val name: String?,
    val description: String?,
    val victoryCondition: String?,
    val playerTurn: String?,
    val endGame: String?,
    val effects: String?,
    val preparation: String?,
    val priority: Int?
) {

    var favorite: Boolean? = null
    var lock: Boolean? = null
    var author: String? = null
    var id: Long? = null

    constructor(
        name: String?,
        description: String?,
        victoryCondition: String?,
        playerTurn: String?,
        endGame: String?,
        effects: String?,
        preparation: String?,
        priority: Int?,
        favorite: Boolean?,
        lock: Boolean?,
        author: String?,
    ) : this(
        name, description, victoryCondition, playerTurn, endGame, effects, preparation, priority,
    ) {
        this.favorite = favorite
        this.lock = lock
        this.author = author
    }

    constructor(
        name: String?,
        description: String?,
        victoryCondition: String?,
        playerTurn: String?,
        endGame: String?,
        effects: String?,
        preparation: String?,
        priority: Int?,
        favorite: Boolean?,
        lock: Boolean?,
        author: String?,
        id: Long?
    ) : this(
        name, description, victoryCondition, playerTurn, endGame, effects, preparation, priority, favorite, lock, author
    ) {
        this.id = id
    }

    fun toBoardgameInfo(): BoardgameInfo {
        return BoardgameInfo(
            boardgameId = id,
            boardgameName = name ?: "",
            boardgameDescription = description ?: "",
            boardgameFavorite = favorite ?: false,
            boardgameLock = lock ?: false,
            boardgamePriority = priority ?: 1,
            helpcardAuthor = author ?: ""
        )
    }

    fun toHelpcard(boardgameId: Long): Helpcard {
        return Helpcard(
            helpcardId = null,
            boardgameId = boardgameId,
            boardgameName = name ?: "",
            helpcardAuthor = author ?: "",
            helpcardVictoryCondition = victoryCondition ?: "",
            helpcardPlayerTurn = playerTurn ?: "",
            helpcardEndGame = endGame ?: "",
            helpcardEffects = effects ?: "",
            helpcardPreparation = preparation ?: "",
            boardgamePriority = priority ?: 1
        )
    }

    fun toHelpcard(helpcardId: Long, boardgameId: Long): Helpcard {
        return Helpcard(
            helpcardId = helpcardId,
            boardgameId = boardgameId,
            boardgameName = name ?: "",
            helpcardAuthor = author ?: "",
            helpcardVictoryCondition = victoryCondition ?: "",
            helpcardPlayerTurn = playerTurn ?: "",
            helpcardEndGame = endGame ?: "",
            helpcardEffects = effects ?: "",
            helpcardPreparation = preparation ?: "",
            boardgamePriority = priority ?: 1
        )
    }

    override fun toString(): String {
        return "BoardgameRaw(name=$name, description=$description, victoryCondition=$victoryCondition, playerTurn=$playerTurn, endGame=$endGame, effects=$effects, preparation=$preparation, priority=$priority, favorite=$favorite, lock=$lock, author=$author, id=$id)"
    }

}
