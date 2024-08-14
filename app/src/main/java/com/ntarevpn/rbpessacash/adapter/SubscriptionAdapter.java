package com.ntarevpn.rbpessacash.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ntarevpn.rbpessacash.databinding.SubscriptionHeaderLayoutBinding;
import com.ntarevpn.rbpessacash.databinding.SubscriptionItemLayoutIndratechBinding;
import com.ntarevpn.rbpessacash.models.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.MyViewHolder> {
    private final ArrayList<Subscription> subscriptions = new ArrayList<>();
    private String pickedSubscription = "";
    private OnItemClickListener listener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == -1) {
            return HeaderViewHolder.getInstance(parent);
        } else {
            return ItemViewHolder.getInstance(parent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            Subscription subscription = subscriptions.get(position-1);
            itemViewHolder.bind(subscription, Objects.equals(subscription.getId(), pickedSubscription), listener);
        }
    }

    @Override
    public int getItemCount() {
        return subscriptions.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return -1;
        } else {
            return 0;
        }
    }

    public void setData(@NonNull List<Subscription> newList) {
        subscriptions.clear();
        subscriptions.addAll(newList);
        notifyDataSetChanged();
    }

    public void pickSubscription(String picked) {
        pickedSubscription = picked;
        notifyDataSetChanged();
    }

    public void setClickListener(OnItemClickListener newListener) {
        listener = newListener;
    }

    public static abstract class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener {
        void onClick(Subscription subscription);
    }

    private static class ItemViewHolder extends MyViewHolder {
        private final SubscriptionItemLayoutIndratechBinding binding;

        public ItemViewHolder(@NonNull SubscriptionItemLayoutIndratechBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(Subscription subscription, boolean isPicked, OnItemClickListener listener) {
            binding.itemSubscriptionName.setText(subscription.getName());
            binding.itemSubscriptionPrice.setText(subscription.getCurrency() + subscription.getPrice());
            binding.itemCheckIndicator.setChecked(isPicked);

            binding.getRoot().setOnClickListener(view -> listener.onClick(subscription));
        }

        private static ItemViewHolder getInstance(ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            SubscriptionItemLayoutIndratechBinding binding = SubscriptionItemLayoutIndratechBinding.inflate(layoutInflater, parent, false);
            return new ItemViewHolder(binding);
        }
    }

    private static class HeaderViewHolder extends MyViewHolder {
        private SubscriptionHeaderLayoutBinding binding;

        public HeaderViewHolder(@NonNull SubscriptionHeaderLayoutBinding binding) {
            super(binding.getRoot());
        }

        private static HeaderViewHolder getInstance(ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            SubscriptionHeaderLayoutBinding binding = SubscriptionHeaderLayoutBinding.inflate(layoutInflater, parent, false);
            return new HeaderViewHolder(binding);
        }
    }
}
