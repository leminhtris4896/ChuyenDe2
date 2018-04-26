package com.example.trile.foodlocation;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.trile.foodlocation.Adapter.AdapterPlace;
import com.example.trile.foodlocation.Adapter.AdapterPost;
import com.example.trile.foodlocation.Models.mdBusiness;
import com.example.trile.foodlocation.Models.mdPost;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


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

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        recyclerBusiness = (RecyclerView) view.findViewById(R.id.recyclerView_Business);
        recyclerPost = (RecyclerView) view.findViewById(R.id.recyclerView_Post);

        final CheckBox cbx_unlike = (CheckBox) view.findViewById(R.id.cbx_unlike);
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
        /*ArrayList<mdBusiness> arrProductPlace = new ArrayList<mdBusiness>();

        arrProductPlace.add(new mdBusiness("","","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/img_cafe.jpg?alt=media&token=3e0f0894-b78b-4109-a2a1-0882df2675b8","Napoli","0908668620","74/2/6 Linh Đông , Thủ Đức","Quán nước","07h00 - 22h00","4.5","",""));
        arrProductPlace.add(new mdBusiness("","","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/img_gongcha.jpg?alt=media&token=9a3209fa-c90b-4a0d-878d-43231b561628","Apola","0908668620","74/2/6 Linh Đông , Thủ Đức","Quán nước","07h00 - 22h00","4","",""));
        arrProductPlace.add(new mdBusiness("","","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/img_lau.jpg?alt=media&token=529767db-3fcc-4ba3-9bd8-3cc79eedd3a7","Lẩu Giấy","0908668620","74/2/6 Linh Đông , Thủ Đức","Quán ăn","07h00 - 22h00","4","",""));
        arrProductPlace.add(new mdBusiness("","","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/img_nuong.jpg?alt=media&token=2245dbdb-e671-49d0-b766-f6309c9a303c","Set Nướng","0908668620","74/2/6 Linh Đông , Thủ Đức","Quán ăn","07h00 - 22h00","3.5","",""));
        arrProductPlace.add(new mdBusiness("","","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/img_trasua.jpg?alt=media&token=b4dfc766-4840-4976-a65c-ab1f07e47d1a","Apache","0908668620","74/2/6 Linh Đông , Thủ Đức","Quán nước","07h00 - 22h00","4","",""));
        arrProductPlace.add(new mdBusiness("","","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/img_nhau1.JPG?alt=media&token=9cfb41fc-afa4-4343-a8cc-2c89b636ee94","O2 Quán","0908668620","74/2/6 Linh Đông , Thủ Đức","Quán nước","07h00 - 22h00","4","",""));
        arrProductPlace.add(new mdBusiness("","","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/img_nuong.jpg?alt=media&token=2245dbdb-e671-49d0-b766-f6309c9a303c","Khram 2e","0908668620","74/2/6 Linh Đông , Thủ Đức","Quán nước","07h00 - 22h00","4","",""));
        arrProductPlace.add(new mdBusiness("","","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/banh-mi-tho-nhy-ki.jpg?alt=media&token=b52f8b98-fafb-4f6b-822c-adfd6af05538","Bánh Mỳ Thổ Nhỹ Kỳ Kebab Tiếp Cruise","097 744 66 86","309/93 Võ Văn Ngân, P. Linh Chiểu, Quận Thủ Đức","Quán ăn","09:00 - 21:00","4","",""));
        databaseReference.child("Business").setValue(arrProductPlace);
        databaseReference.child("Business").setValue(arrProductPlace);*/
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
                arrBusiness.add(new mdBusiness(mdBusiness.getStrID(),mdBusiness.getStrEmail(), mdBusiness.getStrPass(), mdBusiness.getStrImage(), mdBusiness.getStrName(), mdBusiness.getStrPhone(), mdBusiness.getStrAddress(), mdBusiness.getStrBusinessType(), mdBusiness.getStrOpenTime(), mdBusiness.getStrScoreRating(), mdBusiness.getStrListProductList(), mdBusiness.getStrListCommentList(),mdBusiness.getDbLatitude(),mdBusiness.getDbLongitude()));
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
                arrPost.add(new mdPost(mdPost.getPostID(), mdPost.getNameProduct(), mdPost.getDescriptionProduct(), mdPost.getImgProduct(), mdPost.getnNumberLike(), mdPost.getnNumberUnlike(), mdPost.getnNumberComment(), mdPost.isCheckLike(), mdPost.isCheckUnLike(), mdPost.getLienKetDiaDiem(), mdPost.getArrayListCommentPost()));
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

        recyclerBusiness.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerBusiness, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent myIntent = new Intent(getActivity().getBaseContext(), BusinessDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("detailBusiness", arrBusiness.get(position).getStrName());
                myIntent.putExtra("bundle", bundle);
                getActivity().startActivity(myIntent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                // do whatever
            }
        }
        ));

        scrollByTime();

        return view;
    }


}
