package com.qhcloud.home.app.start.main.chat;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.qhcloud.demo.R;
import com.qhcloud.home.app.BaseActivity;
import com.qhcloud.home.app.picture.PictureActivity;
import com.qhcloud.home.entity.JniResponse;
import com.qhcloud.home.entity.Message;
import com.qhcloud.home.entity.UserInfo;
import com.qhcloud.home.util.Log;
import com.qhcloud.home.view.pullrefreshlayout.PullRefreshLayout;
import com.qhcloud.home.view.pullrefreshlayout.XRecyclerView;
import com.qhcloud.net.ChatMessage;
import com.qhcloud.net.DownloadFileResultInfo;
import com.qhcloud.net.NetInfo;
import com.qhcloud.net.UploadFileFihishiInfo;
import com.qhcloud.net.UploadFileResultInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 * 聊天页
 * @author youngbin
 */

public class ChatActivity extends BaseActivity implements IChatView {

    private static final String TAG = "ChatActivity";

    public static final int REQUEST_PICTURE = 1;

    @BindView(R.id.chat_refresh_layout)
    PullRefreshLayout mRefreshLayout;

    @BindView(R.id.chat_refresh_rv)
    XRecyclerView mRecyclerView;

    @BindView(R.id.chat_content_et)
    EditText mContentEt;

    @BindView(R.id.tab_chat_more_layout)
    LinearLayout mMoreLayout;

    private ChatPresenter mPresenter;
    private UserInfo mToUserInfo;
    private ChatAdapter mAdapter;

    public static void startActivity(Activity activity, UserInfo userInfo) {
        Intent intent = new Intent(activity, ChatActivity.class);
        intent.putExtra("user_info", userInfo);
        activity.startActivity(intent);
    }


    @Override
    protected void initView() {
        setContentView(R.layout.activity_chat);
        mUnBinder = ButterKnife.bind(this);
    }

    @Override
    protected void initListener() {


    }

    @Override
    protected void initReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(String.valueOf(NetInfo.QHC_CMD_SEND_CHAT_MSG_RSP));
        filter.addAction(String.valueOf(NetInfo.QHC_CMD_RECV_CHAT_MSG));
        filter.addAction(String.valueOf(NetInfo.QHC_CMD_UPLOAD_FILE_RSP));
        filter.addAction(String.valueOf(NetInfo.QHC_CMD_FINISH_UPLOAD_FILE_RSP));
        filter.addAction(String.valueOf(NetInfo.QHC_CMD_DOWN_FILE_RSP));
        LocalBroadcastManager.getInstance(this).registerReceiver(mChatReceiver, filter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mToUserInfo = intent.getParcelableExtra("user_info");
        if (mToUserInfo != null) {
            if (TextUtils.isEmpty(mToUserInfo.getRemark())) {
                setTitle(mToUserInfo.getNickname());
            } else {
                setTitle(mToUserInfo.getRemark());
            }
        }
        mRecyclerView.setLoad(false);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);

        mPresenter = new ChatPresenter(this, this);
    }

    @Override
    protected void saveData(Bundle outState) {

    }

    @OnClick(R.id.chat_send_btn)
    void onSend(View view) {
        mPresenter.sendText();
        if (mAdapter != null) {
            mRecyclerView.smoothScrollToPosition(mAdapter.getCount());
        }
    }

    @OnClick(R.id.chat_send_more_btn)
    void onMore(View view) {
        mMoreLayout.setVisibility(mMoreLayout.isShown() ? View.GONE : View.VISIBLE);
    }

    @OnClick(R.id.tab_chat_more_picture_btn)
    void onPicture(View view) {
        PictureActivity.startActivityForResult(this, REQUEST_PICTURE);
    }


    @Override
    public int getToUid() {
        return mToUserInfo != null ? mToUserInfo.getUid() : -1;
    }

    @Override
    public String getContent() {
        return mContentEt.getText().toString();
    }

    @Override
    public void setContent(String content) {
        mContentEt.setText("");
    }

    @Override
    public void setAdapter(int toUid, List<Message> list) {
        if (mAdapter == null) {
            mAdapter = new ChatAdapter(toUid, mToUserInfo, list);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setList(list);
        }
    }

    @Override
    public void addAdapter(int toUid, List<Message> list) {
        if (mAdapter == null) {
            mAdapter = new ChatAdapter(toUid, mToUserInfo, list);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.addList(list);
        }
    }

    @Override
    public void notifyData() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            mRecyclerView.smoothScrollToPosition(mAdapter.getCount());
        }
    }

    private BroadcastReceiver mChatReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            JniResponse response = intent.getParcelableExtra("response");
            if (response == null)
                return;
            if (String.valueOf(NetInfo.QHC_CMD_SEND_CHAT_MSG_RSP).equals(action)) {


            } else if (String.valueOf(NetInfo.QHC_CMD_RECV_CHAT_MSG).equals(action) && response.getObj() instanceof ChatMessage) {
                Message message = intent.getParcelableExtra("message");
                mPresenter.recvText(message);
            } else if (String.valueOf(NetInfo.QHC_CMD_UPLOAD_FILE_RSP).equals(action) && response.getObj() instanceof UploadFileResultInfo) {
                UploadFileResultInfo info = (UploadFileResultInfo) response.getObj();
                if (info.getExist() == 0) {
                    Log.i(TAG, "文件不存在");
                    mPresenter.uploadFileFinish(info, response.getSeq());
                } else {
                    Log.i(TAG, "文件存在");
                    mPresenter.sendImage(info.getFileIds(), response.getSeq());
                }
            } else if (String.valueOf(NetInfo.QHC_CMD_FINISH_UPLOAD_FILE_RSP).equals(action) && response.getObj() instanceof UploadFileFihishiInfo) {
                UploadFileFihishiInfo info = (UploadFileFihishiInfo) response.getObj();
                mPresenter.sendImage(info.getFileId(), response.getSeq());
            } else if (String.valueOf(NetInfo.QHC_CMD_DOWN_FILE_RSP).equals(action) && response.getObj() instanceof DownloadFileResultInfo) {
                //noinspection unchecked
                DownloadFileResultInfo info = (DownloadFileResultInfo) response.getObj();
                mPresenter.updateImageUrl(info.getDown_url(), response.getSeq());
            }

        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_PICTURE:
                if (resultCode == RESULT_OK) {
                    List<String> pictureList = data.getStringArrayListExtra("select_list");
                    if (pictureList != null && !pictureList.isEmpty()) {
                        mPresenter.uploadFile(pictureList.get(0));
                    }
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.clear();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mChatReceiver);
        super.onDestroy();
    }
}
