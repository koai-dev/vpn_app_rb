package com.ntarevpn.rbpessacash.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private final MutableLiveData<Integer> points = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> dailyPoints = new MutableLiveData<>(0);
    private final MutableLiveData<Boolean> feedbackDialogStatus = new MutableLiveData<>(false);

    public LiveData<Integer> getPoints() {
        return points;
    }

    public void setPoints(Integer value) {
        points.postValue(value);
    }

    public LiveData<Integer> getDailyPoints() {
        return points;
    }

    public void setDailyPoints(Integer value) {
        dailyPoints.postValue(value);
    }

    public LiveData<Boolean> getFeedbackDialogStatus() {
        return feedbackDialogStatus;
    }

    public void showFeedbackDialog() {
        feedbackDialogStatus.postValue(true);
    }

    public void resetFeedbackDialog() {
        feedbackDialogStatus.postValue(false);
    }
}
