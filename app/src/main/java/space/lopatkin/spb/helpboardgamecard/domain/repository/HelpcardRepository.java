package space.lopatkin.spb.helpboardgamecard.domain.repository;

import androidx.lifecycle.LiveData;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

public interface HelpcardRepository {

    /**
     * Получить карточку памяти по идентификатору настольной игры.
     */
    LiveData<Helpcard> getHelpcard(int boardGameId);

}
