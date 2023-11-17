package com.pavitharan.tha_204151x.adapters;

import com.pavitharan.tha_204151x.R;
import com.pavitharan.tha_204151x.activities.FormActivity;
import com.pavitharan.tha_204151x.data.Item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
public class ItemAdapter extends ListAdapter<Item,ItemAdapter.ViewHolder> {
    private Activity activity;

    public ItemAdapter(Activity activity) {
        super(DIFF_CALLBACK);
        this.activity = activity;
    }

    private static final DiffUtil.ItemCallback<Item> DIFF_CALLBACK = new DiffUtil.ItemCallback<Item>() {
        @Override
        public boolean areItemsTheSame(Item oldItem, Item newItem) {
            return oldItem.getItemid() == newItem.getItemid();
        }

        @Override
        public boolean areContentsTheSame(Item oldItem, Item newItem) {
            return oldItem.getName().equals(newItem.getName())
                    && (oldItem.getDescription().equals(newItem.getDescription())
                    && (oldItem.getPrice() == newItem.getPrice()));
        }
    };


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_card,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder holder, int position) {

        Item item = getItemAt(position);

        TextView tv_title = holder.tv_item_title;
        tv_title.setText(item.getName());

        TextView tv_description = holder.tv_item_description;
        tv_description.setText(item.getDescription());

        TextView tv_price = holder.tv_item_price;
        tv_price.setText("Rs. "+String.valueOf(item.getPrice()));

        ImageView iv_itemImage = holder.iv_item_pic;
        int imagePoint = position%3;
        iv_itemImage.setImageResource(imagePoint==1?R.drawable.image1:imagePoint==2? R.drawable.image2:R.drawable.image3);

        holder.btn_item_edit.setOnClickListener(v -> {
            Intent intent = new Intent(this.activity, FormActivity.class);
            intent.putExtra("Title",item.getName());
            intent.putExtra("Description",item.getDescription());
            intent.putExtra("Price",item.getPrice());
            intent.putExtra("Position",position);
            intent.putExtra("ID",item.getItemid());
            activity.startActivityForResult(intent,2);
        });
    }

    public Item getItemAt(int position){
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_item_pic;
        private TextView tv_item_title;
        private TextView tv_item_description;
        private TextView tv_item_price;
        private Button btn_item_edit;


        public ViewHolder(View itemView){
            super(itemView);
            iv_item_pic = itemView.findViewById(R.id.iv_item_pic);
            tv_item_title =  itemView.findViewById(R.id.tv_item_title);
            tv_item_description = itemView.findViewById(R.id.tv_item_description);
            tv_item_price = itemView.findViewById(R.id.tv_item_price);
            btn_item_edit = itemView.findViewById(R.id.item_edit);
        }
    }


}
