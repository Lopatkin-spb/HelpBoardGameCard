package space.lopatkin.spb.helpboardgamecard.domain.repository;

import androidx.lifecycle.LiveData;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

import java.util.List;

public interface HelpcardRepository {

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
     * Добавить новую карточку памяти.
     */
    void addNewHelpcard(Helpcard helpcard);

}
