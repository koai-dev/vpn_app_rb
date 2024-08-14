package com.ntarevpn.rbpessacash.state;

import com.ntarevpn.rbpessacash.models.Subscription;
import com.ntarevpn.rbpessacash.state.helper.FailureState;
import com.ntarevpn.rbpessacash.state.helper.LoadingState;
import com.ntarevpn.rbpessacash.state.helper.SuccessState;

import java.util.List;

public interface SubscriptionsState {
    class Success implements SubscriptionsState, SuccessState<List<Subscription>> {
        private final List<Subscription> subscriptions;

        public Success(List<Subscription> subscriptions) {
            this.subscriptions = subscriptions;
        }

        @Override
        public List<Subscription> getResult() {
            return subscriptions;
        }
    }

    class Failure implements SubscriptionsState, FailureState {
        private final String message;

        public Failure(String message) {
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    class Loading implements SubscriptionsState, LoadingState { }

    class Idle implements SubscriptionsState, LoadingState { }
}
