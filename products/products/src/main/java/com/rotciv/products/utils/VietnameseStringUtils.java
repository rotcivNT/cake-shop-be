package com.rotciv.products.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class VietnameseStringUtils {

    public static String toLowerCaseNonAccentVietnamese(String str) {
        str = str.toLowerCase();

        // Replace accented characters with non-accented equivalents
        str = str.replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a");
        str = str.replaceAll("[èéẹẻẽêềếệểễ]", "e");
        str = str.replaceAll("[ìíịỉĩ]", "i");
        str = str.replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o");
        str = str.replaceAll("[ùúụủũưừứựửữ]", "u");
        str = str.replaceAll("[ỳýỵỷỹ]", "y");
        str = str.replaceAll("[đ]", "d");

        // Remove combining diacritical marks
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        return str;
    }
}

