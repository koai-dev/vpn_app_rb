package com.ntarevpn.rbpessacash.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ntarevpn.rbpessacash.activity.Challenges;
import com.ntarevpn.rbpessacash.activity.CollectReward;
import com.ntarevpn.rbpessacash.activity.CompSurvey;
import com.ntarevpn.rbpessacash.activity.OpenReward;
import com.ntarevpn.rbpessacash.activity.PurchaseActivity;
import com.ntarevpn.rbpessacash.activity.ScratchActivity;
import com.ntarevpn.rbpessacash.activity.TictactoeMain;
import com.ntarevpn.rbpessacash.activity.VideoAds;
import com.ntarevpn.rbpessacash.activity.Wallet;
import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.databinding.ItemMenuIndratechBinding;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {
    private final Context context;
    public final ArrayList<MenuArg> args = new ArrayList<>();

    public MenuAdapter(Context context) {
        this.context = context;
    }

    public void initializeNavMenu() {
        addMenu(R.string.tt_get_reward, R.string.click_to_earn_more_coins, R.drawable.medal, v -> startActivity(CollectReward.class));
        addMenu(R.string.open_reward, R.string.click_to_earn_more_coins, R.drawable.gift, v -> startActivity(OpenReward.class));
        addMenu(R.string.complete_challenge, R.string.complete_challenge, R.drawable.event, v -> startActivity(Challenges.class));
        addMenu(R.string.watch_video, R.string.watch_videos_earn_coins, R.drawable.profits, v -> startActivity(VideoAds.class));
        addMenu(R.string.complete_survey, R.string.click_to_earn_more_coins, R.drawable.surveyor, v -> startActivity(CompSurvey.class));
        addMenu(R.string.premium_plans, R.string.buy_premium, R.drawable.money, v -> startActivity(PurchaseActivity.class));
        addMenu(R.string.scratch_to_win, R.string.scratch_earn_coins, R.drawable.scratch, v -> startActivity(ScratchActivity.class));
        addMenu(R.string.tic_tac_toe, R.string.tic_tac_earn_coins, R.drawable.tic_img, v -> startActivity(TictactoeMain.class));
        addMenu(R.string.Wallet, R.string.click_check_coins, R.drawable.purse, v -> startActivity(Wallet.class));
    }

    private String getString(int resId) {
        return context.getString(resId);
    }

    public void addMenu(String title, String description, @DrawableRes Integer icon, View.OnClickListener clickListener) {
        args.add(new MenuArg(title, description, icon, clickListener));
    }

    public void addMenu(@StringRes int title, @StringRes int description, @DrawableRes Integer icon, View.OnClickListener clickListener) {
        addMenu(getString(title), getString(description), icon, clickListener);
    }

    private void startActivity(Class<?> activity) {
        Intent i = new Intent(context, activity);
        context.startActivity(i);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return MyViewHolder.createInstance(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(args.get(position));
    }

    @Override
    public int getItemCount() {
        return args.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        final ItemMenuIndratechBinding binding;
        public MyViewHolder(@NonNull ItemMenuIndratechBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private static MyViewHolder createInstance(ViewGroup viewGroup) {
            Context context = viewGroup.getContext();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            ItemMenuIndratechBinding binding = ItemMenuIndratechBinding.inflate(layoutInflater, viewGroup, false);
            return new MyViewHolder(binding);
        }

        public void bind(MenuArg menu) {
            binding.itemMenuTitle.setText(menu.title);
            binding.itemMenuDescription.setText(menu.description);
            Glide.with(binding.getRoot())
                    .load(menu.icon)
                    .into(binding.itemIcon);
            binding.getRoot().setOnClickListener(menu.listener);
        }
    }

    private static class MenuArg {
        private final String title;
        private final String description;
        @DrawableRes
        private final Integer icon;
        private final View.OnClickListener listener;

        private MenuArg(String title, String description, @DrawableRes Integer icon, View.OnClickListener listener) {
            this.title = title;
            this.description = description;
            this.icon = icon;
            this.listener = listener;
        }
    }
}
