package com.hanor.oa;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Test {
    public static void main(String[] args) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println(Timestamp.valueOf(dateFormat.format(new java.util.Date())));
    }
}
