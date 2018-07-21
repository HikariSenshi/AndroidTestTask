package com.testtask.applicationa.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.testtask.applicationa.R;
import com.testtask.applicationa.entity.ImageModel;
import com.testtask.applicationa.model.interactor.HistoryInteractor;
import com.testtask.applicationa.model.repository.HistoryRepository;
import com.testtask.applicationa.presentation.history.HistoryPresenter;
import com.testtask.applicationa.presentation.history.HistoryView;
import com.testtask.applicationa.ui.adapters.HistoryRecyclerAdapter;
import com.testtask.applicationa.ui.adapters.OnItemClickListener;

import java.util.List;

import static com.testtask.applicationa.utils.Consts.INTENT_KEY;
import static com.testtask.applicationa.utils.Consts.IMAGE_STATUS;

public class HistoryFragment extends Fragment implements HistoryView{

    View view;
    TextView emptyText;
    RecyclerView recyclerView;
    HistoryRecyclerAdapter adapter;

    HistoryPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        // init presenter for one time to avoid its second init in onActivityCreated
        if(presenter == null){
            presenter = new HistoryPresenter(this, new HistoryInteractor(new HistoryRepository(getActivity())));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.history_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //init views
        emptyText = view.findViewById(R.id.history_empty_text);
        recyclerView = view.findViewById(R.id.history_rv);

        //create adapter and some stuff for recycler view
        adapter = new HistoryRecyclerAdapter(onItemClickCallback);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        //check list for empty
        presenter.setImagesOrShowEmptyText();
        recyclerView.setAdapter(adapter);
    }

    //listener for recycler view
    OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            presenter.openNextApp(position);
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.sort_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void showDateSortDialog(){
        if(getActivity() != null) {
            new MaterialDialog.Builder(getActivity())
                    .title(R.string.title_date_dialog)
                    .itemsColor(getResources().getColor(android.R.color.black))
                    .items(R.array.items_date)
                    .itemsCallback((dialog, view, which, text) -> presenter.setDateSorting(which))
                    .show();
        }
    }

    public void showStatusSortDialog(){
        if(getActivity() != null) {
            new MaterialDialog.Builder(getActivity())
                    .title(R.string.title_status_dialog)
                    .items(R.array.items_status)
                    .itemsColor(getResources().getColor(android.R.color.black))
                    .itemsCallback((dialog, view, which, text) -> presenter.setStatusSorting(which))
                    .show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort_by_date:
                showDateSortDialog();
                break;
            case R.id.sort_by_status:
                showStatusSortDialog();
                break;
        }
        return true;
    }

    @Override
    public void showEmptyText() {
        emptyText.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void setImagesList(final List<ImageModel> imageModels) {
        recyclerView.post(() -> adapter.setData(imageModels));
        adapter.notifyDataSetChanged();
    }
    @Override
    public void openNextApp(List<ImageModel> imageModels, int position){
        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage(getString(R.string.second_app_package_name));
        //check if App B exists
        if(intent != null){
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // put image url and status to retrieve it in App B
            intent.putExtra(INTENT_KEY, imageModels.get(position).getImageUrl());
            intent.putExtra(IMAGE_STATUS, imageModels.get(position).getStatus());

            getActivity().startActivity(intent);
        } else {
            Toast.makeText(getActivity(), getString(R.string.app_not_found), Toast.LENGTH_SHORT).show();
        }
    }

}
