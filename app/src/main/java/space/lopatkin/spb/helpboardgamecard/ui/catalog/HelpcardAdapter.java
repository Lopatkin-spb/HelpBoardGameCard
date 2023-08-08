package space.lopatkin.spb.helpboardgamecard.ui.catalog;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

import java.util.ArrayList;
import java.util.List;

public class HelpcardAdapter extends RecyclerView.Adapter<HelpcardAdapter.HelpcardHolder> {

    private List<Helpcard> listHelpcards = new ArrayList<>(); //в будущем рассмотреть апгрейд на сортед лист

    private OnItemClickListener listenerView;
    private OnItemCheckboxListenerTest listenerCheckboxTest;
    private OnItemCheckboxListenerLock listenerCheckboxLock;

    //метод для создания холдера (обязательный)
    @NonNull
    @Override
    public HelpcardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(
                parent.getContext()).inflate(
                R.layout.item_card, parent, false);

        return new HelpcardHolder(itemView);
    }

    //установка значений в поле\ячейку из бд через адаптер
    @Override
    public void onBindViewHolder(@NonNull HelpcardHolder holder, final int position) {
        holder.bind(listHelpcards.get(position));

    }

    //обязательный метод ресайклера
    @Override
    public int getItemCount() {
        return listHelpcards.size();
    }


    //метод сет для массива карточек
    public void setListHelpcards(List<Helpcard> listHelpcards) {
        this.listHelpcards = listHelpcards;
        notifyDataSetChanged();
    }

    //uznaet poziciu dlia svaipa stirania
    public Helpcard getHelpcardAt(int position) {
        return listHelpcards.get(position);
    }


    //вся работа с ресайкл вью идет в адаптере (вставка и забор данных)
    class HelpcardHolder extends RecyclerView.ViewHolder {
        //перечисляем все видимые компоненты
        private TextView textViewTitle;
        private TextView textViewDescription;
        private CheckBox booleanViewFavorites;
        private CheckBox booleanViewLock;
        private TextView textViewPriority;

        //конструктор
        public HelpcardHolder(final View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_item_title);
            textViewDescription = itemView.findViewById(R.id.text_item_description);
            booleanViewFavorites = itemView.findViewById(R.id.action_item_favorites);
            booleanViewLock = itemView.findViewById(R.id.action_item_lock);
            textViewPriority = itemView.findViewById(R.id.text_item_priority);

            // общее вью  для показа карты
            //обработчик для всего итема сразу
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int adapterPosition = getAdapterPosition();
                    if (listenerView != null && adapterPosition != RecyclerView.NO_POSITION) {
                        listenerView.onItemClick(listHelpcards.get(adapterPosition));
                    }
                }
            });

        }

        //установка данных во вью
        public void bind(Helpcard helpcard) {
            textViewTitle.setText(helpcard.getTitle());
            if (helpcard.getDescription() != null && !helpcard.getDescription().isEmpty()) {
                textViewDescription.setText(helpcard.getDescription());
            }
            textViewPriority.setText(String.valueOf(helpcard.getPriority()));

            booleanViewFavorites.setOnCheckedChangeListener(null);
            booleanViewFavorites.setChecked((helpcard.isFavorites()));
            booleanViewFavorites.setOnCheckedChangeListener(onCheckedChangeListener);

            booleanViewLock.setOnCheckedChangeListener(null);
            booleanViewLock.setChecked((helpcard.isLock()));
            booleanViewLock.setOnCheckedChangeListener(onCheckedChangeListenerLock);
        }

        //обработчик на чекбокс Favorites
        private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int adapterPosition = getAdapterPosition();
                if (listenerCheckboxTest != null && adapterPosition != RecyclerView.NO_POSITION) {
                    listenerCheckboxTest.onItemCheckboxTest(listHelpcards.get(adapterPosition), b);
                }
            }
        };

        //обработчик на чекбокс Lock
        private CompoundButton.OnCheckedChangeListener onCheckedChangeListenerLock = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int adapterPosition = getAdapterPosition();

                if (listenerCheckboxLock != null && adapterPosition != RecyclerView.NO_POSITION) {
                    listenerCheckboxLock.onItemCheckboxLock(listHelpcards.get(adapterPosition), b);
                }
            }
        };

    }

    //для редактирования (? может просмотра?) существующей записи строки
    public interface OnItemClickListener {
        void onItemClick(Helpcard helpCard);
    }

    public void setOnItemClickListener(OnItemClickListener listenerView) {
        this.listenerView = listenerView;
    }


    //для редактирования чекбокса favorites
    public interface OnItemCheckboxListenerTest {
        void onItemCheckboxTest(Helpcard helpCard, boolean b);
    }

    public void setOnItemCheckboxListenerTest(OnItemCheckboxListenerTest listenerCheckboxTest) {
        this.listenerCheckboxTest = listenerCheckboxTest;
    }


    //для редактирования чекбокса lock
    public interface OnItemCheckboxListenerLock {
        void onItemCheckboxLock(Helpcard helpCard, boolean b);
    }

    public void setOnItemCheckboxListenerLock(OnItemCheckboxListenerLock listenerCheckboxLock) {
        this.listenerCheckboxLock = listenerCheckboxLock;
    }

}
