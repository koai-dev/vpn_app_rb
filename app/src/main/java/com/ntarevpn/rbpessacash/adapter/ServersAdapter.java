package com.ntarevpn.rbpessacash.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ntarevpn.rbpessacash.databinding.AdsBannerIndratechBinding;
import com.ntarevpn.rbpessacash.databinding.ItemServerIndratechBinding;
import com.ntarevpn.rbpessacash.models.Server;
import com.ntarevpn.rbpessacash.util.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServersAdapter extends RecyclerView.Adapter<ServersAdapter.MyViewHolder> {
    private final ArrayList<Server> servers = new ArrayList<>();
    private String chosen = "";
    private ServerListener listener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == -1) {
            return AdViewHolder.getInstance(parent);
        }
        return ItemViewHolder.getInstance(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            final int mPosition = position + 1 < 6 ? position : position - (int) Math.floor((float) position / (float) (servers.size() / additionalSpace()));
            Server server = servers.get(mPosition);
            ((ItemViewHolder) holder).bind(server, listener, Objects.equals(server.getId(), chosen));
        } else if (holder instanceof AdViewHolder) {
            ((AdViewHolder) holder).bind();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if ((position + 1) % 6 == 0 || (position + 1 < 6 && position + 1 == servers.size() + additionalSpace())) {
            Log.d("Spot", "AdSpot");
            return -1; // Ad Spot
        } else {
            Log.d("Spot", "ItemSpot");
            return 0;
        }
    }

    @Override
    public int getItemCount() {
        return servers.size() + additionalSpace();
    }

    private int additionalSpace() {
        return (int) Math.floor((float) servers.size() / 6.0f);
    }

    public static abstract class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class AdViewHolder extends MyViewHolder {
        private final AdsBannerIndratechBinding binding;
        private boolean alreadyAdded = false;

        public AdViewHolder(@NonNull AdsBannerIndratechBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private static AdViewHolder getInstance(ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            AdsBannerIndratechBinding binding = AdsBannerIndratechBinding.inflate(inflater, parent, false);
            return new AdViewHolder(binding);
        }

        private void bind() {
            if (binding.getRoot().getChildCount() < 1 && !alreadyAdded) {
                binding.getRoot().removeAllViews();
                Constant.loadSmallAdBanner(binding.getRoot());
                alreadyAdded = true;
            }
        }
    }

    public static class ItemViewHolder extends MyViewHolder {
        private final ItemServerIndratechBinding binding;

        public ItemViewHolder(ItemServerIndratechBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private static ItemViewHolder getInstance(ViewGroup viewGroup) {
            Context context = viewGroup.getContext();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            ItemServerIndratechBinding binding = ItemServerIndratechBinding.inflate(layoutInflater, viewGroup, false);

            return new ItemViewHolder(binding);
        }

        private void bind(Server server, ServerListener listener, boolean chosen) {
            RadioButton check = binding.itemCheckIndicator;
            binding.itemServerName.setText(server.getServerName());
            Glide.with(binding.itemBanner)
                    .load(server.getFlagUrl())
                    .into(binding.itemBanner);
            check.setChecked(chosen);
            if (!server.getIsFree()) {
                binding.itemPaidChecker.setVisibility(View.VISIBLE);
            }
            binding.getRoot().setOnClickListener(v -> listener.onPicked(server));
        }
    }

    public interface ServerListener {
        void onPicked(Server server);
    }

    public void setData(List<Server> newServers, String newChosen) {
        servers.clear();
        servers.addAll(newServers);
        chosen = newChosen != null ? newChosen : "";
        notifyDataSetChanged();
    }

    public void setServerListener(ServerListener newListener) {
        listener = newListener;
    }
}
