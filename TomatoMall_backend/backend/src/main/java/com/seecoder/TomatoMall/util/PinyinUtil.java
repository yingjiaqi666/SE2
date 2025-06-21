package com.seecoder.TomatoMall.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {
    private static final HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();

    static {
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    }

    // 获取汉字全拼
    public static String getFullPinyin(String chinese) {
        try {
            StringBuilder pinyin = new StringBuilder();
            for (char c : chinese.toCharArray()) {
                if (Character.toString(c).matches("[\\u4E00-\\u9FA5]")) {
                    String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    if (pinyins != null && pinyins.length > 0) {
                        pinyin.append(pinyins[0]);
                    }
                } else {
                    pinyin.append(c);
                }
            }
            return pinyin.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            return chinese;
        }
    }

    // 获取拼音首字母
    public static String getInitialPinyin(String chinese) {
        try {
            StringBuilder initials = new StringBuilder();
            for (char c : chinese.toCharArray()) {
                if (Character.toString(c).matches("[\\u4E00-\\u9FA5]")) {
                    String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    if (pinyins != null && pinyins.length > 0) {
                        initials.append(pinyins[0].charAt(0));
                    }
                } else {
                    initials.append(c);
                }
            }
            return initials.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            return chinese;
        }
    }
}