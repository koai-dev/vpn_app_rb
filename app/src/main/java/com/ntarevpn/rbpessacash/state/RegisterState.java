package com.ntarevpn.rbpessacash.state;

import com.ntarevpn.rbpessacash.models.User;
import com.ntarevpn.rbpessacash.state.helper.SuccessState;
import com.ntarevpn.rbpessacash.state.helper.FailureState;
import com.ntarevpn.rbpessacash.state.helper.LoadingState;

public interface RegisterState {
    class Success implements RegisterState, SuccessState<User> {
        private final User user;
        private final String password;

        public Success(User user, String password) {
            this.user = user;
            this.password = password;
        }

        @Override
        public User getResult() {
            return user;
        }

        public String getPassword() {
            return password;
        }
    }

    class Failure implements RegisterState, FailureState {
        private final String message;

        public Failure(String message) {
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    class Loading implements RegisterState, LoadingState { }

    class Idle implements RegisterState, LoadingState { }
}
