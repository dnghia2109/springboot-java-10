package vn.techmaster.bookonline.util;

import java.util.Arrays;
import java.util.List;

public class Utils {
    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    // Get file extension
    public static String getExtensionFile(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return fileName.substring(lastIndexOf + 1);
    }

    // Check file extension is in valid list
    public static boolean checkFileExtenstion(String fileExtension) {
        List<String> fileExtensions = Arrays.asList("png", "jpg", "jpeg", "webp");
        return fileExtensions.contains(fileExtension);
    }

    // Show list of string separate by comma and space
    public static String showList(List<String> list) {
        return String.join(", ", list);
    }
}
