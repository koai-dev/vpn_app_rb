package com.ntarevpn.rbpessacash.fragment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.ntarevpn.rbpessacash.databinding.FragmentProfileBinding;
import com.ntarevpn.rbpessacash.viewModels.ProfileViewModel;
import com.ntarevpn.rbpessacash.ApiBaseUrl;
import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.activity.Main;
import com.ntarevpn.rbpessacash.models.User;
import com.ntarevpn.rbpessacash.state.ProfileUpdateState;
import com.ntarevpn.rbpessacash.util.Constant;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import ph.gemeaux.materialloadingindicator.MaterialCircularIndicator;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;
    private MaterialCircularIndicator progressIndicator;
    private final ActivityResultLauncher<String> imagePicker = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> viewModel.setTempProfile(result));
    private String imageBase64;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        initializeMiscellaneous();
        initializeViewModel();
        initializeProfile();
        initializeClickListener();

        return binding.getRoot();
    }

    private void initializeMiscellaneous() {
        progressIndicator = new MaterialCircularIndicator(getActivity());
        progressIndicator.setTrackCornerRadius(10);
        progressIndicator.setCanceleable(false);
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        listenToProfile();
        listenToUpdateProfileState();
    }

    private void listenToProfile() {
        viewModel.getTempProfile().observe(getViewLifecycleOwner(), result -> {
            if (!result.getPath().isEmpty()) {
                try {
                    ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), result);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, imageStream);
                    byte[] imageBytes = imageStream.toByteArray();
                    imageStream.close();

                    Glide.with(getContext())
                            .load(bitmap)
                            .centerCrop()
                            .into(binding.userProfile);
                    imageBase64 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                } catch (IOException e) {
                    imageBase64 = "";
                    Constant.showToastMessage(getContext(), "There's an error when getting the image");
                }
            } else {
                imageBase64 = "";
            }
        });
    }

    private void listenToUpdateProfileState() {
        viewModel.getUpdateProfileState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof ProfileUpdateState.Success) {
                ProfileUpdateState.Success successState = (ProfileUpdateState.Success) state;
                User user = successState.getResult();

                viewModel.setTempProfile(Uri.EMPTY);

                Constant.SetUserData(getContext(), user, null);

                initializeProfile();
                updateSidebarProfile();

                viewModel.resetProfileState();
                progressIndicator.dismiss();
                Constant.showToastMessage(getContext(), "Successfully update user profile");
            } else if (state instanceof ProfileUpdateState.Failure) {
                ProfileUpdateState.Failure failedState = (ProfileUpdateState.Failure) state;

                viewModel.resetProfileState();
                progressIndicator.dismiss();
                Constant.showToastMessage(getContext(), failedState.getMessage());
            } else if (state instanceof ProfileUpdateState.Loading) {
                progressIndicator.show();
            }
        });
    }

    private void updateSidebarProfile() {
        try {
            Main activity = (Main) getActivity();
            if (activity != null) {
                activity.initializeNavProfile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeProfile() {
        if (getContext() != null) {
            binding.emailChange.setText(Constant.getString(getContext(), Constant.USER_EMAIL));
            binding.usernameChange.setText(Constant.getString(getContext(), Constant.USER_NAME));
            binding.numberChange.setText(Constant.getString(getContext(), Constant.USER_NUMBER));
            Glide.with(getContext())
                    .load(ApiBaseUrl.LOAD_USER_IMAGE_APP + Constant.getString(getContext(), Constant.USER_IMAGE))
                    .centerCrop()
                    .placeholder(R.drawable.user_ic)
                    .into(binding.userProfile);
        }
    }

    private void initializeClickListener() {
        binding.profileContainer.setOnClickListener(v -> pickImage());
        binding.profileChangeSubmit.setOnClickListener(v -> changeProfile());
    }

    private void pickImage() {
        if (Constant.isMediaReadPermissionGranted(getActivity())) {
            imagePicker.launch("image/*");
        } else {
            Constant.showToastMessage(getContext(), "Cannot open storage because permission denied.");
        }
    }

    private void changeProfile() {
        String userId = Constant.getString(getContext(), Constant.USER_ID);
        String email = binding.emailChange.getText().toString();
        String number = binding.numberChange.getText().toString();
        String username = binding.usernameChange.getText().toString();

        Log.d("IMAGE", imageBase64);

        Constant.hideKeyboard(getActivity());
        viewModel.updateUserProfile(userId, email, number, username, imageBase64);
    }
}
