package space.lopatkin.spb.helpboardgamecard.ui;

//import android.support.v4.*;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
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
    private OnItemEditClickListener listenerEdit;
    private OnItemCheckboxListenerTest listenerCheckboxTest;
    private OnItemCheckboxListenerLock listenerCheckboxLock;


    //метод для создания холдера (обязательный)
    @NonNull
    @Override
    public HelpcardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(
                parent.getContext()).inflate(
                R.layout.helpcard_item, parent, false);

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


//    public Helpcard getHelpCardAtCheck(boolean b) {
//        return helpcards.
//    }


    // pervii varik
//    private SortedList<HelpCard> sortedList;
//
//    public AdapterRecyclerview() {
//
//        sortedList = new SortedList<>(HelpCard.class, new SortedList.Callback<HelpCard>() {
//            @Override
//            public int compare(HelpCard o1, HelpCard o2) {
//                if (!o2.favorites && o1.favorites) {
//                    return 1;
//                }
//                if (o2.favorites && !o1.favorites) {
//                    return -1;
//                }
//                return (int) (o2.timestamp - o1.timestamp);
//            }
//
//            //метод вызывается когда какойто елемент в позиции меняется
//            @Override
//            public void onChanged(int position, int count) {
//                notifyItemRangeChanged(position, count);
//            }
//
//            @Override
//            public boolean areContentsTheSame(HelpCard oldItem, HelpCard newItem) {
//                return oldItem.equals(newItem);
//            }
//
//            @Override
//            public boolean areItemsTheSame(HelpCard item1, HelpCard item2) {
//                return item1.uniqueId == item2.uniqueId;
//            }
//
//            @Override
//            public void onInserted(int position, int count) {
//                notifyItemRangeInserted(position, count);
//            }
//
//            @Override
//            public void onRemoved(int position, int count) {
//                notifyItemRangeRemoved(position, count);
//            }
//
//            @Override
//            public void onMoved(int fromPosition, int toPosition) {
//                notifyItemMoved(fromPosition, toPosition);
//            }
//        });
//    }
//
//    @NonNull
//    @Override
//    public HelpCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(
//                parent.getContext()).inflate(
//                R.layout.item_helpcard_list, parent, false);
//
//        return new HelpCardViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull HelpCardViewHolder holder, int position) {
//        holder.bind(sortedList.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return sortedList.size();
//    }
//
//    //обновление елементов списка
//// (найдет разницу в елементах и вызовет нужные методы для
//// обновления или замены только тех елементов которые нужны)
//    public void setItems(List<HelpCard> helpCards) {
//        sortedList.replaceAll(helpCards);
//    }


    //вся работа с ресайкл вью идет в адаптере (вставка и забор данных)
    class HelpcardHolder extends RecyclerView.ViewHolder {

        //перечисляем все видимые компоненты
        private TextView textViewTitle;
        private TextView textViewDescription;
        private CheckBox booleanViewFavorites;
        private CheckBox booleanViewLock;
        private ImageView imageViewEdit;
        private TextView textViewPriority;

        //конструктор
        public HelpcardHolder(final View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_item_title);
            textViewDescription = itemView.findViewById(R.id.text_view_item_description);
            booleanViewFavorites = itemView.findViewById(R.id.button_view_item_favorites);
            booleanViewLock = itemView.findViewById(R.id.checkbox_view_item_lock);
            imageViewEdit = itemView.findViewById(R.id.image_view_item_edit);
            textViewPriority = itemView.findViewById(R.id.text_view_item_priority);


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


            //обработчик для редактирования записи
            imageViewEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int adapterPosition = getAdapterPosition();
                    if (listenerEdit != null && adapterPosition != RecyclerView.NO_POSITION) {
                        listenerEdit.onItemEditClick(listHelpcards.get(adapterPosition));
                    }
                }
            });
        }

        //установка данных во вью
        public void bind(Helpcard helpcard) {
            textViewTitle.setText(helpcard.getTitle());
            textViewDescription.setText(helpcard.getDescription());
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


//        //конструктор
//        public HelpCardViewHolder(@NonNull final View itemView) {
//            super(itemView);
//
//
//            helpCardText = itemView.findViewById(R.id.helpcard_text);
//            complited = itemView.findViewById(R.id.complited);
//            delete = itemView.findViewById(R.id.delete);
//
////обработчик для всего итема сразу - вызов активити для редактирования заметки
//// (нестандартный способ, чтоб не возится с передачей данных)
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                   MainActivity mainActivity = new MainActivity();
////                    mainActivity.startNewcardFragment();
//
//                    // Context context = itemView.getContext(), helpCard;
//                    //itemView.getContext();
//
//         //NewCardFragment.start((Fragment) itemView.getContext(), helpCard);
//                }
//            });
//
//
//
//            complited.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
//                    if (!silentUpdate) {
//                        helpCard.favorites = checked;
//                        App.getInstance().getHelpCardDao().update(helpCard);
//                    }
//                    updateStrokeOut();
//                }
//            });
//
//        }
//
//        //зачеркивание записи
//        private void updateStrokeOut() {
//            if (helpCard.favorites) {
//                helpCardText.setPaintFlags(helpCardText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//            } else {
//                helpCardText.setPaintFlags(helpCardText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
//            }
//        }


    }


    //для редактирования существующей записи строки
    public interface OnItemClickListener {
        void onItemClick(Helpcard helpCard);
    }

    public void setOnItemClickListener(OnItemClickListener listenerView) {
        this.listenerView = listenerView;
    }


    //для редактирования существующей записи строки
    public interface OnItemEditClickListener {
        void onItemEditClick(Helpcard helpCard);
    }

    public void setOnItemEditClickListener(OnItemEditClickListener listenerEdit) {
        this.listenerEdit = listenerEdit;
    }


    //для редактирования чекбокса
    public interface OnItemCheckboxListenerTest {
        void onItemCheckboxTest(Helpcard helpCard, boolean b);
    }

    public void setOnItemCheckboxListenerTest(OnItemCheckboxListenerTest listenerCheckboxTest) {
        this.listenerCheckboxTest = listenerCheckboxTest;
    }



    //для редактирования чекбокса
    public interface OnItemCheckboxListenerLock {
        void onItemCheckboxLock(Helpcard helpCard, boolean b);
    }

    public void setOnItemCheckboxListenerLock(OnItemCheckboxListenerLock listenerCheckboxLock) {
        this.listenerCheckboxLock = listenerCheckboxLock;
    }


}
