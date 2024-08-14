package com.ntarevpn.rbpessacash.state;

import com.ntarevpn.rbpessacash.models.SMTPDetail;
import com.ntarevpn.rbpessacash.state.helper.FailureState;
import com.ntarevpn.rbpessacash.state.helper.LoadingState;
import com.ntarevpn.rbpessacash.state.helper.SuccessState;

import java.util.List;

public interface SMTPState {
    class Success implements SMTPState, SuccessState<List<SMTPDetail>> {
        private final List<SMTPDetail> smtpDetails;

        public Success(List<SMTPDetail> smtpDetails) {
            this.smtpDetails = smtpDetails;
        }

        @Override
        public List<SMTPDetail> getResult() {
            return smtpDetails;
        }
    }

    class Failure implements SMTPState, FailureState {
        private final String message;

        public Failure(String message) {
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    class Loading implements SMTPState, LoadingState {
    }

    class Idle implements SMTPState, LoadingState {
    }
}
