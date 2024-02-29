package space.lopatkin.spb.helpboardgamecard.domain.model

sealed class Completable {

    data class onComplete<out T>(val data: T) : Completable()

}