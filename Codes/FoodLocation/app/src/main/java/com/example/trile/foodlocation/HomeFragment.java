package com.example.trile.foodlocation;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        recyclerBusiness = (RecyclerView) view.findViewById(R.id.recyclerView_Business);
        recyclerPost = (RecyclerView) view.findViewById(R.id.recyclerView_Post);

        /*mdProductHome vuaocPost = new mdProductHome("Giới thiệu quán Vua ốc","Nhiều người vẫn hay nói đùa rằng đến Sài Gòn mà không một lần được ăn ốc thì coi như chưa đến Sài Gòn. \n" +
                "Nói vậy để thấy rằng ốc đối với người Sài Gòn như một thức quà hấp dẫn và cũng không kém phần đặc biệt.\n" +
                " Thế nhưng những loại ốc ở siêu thị ốc vua ốc còn đặc biệt hơn nữa khi ở đây mang tới cho thực khách hàng\n" +
                " loạt loại ốc lạ lẫm, hiếm có như ốc trinh nữ, ốc chung tình, ốc xối xả, ốc tê tái, nghêu tình nhân, ốc cổ đại, ốc vú nàng,\n" +
                " ốc sung, ốc chúa, ốc heo, ốc tai tượng, ốc bàn tay, ốc mặt trăng, ốc núi, ốc cầu gai, ốc móng chân, ốc ma nữ,…\n" +
                " Ấn tượng ngay từ những cái tên, siêu thị ốc vua ốc đã đủ hấp dẫn bạn chưa?","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/sieuthiocvuaoc_post.jpg?alt=media&token=7b2b6e63-4766-450b-9206-69f838d611b5","40","3","3");
        databaseReference.child("Post").push().setValue(vuaocPost);*/
        /*mdProductHome nuongbiadaomappost = new mdProductHome("Giới thiệu quán Nướng và bia đạo mập 2","Là một trong những quán nhậu nổi tiếng ở Thủ Đức với rất nhiều món nướng hấp dẫn cùng bia tươi mát lạnh lúc nào cũng sẵn sàng. Có thể kể tên một số món nướng tại đây được nhiều người ưu thích như chân gà, sụn gà, mề gà, răng mực, lưỡi vịt, thịt nai, thịt đà điểu, heo rừng, bò, bạch tuộc, tôm nướng,… Điều đặc biệt nhất của quán là thời gian mở cừa từ 15h đến tận nửa đêm nên bạn có thể thoải mái ngồi lai rai, hàn huyên cùng bạn bè mà không lo cuộc vui vị lỡ dở.","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/nuongvabiadaomap2_post.jpg?alt=media&token=7e63a545-508a-48a3-8312-04a4a89d429a","40","3","3");
        databaseReference.child("Post").push().setValue(nuongbiadaomappost);
        mdProductHome giangQuanPost = new mdProductHome("Giới thiệu Quán hải sản Giang ghẹ","Cũng là một trong những quán nướng đặc biệt đông khách tại Thủ Đức, thế nhưng Giang quán tập trung vào những món nướng hải sản rất hấp dẫn. Sò huyết, tôm nướng muối ớt, mực nướng sa tế, cá, lươn, tôm, hàu nướng phô mai,… tất cả đều được chế biến ngay tại bàn khiến những cái bụng đói meo phải sôi sục suốt cả bữa ăn. Một số loại lẩu của quán cũng được phản hồi rất tốt như lẩu cá lăng hay lẩu măng chua,…","https://firebasestorage.googleapis.com/v0/b/reviewfoodver10.appspot.com/o/giangquan_post.jpg?alt=media&token=1e345b69-506e-4e02-b2be-1575afdfc09b","40","3","3");
        databaseReference.child("Post").push().setValue(giangQuanPost);*/
        // RecyclerView 1
        CustomLinearLayout layoutManagertop = new CustomLinearLayout(getActivity(), 500);
        layoutManagertop.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerBusiness.setHasFixedSize(true);
        recyclerBusiness.setLayoutManager(layoutManagertop);

        // RecyclerView 2
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerPost.setHasFixedSize(true);
        recyclerPost.setLayoutManager(layoutManager);


        // Array of AdapterPlace

        /*arrBusiness = new ArrayList<mdTopLocation>();
        arrBusiness.add(new mdTopLocation(R.mipmap.trasua,"Trà Sữa Apa","44D/1B Ấp Đồng An , ..."));
        arrBusiness.add(new mdTopLocation(R.mipmap.cafe,"Cà phê Napoli","66A Chương Dương , ..."));
        arrBusiness.add(new mdTopLocation(R.mipmap.lau,"Lẩu thái Khamge","74/2/6 Linh Đông , ..."));
        arrBusiness.add(new mdTopLocation(R.mipmap.trasua,"Trà Sữa Apa","44D/1B Ấp Đồng An , ..."));
        arrBusiness.add(new mdTopLocation(R.mipmap.cafe,"Cà phê Napoli","66A Chương Dương , ..."));
        arrBusiness.add(new mdTopLocation(R.mipmap.lau,"Lẩu thái Khamge","74/2/6 Linh Đông , ..."));*/

        // Array of HomeProduct
        arrPost = new ArrayList<mdPost>();
        /*arrPost.add(new mdProductHome("Trà Sữa Socola","Rất béo ngậy , rất thơm ngon",R.mipmap.trasua,"12","13","100"));
        arrPost.add(new mdProductHome("Lẩu thái","Rất béo ngậy , rất thơm ngon",R.mipmap.lau,"12","13","100"));
        arrPost.add(new mdProductHome("Cà phê nóng","Rất béo ngậy , rất thơm ngon",R.mipmap.cafe,"12","13","100"));*/

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
                arrPost.add(new mdPost(mdPost.getNameProduct(), mdPost.getDescriptionProduct(), mdPost.getImgProduct(), mdPost.getnNumberLike(), mdPost.getnNumberUnlike(), mdPost.getnNumberComment()));
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
        // sự kiện click bài post
        recyclerPost.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerPost, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent myIntent = new Intent(getActivity().getBaseContext(),PostDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("detailPost", arrPost.get(position).getNameProduct());
                myIntent.putExtra("bundle", bundle);
                getActivity().startActivity(myIntent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                // do whatever
            }
        }
        ));
        // sự kiện click địa điểm
        recyclerBusiness.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerBusiness, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent myIntent = new Intent(getActivity().getBaseContext(),BusinessDetailActivity.class);
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
