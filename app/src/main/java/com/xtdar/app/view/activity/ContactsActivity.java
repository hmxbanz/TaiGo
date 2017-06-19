package com.xtdar.app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xtdar.app.model.User;
import com.xtdar.app.model.UserList;
import com.xtdar.app.widget.pinyin.CharacterParser;
import com.xtdar.app.widget.pinyin.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.xtdar.app.R;
import com.xtdar.app.adapter.FriendListAdapter;
import com.xtdar.app.view.widget.SelectableRoundedImageView;
import com.xtdar.app.widget.pinyin.PinyinComparator;


public class ContactsActivity extends BaseActivity implements View.OnClickListener {
    private SelectableRoundedImageView mSelectableRoundedImageView;
    private TextView mNameTextView;
    private TextView mNoFriends;
    private TextView mUnreadTextView;
    private View mHeadView;
    private EditText mSearchEditText;
    private ListView mListView;
    private PinyinComparator mPinyinComparator;
    private SideBar mSidBar;
    /**
     * 中部展示的字母提示
     */
    private TextView mDialogTextView;

    private List<User> mFriendList;
    private List<User> mFilteredFriendList;
    /**
     * 好友列表的 mFriendListAdapter
     */
    private FriendListAdapter mFriendListAdapter;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser mCharacterParser;
    /**
     * 根据拼音来排列ListView里面的数据类
     */

    private String mId;
    private String mCacheName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        txtTitle =(TextView) findViewById(R.id.text_title);
        txtTitle.setText("好友列表");

        initView();
        initData();
        updateFriendsList(mFriendList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_register:
                startActivity(new Intent(this,RegisterActivity.class));
                break;

        }
    }

    private void initView() {
        mSearchEditText = (EditText) findViewById(R.id.search);
        mListView = (ListView) findViewById(R.id.listview);
        mNoFriends = (TextView) findViewById(R.id.show_no_friend);
        mSidBar = (SideBar) findViewById(R.id.sidrbar);
        mDialogTextView = (TextView) findViewById(R.id.group_dialog);
        mSidBar.setTextView(mDialogTextView);

        LayoutInflater mLayoutInflater = LayoutInflater.from(this);
        mHeadView = mLayoutInflater.inflate(R.layout.item_contact_list_header,null);
        mUnreadTextView = (TextView) mHeadView.findViewById(R.id.tv_unread);
        RelativeLayout newFriendsLayout = (RelativeLayout) mHeadView.findViewById(R.id.re_newfriends);

        mListView.addHeaderView(mHeadView);
        mNoFriends.setVisibility(View.VISIBLE);
        newFriendsLayout.setOnClickListener(this);

        //设置右侧触摸监听
        mSidBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = mFriendListAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mListView.setSelection(position);
                }

            }
        });
    }

    private void initData() {
        //mFriendList = new ArrayList<>();
        mFriendList= UserList.getData();
        FriendListAdapter adapter = new FriendListAdapter(this, mFriendList);
        mListView.setAdapter(adapter);
        mFilteredFriendList = new ArrayList<>();
        //实例化汉字转拼音类
        mCharacterParser = CharacterParser.getInstance();
        mPinyinComparator = PinyinComparator.getInstance();
    }

    private void updateFriendsList(List<User> friendsList) {
        //updateUI fragment初始化和好友信息更新时都会调用,isReloadList表示是否是好友更新时调用
        boolean isReloadList = false;
        if (mFriendList != null && mFriendList.size() > 0) {
            //mFriendList.clear();
            //isReloadList = true;
        }
        mFriendList = friendsList;
        if (mFriendList != null && mFriendList.size() > 0) {
            handleFriendDataForSort();
            mNoFriends.setVisibility(View.GONE);
        } else {
            mNoFriends.setVisibility(View.VISIBLE);
        }
        mSidBar.setVisibility(View.VISIBLE);
        // 根据a-z进行排序源数据
        Collections.sort(mFriendList, mPinyinComparator);
        if (isReloadList) {
            mFriendListAdapter.updateListView(mFriendList);
        } else {
            mFriendListAdapter = new FriendListAdapter(this, mFriendList);
            mListView.setAdapter(mFriendListAdapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (mListView.getHeaderViewsCount() > 0) {
                        startFriendDetailsPage(mFriendList.get(position - 1));
                    } else {
                        startFriendDetailsPage(mFilteredFriendList.get(position));
                    }
                }
            });


        }
    }

    private void handleFriendDataForSort() {
        for (User friend : mFriendList) {
                String letters = replaceFirstCharacterWithUppercase(friend.getNameSpelling());
                friend.setLetters(letters);
        }
    }

    private String replaceFirstCharacterWithUppercase(String spelling) {
        if (!TextUtils.isEmpty(spelling)) {
            char first = spelling.charAt(0);
            char newFirst = first;
            if (first >= 'a' && first <= 'z') {
                newFirst -= 32;
            }
            return spelling.replaceFirst(String.valueOf(first), String.valueOf(newFirst));
        } else {
            return "#";
        }
    }

    private void startFriendDetailsPage(User user) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("type", 2);
        //intent.putExtra("user", user);
        startActivity(intent);
    }
}
