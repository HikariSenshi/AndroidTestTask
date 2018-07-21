package com.testtask.applicationa.presentation.history;

import com.testtask.applicationa.entity.ImageModel;
import com.testtask.applicationa.model.interactor.HistoryInteractor;

import java.util.ArrayList;
import java.util.List;

import static com.testtask.applicationa.utils.Consts.GREEN_RED_GREY;
import static com.testtask.applicationa.utils.Consts.GREY_RED_GREEN;
import static com.testtask.applicationa.utils.Consts.NEW_OLD;
import static com.testtask.applicationa.utils.Consts.OLD_NEW;

public class HistoryPresenter {

    private HistoryView view;
    private HistoryInteractor interactor;

    public HistoryPresenter(HistoryView view, HistoryInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    public void setImagesOrShowEmptyText(){
        if(interactor.getSortedList().isEmpty()){
            view.showEmptyText();
        } else {
            view.setImagesList(interactor.getSortedList());
        }
    }

    public void setDateSorting(int position){
        if(position == 0) {
            view.setImagesList(interactor.getImages());
            interactor.setSortingMode(OLD_NEW);
        } else {
            view.setImagesList(interactor.sortByNewDate());
            interactor.setSortingMode(NEW_OLD);
        }
    }

    public void setStatusSorting(int position){
        if(position == 0) {
            view.setImagesList(interactor.sortByGreenRedGrey());
            interactor.setSortingMode(GREEN_RED_GREY);
        } else {
            view.setImagesList(interactor.sortByGreyRedGreen());
            interactor.setSortingMode(GREY_RED_GREEN);
        }
    }

    public void openNextApp(int position){
        view.openNextApp(interactor.getSortedList(), position);
    }

}
