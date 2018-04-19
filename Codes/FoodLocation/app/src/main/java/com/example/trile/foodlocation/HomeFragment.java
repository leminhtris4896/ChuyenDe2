package com.example.trile.foodlocation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.trile.foodlocation.Adapter.AdapterPlace;
import com.example.trile.foodlocation.Adapter.AdapterPost;
import com.example.trile.foodlocation.Models.mdBusiness;
import com.example.trile.foodlocation.Models.mdComment;
import com.example.trile.foodlocation.Models.mdPost;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    // Array of AdapterPlace
    private ArrayList<mdBusiness> arrBusiness;
    private AdapterPlace adapterBusiness;
    private RecyclerView recyclerBusiness;
    // Array of HomeProduct
    private ArrayList<mdPost> arrPost;
    private AdapterPost adapterPost;
    private RecyclerView recyclerPost;
    // Firebase
    DatabaseReference databaseReference;


    public HomeFragment() {
        // Required empty public constructor
    }

    private int position = 0;

    private void scrollByTime() {
        final android.os.Handler handler = new android.os.Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                position++;
                if (position >= arrBusiness.size()) {
                    position = 0;
                }
                recyclerBusiness.smoothScrollToPosition(position);
                handler.postDelayed(this, 3000);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_layout, container, false);

        recyclerBusiness = (RecyclerView) view.findViewById(R.id.recyclerView_Business);
        recyclerPost = (RecyclerView) view.findViewById(R.id.recyclerView_Post);

        final CheckBox cbx_unlike = (CheckBox)  view.findViewById(R.id.cbx_unlike);
     /* ArrayList<mdComment> arrayListCommmentPost = new ArrayList<>();
        mdComment cm1 = new mdComment("https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/badge.png?alt=media&token=d0362dde-7ddc-43f6-b480-0ad3aaa554d9", "mon1 kha1 ngon");
        mdComment cm2 = new mdComment("https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/badge.png?alt=media&token=d0362dde-7ddc-43f6-b480-0ad3aaa554d9", "mon1 kha1 ngon");
        arrayListCommmentPost.add(cm1);
        arrayListCommmentPost.add(cm2);
        ArrayList<mdPost> mdPostArrayList = new ArrayList<>();
        mdPostArrayList.add(new mdPost("0","Giới thiệu quán Vua ốc", "Nhiều người vẫn hay nói đùa rằng đến Sài Gòn mà không một lần được ăn ốc thì coi như chưa đến Sài Gòn. \n" +
                "Nói vậy để thấy rằng ốc đối với người Sài Gòn như một thức quà hấp dẫn và cũng không kém phần đặc biệt.\n" +
                " Thế nhưng những loại ốc ở siêu thị ốc vua ốc còn đặc biệt hơn nữa khi ở đây mang tới cho thực khách hàng\n" +
                " loạt loại ốc lạ lẫm, hiếm có như ốc trinh nữ, ốc chung tình, ốc xối xả, ốc tê tái, nghêu tình nhân, ốc cổ đại, ốc vú nàng,\n" +
                " ốc sung, ốc chúa, ốc heo, ốc tai tượng, ốc bàn tay, ốc mặt trăng, ốc núi, ốc cầu gai, ốc móng chân, ốc ma nữ,…\n" +
                " Ấn tượng ngay từ những cái tên, siêu thị ốc vua ốc đã đủ hấp dẫn bạn chưa?", "https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/sieuthiocvuaoc_post.jpg?alt=media&token=7b2b6e63-4766-450b-9206-69f838d611b5", "40", "3", "3", false, true, "Khram 2e", arrayListCommmentPost));
       mdPostArrayList.add(new mdPost("1","Giới thiệu quán Nướng và bia đạo mập 2", "Là một trong những quán nhậu nổi tiếng ở Thủ Đức với rất nhiều món nướng hấp dẫn cùng bia tươi mát lạnh lúc nào cũng sẵn sàng. Có thể kể tên một số món nướng tại đây được nhiều người ưu thích như chân gà, sụn gà, mề gà, răng mực, lưỡi vịt, thịt nai, thịt đà điểu, heo rừng, bò, bạch tuộc, tôm nướng,… Điều đặc biệt nhất của quán là thời gian mở cừa từ 15h đến tận nửa đêm nên bạn có thể thoải mái ngồi lai rai, hàn huyên cùng bạn bè mà không lo cuộc vui vị lỡ dở.", "https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/nuongvabiadaomap2_post.jpg?alt=media&token=7e63a545-508a-48a3-8312-04a4a89d429a", "40", "3", "3", false, true, "Khram 2e", arrayListCommmentPost));
       mdPostArrayList.add(new mdPost("2","Giới thiệu Quán hải sản Giang ghẹ", "Cũng là một trong những quán nướng đặc biệt đông khách tại Thủ Đức, thế nhưng Giang quán tập trung vào những món nướng hải sản rất hấp dẫn. Sò huyết, tôm nướng muối ớt, mực nướng sa tế, cá, lươn, tôm, hàu nướng phô mai,… tất cả đều được chế biến ngay tại bàn khiến những cái bụng đói meo phải sôi sục suốt cả bữa ăn. Một số loại lẩu của quán cũng được phản hồi rất tốt như lẩu cá lăng hay lẩu măng chua,…", "https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/giangquan_post.jpg?alt=media&token=1e345b69-506e-4e02-b2be-1575afdfc09b", "40", "3", "3", false, true, "Khram 2e", arrayListCommmentPost));
        databaseReference.child("Post").setValue(mdPostArrayList);*/
        // RecyclerView 1
        CustomLinearLayout layoutManagertop = new CustomLinearLayout(getActivity(), 500);
        layoutManagertop.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerBusiness.setHasFixedSize(true);
        recyclerBusiness.setLayoutManager(layoutManagertop);

        // RecyclerView 2
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerPost.setHasFixedSize(true);
        recyclerPost.setLayoutManager(layoutManager);



        // Array of HomeProduct
        arrPost = new ArrayList<mdPost>();

        // Adapter of AdapterPlace
        arrBusiness = new ArrayList<mdBusiness>();
        adapterBusiness = new AdapterPlace(arrBusiness, getContext());
        recyclerBusiness.setAdapter(adapterBusiness);
        recyclerBusiness.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());
        databaseReference.child("Business").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final mdBusiness mdBusiness = dataSnapshot.getValue(mdBusiness.class);
                arrBusiness.add(new mdBusiness(mdBusiness.getStrEmail(), mdBusiness.getStrPass(), mdBusiness.getStrImage(), mdBusiness.getStrName(), mdBusiness.getStrPhone(), mdBusiness.getStrAddress(), mdBusiness.getStrBusinessType(), mdBusiness.getStrOpenTime(), mdBusiness.getStrScoreRating(), mdBusiness.getStrListProductList(), mdBusiness.getStrListCommentList()));
                adapterBusiness.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Adapter of HomeProduct
        arrPost = new ArrayList<mdPost>();
        adapterPost = new AdapterPost(arrPost, getContext());
        recyclerPost.setAdapter(adapterPost);
        recyclerPost.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());
        databaseReference.child("Post").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final mdPost mdPost = dataSnapshot.getValue(mdPost.class);
                arrPost.add(new mdPost(mdPost.getPostID(),mdPost.getNameProduct(), mdPost.getDescriptionProduct(), mdPost.getImgProduct(), mdPost.getnNumberLike(), mdPost.getnNumberUnlike(), mdPost.getnNumberComment(), mdPost.isCheckLike(), mdPost.isCheckUnLike(), mdPost.getLienKetDiaDiem(), mdPost.getArrayListCommentPost()));
                adapterPost.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        scrollByTime();

        return view;
    }



}
