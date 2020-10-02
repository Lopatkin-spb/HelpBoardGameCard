//package space.lopatkin.spb.helpboardgamecard.ui.helpCard;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.ViewModel;
//import androidx.recyclerview.widget.RecyclerView;
//import space.lopatkin.spb.helpboardgamecard.R;
//
//public class HelpCardViewModel extends ViewModel {
//

//непонятное!!!!!!


//    private MutableLiveData<String> mText;
//
//
//    private RecyclerView recyclerView;
//
//
//    public HelpCardViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("тут хранится каталог памяток");
//
//        //список
//        recyclerView = findViewById(R.id.list);
//
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager (this, RecyclerView.VERTICAL, false);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        //разделитель
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//
//
//    }
//
//    public LiveData<String> getText() {
//        return mText;
//    }
//}