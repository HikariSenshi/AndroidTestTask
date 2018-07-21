package com.testtask.applicationa.presentation.history;

import com.testtask.applicationa.entity.ImageModel;

import java.util.List;

public interface HistoryView {

    void showEmptyText();
    void setImagesList(List<ImageModel> imageModels);
    void openNextApp(List<ImageModel> imageModels, int position);

}
