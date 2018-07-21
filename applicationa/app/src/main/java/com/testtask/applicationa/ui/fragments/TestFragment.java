package com.testtask.applicationa.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.testtask.applicationa.R;

import static com.testtask.applicationa.utils.Consts.INTENT_KEY;

public class TestFragment extends Fragment {

    View view;
    EditText editText;
    Button button;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.test_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //init views
        editText = view.findViewById(R.id.test_edit_text);
        button = view.findViewById(R.id.test_ok_button);
        //set OnClickListener
        setButtonClickListener();
    }


    private void setButtonClickListener(){
        // call onClick method for checking text valid
        button.setOnClickListener(view -> validateUrl(editText.getText().toString().trim()));
    }

    private void validateUrl(String text){
        // check if our text starts with https:// or http://
        // It can`t take guarantees that it is truly image url, but we will validate empty text and not urls
        if(URLUtil.isHttpsUrl(text) || URLUtil.isHttpUrl(text)) {
            launchIntent(text);
        } else {
            Toast.makeText(getActivity(), getString(R.string.not_valid_url), Toast.LENGTH_SHORT).show();
        }
    }

    private void launchIntent(String text){
        // get intent for opening App B
        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage(getString(R.string.second_app_package_name));
        //check if App B exists
        if(intent != null){
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // put image url to retrieve it in App B
            intent.putExtra(INTENT_KEY, text);
            getActivity().startActivity(intent);
        } else {
            Toast.makeText(getActivity(), getString(R.string.app_not_found), Toast.LENGTH_SHORT).show();
        }
    }

}
