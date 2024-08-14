package com.ntarevpn.rbpessacash.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ntarevpn.rbpessacash.databinding.FeedbackActivityIndratechBinding;
import com.ntarevpn.rbpessacash.viewModels.FeedbackViewModel;
import com.ntarevpn.rbpessacash.models.SMTPDetail;
import com.ntarevpn.rbpessacash.state.SMTPState;
import com.ntarevpn.rbpessacash.util.Constant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ph.gemeaux.materialloadingindicator.MaterialCircularIndicator;

public class Feedback extends AppCompatActivity {
    public static final String RATING_REQUEST_CODE = "feedback_rating";
    private FeedbackActivityIndratechBinding binding;
    private MaterialCircularIndicator progressIndicator;
    private FeedbackViewModel viewModel;
    private SMTPDetail smtpDetail;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FeedbackActivityIndratechBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        initializeProgressIndicator();
        initializeViewModel();
        initializeRating();
        initializeClickListener();
    }

    private void initializeProgressIndicator() {
        progressIndicator = new MaterialCircularIndicator(this);
        progressIndicator.setTrackCornerRadius(10);
        progressIndicator.setCanceleable(false);
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(FeedbackViewModel.class);
        listenToState();
        viewModel.fetchSMTPDetailList();
    }

    private void listenToState() {
        viewModel.getState().observe(this, state -> {
            if (state instanceof SMTPState.Success) {
                SMTPState.Success successState = (SMTPState.Success) state;
                List<SMTPDetail> result = successState.getResult();
                if (result.size() > 0) {
                    smtpDetail = result.get(0);
                } else {
                    sendFailure("There's an unexpected error");
                }
                progressIndicator.dismiss();
            } else if (state instanceof SMTPState.Failure) {
                SMTPState.Failure failure = (SMTPState.Failure) state;
                sendFailure(failure.getMessage());
                progressIndicator.dismiss();
            } else if (state instanceof SMTPState.Loading) {
                progressIndicator.show();
            }
        });
    }

    private void sendFailure(@NonNull String message) {
        Constant.showToastMessage(this, message);
        finish();
    }

    private void initializeRating() {
        float rating = getIntent().getFloatExtra(RATING_REQUEST_CODE, 0.0f);
        binding.ratingInput.setRating(rating);
    }

    private void initializeClickListener() {
        binding.submitButton.setOnClickListener(v -> sendEmail());

        binding.exitButtonFeedback.setOnClickListener(v -> onBackPressed());
    }

    private void sendEmail() {
        int rating = Math.round(binding.ratingInput.getRating());
        String feedback = binding.feedbackInput.getText().toString();
        Calendar date = Calendar.getInstance();
        String dateString = dateFormat.format(date.getTime());
        String email = smtpDetail.getSmtpEmail();

        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        i.putExtra(Intent.EXTRA_SUBJECT, "Feedback and Rating | " + dateString);
        i.putExtra(Intent.EXTRA_TEXT, "Rating: \n" + rating + "/5" + "\n\nFeedback: \n" + feedback);

        i.setType("message/rfc822");
        startActivity(Intent.createChooser(i, "Choose an Email client :"));
        finish();
    }
}
