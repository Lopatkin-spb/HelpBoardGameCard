package space.lopatkin.spb.helpboardgamecard.ui;

import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;
import space.lopatkin.spb.helpboardgamecard.App;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.model.HelpCard;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.HelpCardViewHolder> {


    private SortedList<HelpCard> sortedList;

    public Adapter() {

        sortedList = new SortedList<>(HelpCard.class, new SortedList.Callback<HelpCard>() {
            @Override
            public int compare(HelpCard o1, HelpCard o2) {
                if (!o2.favorites && o1.favorites) {
                    return 1;
                }
                if (o2.favorites && !o1.favorites) {
                    return -1;
                }
                return (int) (o2.timestamp - o1.timestamp);
            }

            //метод вызывается когда какойто елемент в позиции меняется
            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(HelpCard oldItem, HelpCard newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(HelpCard item1, HelpCard item2) {
                return item1.uniqueId == item2.uniqueId;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    @NonNull
    @Override
    public HelpCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HelpCardViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_helpcard_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HelpCardViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }


    public void setItems(List<HelpCard> helpCards) {
        sortedList.replaceAll(helpCards);
    }


    static class HelpCardViewHolder extends RecyclerView.ViewHolder {

        TextView helpCardText;
        CheckBox complited;
        View delete;

        HelpCard helpCard;

        boolean silentUpdate;

        //конструктор
        public HelpCardViewHolder(@NonNull final View itemView) {
            super(itemView);

            helpCardText = itemView.findViewById(R.id.helpcard_text);
            complited = itemView.findViewById(R.id.complited);
            delete = itemView.findViewById(R.id.delete);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HelpCardDetailsActivity.start((Activity) itemView.getContext(), helpCard);
                }
            });


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    App.getInstance().getHelpCardDao().delete(helpCard);
                }
            });

            complited.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (!silentUpdate) {
                        helpCard.favorites = checked;
                        App.getInstance().getHelpCardDao().update(helpCard);
                    }
                    updateStrokeOut();
                }
            });

        }

        public void bind(HelpCard helpCard) {
            this.helpCard = helpCard;

            helpCardText.setText(helpCard.text);
            updateStrokeOut();

            silentUpdate = true;
            complited.setChecked(helpCard.favorites);
            silentUpdate = false;

        }


        //зачеркивание записи
        private void updateStrokeOut() {
            if (helpCard.favorites) {
                helpCardText.setPaintFlags(helpCardText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                helpCardText.setPaintFlags(helpCardText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }


    }


}
