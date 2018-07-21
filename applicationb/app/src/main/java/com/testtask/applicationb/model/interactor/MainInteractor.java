package com.testtask.applicationb.model.interactor;

import com.testtask.applicationb.model.repository.MainRepository;

public class MainInteractor {

    private MainRepository repository;

    public MainInteractor(MainRepository repository) {
        this.repository = repository;
    }

    public void insertImage(String imageUrl, int status, String date){
        repository.insertImage(imageUrl, status, date);
    }

    public void updateImage(String imageUrl, int status){
        repository.updateImage(imageUrl, status);
    }

    public void deleteImage(String imageUrl){
        repository.deleteImage(imageUrl);
    }

    public String getDate(){
       return repository.getDate();
    }

}
