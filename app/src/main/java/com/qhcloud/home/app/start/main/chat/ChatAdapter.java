package com.qhcloud.home.app.start.main.chat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qhcloud.demo.R;
import com.qhcloud.home.app.BaseAdapter;
import com.qhcloud.home.entity.Message;
import com.qhcloud.home.entity.UserInfo;
import com.qhcloud.home.view.CircleImageView;

import java.util.List;

/**
 * 聊天适配器（文字，图片）
 * @author youngbin
 */

public class ChatAdapter extends BaseAdapter<Message> {

    public static final int TYPE_TEXT_TO = 100;
    public static final int TYPE_TEXT_FROM = 101;
    public static final int TYPE_IMAGE_TO = 102;
    public static final int TYPE_IMAGE_FROM = 103;

    private int mOwnerUid;
    private UserInfo mToUser;



    public ChatAdapter(int ownerUid, UserInfo userInfo, List<Message> list) {
        super(list);
        mOwnerUid = ownerUid;
        mToUser = userInfo;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = get(position);
        int type = -1;
        switch (message.getType()) {
            case 0:
                //文字
                type = mOwnerUid == message.getFromId()  ? TYPE_TEXT_TO : TYPE_TEXT_FROM;
                break;
            case 2:
                //图片
                type = mOwnerUid == message.getFromId()  ? TYPE_IMAGE_TO : TYPE_IMAGE_FROM;
            default:
        }
        return type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_TEXT_TO:
                viewHolder = new TextToViewHolder(createView(parent, R.layout.item_chat_to_text));
                break;
            case TYPE_TEXT_FROM:
                viewHolder = new TextFromViewHolder(createView(parent, R.layout.item_chat_from_text));
                break;
            case TYPE_IMAGE_TO:
                viewHolder = new ImageToViewHolder(createView(parent, R.layout.item_chat_to_image));
                break;
            case TYPE_IMAGE_FROM:
                viewHolder = new ImageFromViewHolder(createView(parent, R.layout.item_chat_from_image));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = get(position);
        if (holder instanceof TextToViewHolder) {
            TextToViewHolder viewHolder = (TextToViewHolder) holder;
            viewHolder.dateTv.setText(message.getDateText());
            viewHolder.contentTv.setText(message.getContent());
        } else if (holder instanceof TextFromViewHolder) {
            TextFromViewHolder viewHolder = (TextFromViewHolder) holder;
            viewHolder.dateTv.setText(message.getDateText());
            viewHolder.contentTv.setText(message.getContent());
            if(mToUser != null){
                Glide.with(mContext).load(mToUser.getAvatarUrl())
                        .placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).dontAnimate()
                        .into(viewHolder.avatarIv);
            }

        }else if(holder instanceof ImageToViewHolder){
            ImageToViewHolder viewHolder = (ImageToViewHolder) holder;
            viewHolder.dateTv.setText(message.getDateText());
            if(mToUser != null){
                Glide.with(mContext).load(mToUser.getAvatarUrl())
                        .placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).dontAnimate()
                        .into(viewHolder.avatarIv);
            }

            Glide.with(mContext).load(message.getContent()).placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).dontAnimate()
                    .into(viewHolder.contentIv);

        }else if(holder instanceof ImageFromViewHolder){
            ImageFromViewHolder viewHolder = (ImageFromViewHolder) holder;
            viewHolder.dateTv.setText(message.getDateText());
            if(mToUser != null){
                Glide.with(mContext).load(mToUser.getAvatarUrl())
                        .placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).dontAnimate()
                        .into(viewHolder.avatarIv);
            }

            Glide.with(mContext).load(message.getContent()).placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).dontAnimate()
                    .into(viewHolder.contentIv);

        }
    }

    private class TextToViewHolder extends RecyclerView.ViewHolder {

        TextView dateTv;
        CircleImageView avatarIv;
        TextView contentTv;

        private TextToViewHolder(View itemView) {
            super(itemView);

            dateTv = (TextView) itemView.findViewById(R.id.chat_to_text_date_tv);
            avatarIv = (CircleImageView) itemView.findViewById(R.id.chat_to_text_avatar_iv);
            contentTv = (TextView) itemView.findViewById(R.id.chat_to_text_content_tv);

        }
    }

    private class TextFromViewHolder extends RecyclerView.ViewHolder {

        TextView dateTv;
        CircleImageView avatarIv;
        TextView contentTv;

        private TextFromViewHolder(View itemView) {
            super(itemView);
            dateTv = (TextView) itemView.findViewById(R.id.chat_from_text_date_tv);
            avatarIv = (CircleImageView) itemView.findViewById(R.id.chat_from_text_avatar_iv);
            contentTv = (TextView) itemView.findViewById(R.id.chat_from_text_content_tv);
        }
    }

    private class ImageToViewHolder extends RecyclerView.ViewHolder {

        TextView dateTv;
        CircleImageView avatarIv;
        ImageView contentIv;

        private ImageToViewHolder(View itemView) {
            super(itemView);

            dateTv = (TextView) itemView.findViewById(R.id.chat_to_image_date_tv);
            avatarIv = (CircleImageView) itemView.findViewById(R.id.chat_to_image_avatar_iv);
            contentIv = (ImageView) itemView.findViewById(R.id.chat_to_image_content_iv);

        }
    }

    private class ImageFromViewHolder extends RecyclerView.ViewHolder {

        TextView dateTv;
        CircleImageView avatarIv;
        ImageView contentIv;

        private ImageFromViewHolder(View itemView) {
            super(itemView);
            dateTv = (TextView) itemView.findViewById(R.id.chat_from_image_date_tv);
            avatarIv = (CircleImageView) itemView.findViewById(R.id.chat_from_image_avatar_iv);
            contentIv = (ImageView) itemView.findViewById(R.id.chat_from_image_content_iv);
        }
    }

}
