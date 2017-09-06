package com.xtdar.app.view.activity;

import android.content.ContextWrapper;

import com.unity3d.player.UnityPlayer;

/**
 * Created by Administrator on 2017/9/6.
 */

public class UnityPlayerForMe extends UnityPlayer {

        public UnityPlayerForMe(ContextWrapper contextWrapper) {
            super(contextWrapper);
        }

        @Override
        protected void kill() {}

}
