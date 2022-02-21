package com.example.volleyapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<User> users;
    private final LayoutInflater inflater;
    private final Context context;

    public ListAdapter(List<User> users, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.users = users;
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        holder.bindData(users.get(position));
    }

    public User getItemAtPosition(int position) {
        return users.get(position);
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Context context;
        ImageView imageView;
        TextView lblName, lblEmail;
        LinearLayout linearLayoutItem;

        ViewHolder(View view) {
            super(view);
            context = view.getContext();
            imageView = view.findViewById(R.id.imageViewUser);
            lblName = view.findViewById(R.id.lblName);
            lblEmail = view.findViewById(R.id.lblEmail);
            linearLayoutItem = view.findViewById(R.id.linearLayoutItem);
        }

        void bindData(final User user) {
            lblName.setText(user.getFirst_name() + " " + user.getLast_name());
            lblEmail.setText(user.getEmail());
            ImageRequest imgReq = new ImageRequest(
                    user.getAvatar(),
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(context.getResources(), response);
                            dr.setCornerRadius(70);
                            imageView.setImageDrawable(dr);
                        }
                    }, 300,
                    300,
                    ImageView.ScaleType.CENTER,
                    Bitmap.Config.ALPHA_8,
                    error -> error.printStackTrace());
            Singleton.getInstance(context).addToRequestQueue(imgReq);
        }
    }
}
