package com.seecoder.TomatoMall.util;

import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SimilarityUtil {

    // 计算编辑距离相似度（0~1范围）
    public static double levenshteinRatio(String s1, String s2) {
        int maxLen = Math.max(s1.length(), s2.length());
        if (maxLen == 0) return 1.0;

        int distance = LevenshteinDistance.getDefaultInstance().apply(s1, s2);
        return 1 - (double) distance / maxLen;
    }

    // 计算Jaccard相似度（适合短文本）
    public static double jaccardSimilarity(String s1, String s2) {
        Set<Character> set1 = s1.chars().mapToObj(c -> (char)c).collect(Collectors.toSet());
        Set<Character> set2 = s2.chars().mapToObj(c -> (char)c).collect(Collectors.toSet());

        Set<Character> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        Set<Character> union = new HashSet<>(set1);
        union.addAll(set2);

        return union.isEmpty() ? 0 : (double) intersection.size() / union.size();
    }
}