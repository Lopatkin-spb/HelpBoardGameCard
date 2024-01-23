package space.lopatkin.spb.helpboardgamecard.data.repository;

import androidx.lifecycle.LiveData;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;

import java.util.List;

public interface DatabaseRepository {


    /**
     * Получить карточку памяти по идентификатору настольной игры.
     */
    LiveData<Helpcard> getHelpcard(int boardGameId);

    /**
     * Получить все карточки памяти.
     */
    LiveData<List<Helpcard>> getAllHelpcards();

    /**
     * Удалить карточку памяти.
     */
    void delete(Helpcard helpcard);

    /**
     * Удалить карточку памяти.
     */
    void delete(int id);

    /**
     * Обновить карточку памяти.
     */
    void update(Helpcard helpcard);

    /**
     * Удалить все незаблокированные карточки памяти.
     */
    void deleteAllUnlockHelpcards();

    /**
     * Сохранить новую карточку памяти.
     */
    void saveNewHelpcard(Helpcard helpcard);

}