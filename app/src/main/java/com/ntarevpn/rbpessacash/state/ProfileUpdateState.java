package com.ntarevpn.rbpessacash.state;

import com.ntarevpn.rbpessacash.models.User;
import com.ntarevpn.rbpessacash.state.helper.FailureState;
import com.ntarevpn.rbpessacash.state.helper.IdleState;
import com.ntarevpn.rbpessacash.state.helper.LoadingState;
import com.ntarevpn.rbpessacash.state.helper.SuccessState;

public interface ProfileUpdateState {
    class Success implements ProfileUpdateState, SuccessState<User> {
        private final User user;

        public Success(User user) {
            this.user = user;
        }

        @Override
        public User getResult() {
            return user;
        }
    }

    class Failure implements ProfileUpdateState, FailureState {
        private final String message;

        public Failure(String message) {
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    class Loading implements ProfileUpdateState, LoadingState {}

    class Idle implements ProfileUpdateState, IdleState {}
}
