package com.xtdar.app.widget.pinyin;


import com.xtdar.app.model.User;

import java.util.Comparator;


/**
 *
 * @author
 *
 */
public class PinyinComparator implements Comparator<User> {


    public static PinyinComparator instance = null;

    public static PinyinComparator getInstance() {
        if (instance == null) {
            instance = new PinyinComparator();
        }
        return instance;
    }

    public int compare(User o1, User o2) {
        if (o1.getLetters().equals("@")
                || o2.getLetters().equals("#")) {
            return -1;
        } else if (o1.getLetters().equals("#")
                   || o2.getLetters().equals("@")) {
            return 1;
        } else {
            return o1.getLetters().compareTo(o2.getLetters());
        }
    }

}
