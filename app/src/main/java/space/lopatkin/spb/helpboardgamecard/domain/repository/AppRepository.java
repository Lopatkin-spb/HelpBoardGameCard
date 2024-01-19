package space.lopatkin.spb.helpboardgamecard.domain.repository;

import androidx.lifecycle.LiveData;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;

import java.util.List;

public interface AppRepository {

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

    /**
     * Сохранить тип включенной клавиатуры в настройках: кастомная или дефолтная.
     *
     * @param type выбранный пользователем тип.
     */
    void saveKeyboardType(int type);

    /**
     * Получить тип включенной клавиатуры в настройках: кастомная или дефолтная.
     */
    int getKeyboardType();

}
