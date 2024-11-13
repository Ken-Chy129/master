package cn.ken.master.core.util;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Ken-Chy129
 * @date 2024/8/12
 */
public class StringUtil {

    public static boolean equals(String str1, String str2) {
        return Objects.equals(str1, str2);
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static <T> Set<T> split(String str, String delimiter, Function<String, T> converter) {
        if (isEmpty(str)) {
            return Collections.emptySet();
        }
        return Arrays.stream(str.split(delimiter))
                .filter(StringUtil::isNotBlank)
                .map(String::trim)
                .map(converter)
                .collect(Collectors.toSet());
    }
}
