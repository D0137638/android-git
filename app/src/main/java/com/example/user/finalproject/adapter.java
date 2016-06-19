package com.example.user.finalproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by User on 2016/4/17.
 */
public class adapter extends RecyclerView.Adapter <adapter.MyViewHolder> {

    private LayoutInflater inflater;
    List<Information> data = Collections.emptyList();

    public adapter(Context context, List<Information> data)
    {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Information current = data.get(position);

        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                switch (position)
                {
                    case 0:
                        Intent register = new Intent(view.getContext(), RegisterActivity.class);
                        view.getContext().startActivity(register);
                        break;

                    case 1:
                        Intent login = new Intent(view.getContext(), LoginActivity.class);
                        view.getContext().startActivity(login);
                        break;

                    case 2:
                        /*Intent about = new Intent(view.getContext(), AboutActivity.class);
                        view.getContext().startActivity(about);*/
                        AlertDialog.Builder about_msg = new AlertDialog.Builder(view.getContext());
                        about_msg.setTitle("關於程式");
                        about_msg.setMessage("作者:\n\n翁大原、潘詠霖、張硯傑");



                        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //finish();
                                //startActivity(new Intent(FavoriteActivity.this, FavoriteActivity.class));
                            }
                        };
                        about_msg.setNegativeButton("確定", listener);
                        about_msg.show();
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
        }
    }

}
