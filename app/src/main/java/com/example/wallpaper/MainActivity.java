package com.example.wallpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wallpaper.api.ApiUtilities;
import com.example.wallpaper.model.ImageModel;
import com.example.wallpaper.model.SearchModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ImageModel> list;
    private GridLayoutManager manager;
    private ImageAdapter adapter;
    private int page = 1;
    private ProgressDialog dialog;

    private Button islam,nature,sports,tech,art,business,beauty,biotech,sky;

    private int searchPageSize = 5;
    private int pageSize = 30;
    private boolean isLoading;
    private boolean isLastPage;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        islam = findViewById(R.id.islam);
        nature = findViewById(R.id.nature);
        sports = findViewById(R.id.sports);
        tech = findViewById(R.id.tech);
        art = findViewById(R.id.art);
        business = findViewById(R.id.business);
        beauty = findViewById(R.id.beauty);
        biotech = findViewById(R.id.biotech);
        sky = findViewById(R.id.sky);

        recyclerView = findViewById(R.id.recyclerView);
        list = new ArrayList<>();
        adapter = new ImageAdapter(this,list);
        manager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        dialog  = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();


        getData();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItem = manager.getChildCount();
                int totalItem = manager.getItemCount();
                int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();

                if(!isLoading && !isLastPage){
                    if((visibleItem+firstVisibleItemPosition>=totalItem)
                    && firstVisibleItemPosition >=0 &&
                    totalItem >= pageSize){
                        page ++;
                        getData();

                    }
                }
            }
        });
        islam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData("islam");
            }
        });
        nature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData("nature");
            }
        });
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData("sports");
            }
        });
        tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData("technology");
            }
        });
        art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData("art");
            }
        });
        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData("business");
            }
        });
        beauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData("beauty");
            }
        });
        biotech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData("biotechnology");
            }
        });
        sky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData("sky");
            }
        });


    }

    private void getData() {
        isLoading = true;
        ApiUtilities.getApiInterface().getImages(page,30)
                .enqueue(new Callback<List<ImageModel>>() {@Override
                    public void onResponse(Call<List<ImageModel>> call, Response<List<ImageModel>> response) {
                        if (response.body() != null){
                            list.addAll(response.body());
                            adapter.notifyDataSetChanged();

                        }
                        isLoading = false;
                        dialog.dismiss();

                        if(list.size()>0){
                            isLastPage = list.size() < pageSize;
                        }else {
                            isLastPage = true;
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ImageModel>> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView =(SearchView) search.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.show();
                searchData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    private void searchData(String query) {
        dialog.dismiss();
        ApiUtilities.getApiInterface().searchImages(query)
                .enqueue(new Callback<SearchModel>() {
                    @Override

                    public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                        try {
                            list.clear();
                            list.addAll(response.body().getResults());
                            adapter.notifyDataSetChanged();
                        }catch (Exception e){

                        }

                    }

                    @Override
                    public void onFailure(Call<SearchModel> call, Throwable t) {

                    }
                });
    }


}