package com.ntarevpn.rbpessacash.state;

import com.ntarevpn.rbpessacash.models.Server;
import com.ntarevpn.rbpessacash.state.helper.FailureState;
import com.ntarevpn.rbpessacash.state.helper.LoadingState;
import com.ntarevpn.rbpessacash.state.helper.SuccessState;

import java.util.List;

public interface ServersState {
    class Success implements ServersState, SuccessState<List<Server>> {
        private final List<Server> server;

        public Success(List<Server> server) {
            this.server = server;
        }

        @Override
        public List<Server> getResult() {
            return server;
        }
    }

    class Failure implements ServersState, FailureState {
        private final String message;

        public Failure(String message) {
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    class Loading implements ServersState, LoadingState { }

    class Idle implements ServersState, LoadingState { }
}
