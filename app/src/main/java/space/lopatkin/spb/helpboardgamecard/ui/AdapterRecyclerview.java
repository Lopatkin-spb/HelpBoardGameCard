package space.lopatkin.spb.helpboardgamecard.ui;

//import android.support.v4.*;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

import java.util.ArrayList;
import java.util.List;

public class AdapterRecyclerview extends RecyclerView.Adapter<AdapterRecyclerview.HelpCardViewHolder> {

    private List<Helpcard> list = new ArrayList<>();
    private OnItemClickListener listener;


    @NonNull
    @Override
    public HelpCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(
                parent.getContext()).inflate(
                R.layout.item_helpcard_list, parent, false);

        return new HelpCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpCardViewHolder holder, int position) {
        Helpcard currentHelpcard = list.get(position);

        holder.helpCardText.setText(currentHelpcard.getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setCards(List<Helpcard> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    public Helpcard getHelpCardAt (int position) {
        return list.get(position);
    }


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


     class HelpCardViewHolder extends RecyclerView.ViewHolder {


        private TextView helpCardText;

        //конструктор
        public HelpCardViewHolder(@NonNull final View itemView) {
            super(itemView);
            helpCardText = itemView.findViewById(R.id.helpcard_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(list.get(position));
                    }

                }
            });
        }





//        TextView helpCardText;
//        CheckBox complited;
//        View delete;
//
//        HelpCard helpCard;
//
//        boolean silentUpdate;
//
//
//
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
//
//                    Navigation.findNavController(view).navigate(R.id.action_nav_helpcard_to_nav_newcard);
//
//
//                    //NewCardFragment.start((Fragment) itemView.getContext(), helpCard);
//                }
//            });
//
//
//            delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    App.getInstance().getHelpCardDao().delete(helpCard);
//                }
//            });
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
//        public void bind(HelpCard helpCard) {
//            this.helpCard = helpCard;
//
//            helpCardText.setText(helpCard.text);
//            updateStrokeOut();
//
//            silentUpdate = true;
//            complited.setChecked(helpCard.favorites);
//            silentUpdate = false;
//
//        }
//
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



    public interface OnItemClickListener {
        void onItemClick (Helpcard helpCard);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
