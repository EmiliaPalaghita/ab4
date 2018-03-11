package com.example.pemil.interview;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by pemil on 07.03.2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private ArrayList<User> users;

    final private ListItemClickListener onClickListener;

    UserAdapter(ArrayList<User> users, ListItemClickListener listener) {
        this.onClickListener = listener;
        this.users = users;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        UserViewHolder holder = new UserViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        TextView userName;
        ImageView userProfile;

        UserViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            userName = itemView.findViewById(R.id.username);
            userProfile = itemView.findViewById(R.id.user_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clicked = getAdapterPosition();
            onClickListener.onListItemClick(clicked);
        }

        void bind(User user) {
            userName.setText(user.getUserName());
            new ImageTask().execute(user.getUrlPhoto());
        }

        class ImageTask extends AsyncTask<String, Void, Bitmap> {

            @Override
            protected Bitmap doInBackground(String... strings) {
                String path = strings[0];
                Bitmap bmp = null;
                try {
                    bmp = NetworkUtils.downloadImageFromPath(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return bmp;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    userProfile.setImageBitmap(bitmap);
                }
            }
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
}
