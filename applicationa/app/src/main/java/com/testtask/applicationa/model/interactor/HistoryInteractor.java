package com.testtask.applicationa.model.interactor;

import com.testtask.applicationa.entity.ImageModel;
import com.testtask.applicationa.model.repository.HistoryRepository;

import java.util.List;

public class HistoryInteractor {

    private HistoryRepository repository;

    public HistoryInteractor(HistoryRepository repository) {
        this.repository = repository;
    }

    public List<ImageModel> getImages(){
        return repository.getImagesFromDb();
    }

    public List<ImageModel> sortByNewDate(){
        return repository.sortByNewDate();
    }

    public List<ImageModel> sortByGreenRedGrey(){
        return repository.sortByGreenRedGrey();
    }

    public List<ImageModel> sortByGreyRedGreen(){
        return repository.sortByGreyRedGreen();
    }

    public List<ImageModel> getSortedList(){
        return repository.getSortedList();
    }

    public void setSortingMode(String mode){
        repository.setSortingMode(mode);
    }

}
