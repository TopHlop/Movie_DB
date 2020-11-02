package com.example.themoviedb.main.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.themoviedb.R;
import com.example.themoviedb.databinding.FragmentUserBinding;
import com.example.themoviedb.di.DI;
import com.example.themoviedb.main.ui.CircularAvatarIconTransformation;
import com.example.themoviedb.MainActivity;
import com.example.themoviedb.main.viewModel.UserViewModel;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import javax.inject.Inject;


public class UserFragment extends Fragment {

    private FragmentUserBinding binding;
    private OnNavigateToLoginFragmentListener listener;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private UserViewModel userViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OnNavigateToLoginFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnNavigateToLoginFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DI.getAppComponent().inject(this);
        userViewModel = new ViewModelProvider(this, viewModelFactory).get(UserViewModel.class);
        userViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            binding.nameText.setText(user.getName());
            Picasso.get()
                    .load(String.format(getString(R.string.gravatar_url),
                            user.getAvatar().getGravatar().getHash(),
                            binding.avatarIcon.getHeight()))
                    .error(Objects.requireNonNull(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()),
                            R.drawable.ic_avatar)))
                    .transform(CircularAvatarIconTransformation.getInstance())
                    .into(binding.avatarIcon);
        });
        userViewModel.getUserData();
        userViewModel.getIsSuccessDeleteSession().observe(getViewLifecycleOwner(), isSuccessDeleteSession -> {
            if(isSuccessDeleteSession) {
                listener.navigateToLoginFragment();
            }
        });
        binding.exitButton.setOnClickListener(view -> {
            userViewModel.deleteSession();
            setExitButtonClickable(false);
        });
    }

    private void setExitButtonClickable(boolean clickable) {
        binding.exitButton.setClickable(clickable);
    }

    public interface OnNavigateToLoginFragmentListener {
        void navigateToLoginFragment();
    }
}
