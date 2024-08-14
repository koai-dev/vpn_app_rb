package com.ntarevpn.rbpessacash.state;

import com.ntarevpn.rbpessacash.state.helper.FailureState;
import com.ntarevpn.rbpessacash.state.helper.LoadingState;
import com.ntarevpn.rbpessacash.state.helper.SuccessState;

public interface ResetPasswordState {
    class Success implements ResetPasswordState, SuccessState<String> {

        final String password;

        public Success(String password) {
            this.password = password;
        }

        @Override
        public String getResult() {
            return password;
        }
    }

    class Failure implements ResetPasswordState, FailureState {
        private final String message;

        public Failure(String message) {
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    class Loading implements ResetPasswordState, LoadingState {
    }

    class Idle implements ResetPasswordState, LoadingState {
    }
}
