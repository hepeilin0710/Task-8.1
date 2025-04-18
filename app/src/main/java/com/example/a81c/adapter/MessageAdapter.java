package com.example.a81c.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a81c.R;
import com.example.a81c.model.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.VH> {

    private final List<Message> data;
    public MessageAdapter(List<Message> list){ this.data = list; }

    static class VH extends RecyclerView.ViewHolder{
        TextView tv;
        VH(@NonNull View itemView) { super(itemView); tv = itemView.findViewById(R.id.tvMsg); }
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = viewType == 0 ? R.layout.item_msg_right : R.layout.item_msg_left;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new VH(v);
    }

    @Override public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.tv.setText(data.get(position).getContent());
    }

    @Override public int getItemViewType(int position) {
        return data.get(position).getSender() == Message.Sender.USER ? 0 : 1;
    }

    @Override public int getItemCount() { return data.size(); }
}
