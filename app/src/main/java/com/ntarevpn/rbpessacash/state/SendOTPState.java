package com.ntarevpn.rbpessacash.state;

import com.ntarevpn.rbpessacash.state.helper.FailureState;
import com.ntarevpn.rbpessacash.state.helper.LoadingState;
import com.ntarevpn.rbpessacash.state.helper.SuccessState;

public interface SendOTPState {
    class Success implements SendOTPState, SuccessState<String> {
        private final String secretOtp;

        public Success(String secretOtp) {
            this.secretOtp = secretOtp;
        }

        @Override
        public String getResult() {
            return secretOtp;
        }
    }

    class Failure implements SendOTPState, FailureState {
        private final String message;

        public Failure(String message) {
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    class Loading implements SendOTPState, LoadingState { }

    class Idle implements SendOTPState, LoadingState { }
}
