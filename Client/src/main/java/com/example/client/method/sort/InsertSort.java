package com.example.client.method.sort;

/**
 * Created by Qiu on 2017/12/21.
 */

public class InsertSort {
    private static int[] data = {1, 2, 5, 76, 8, 9};

    public static void main(String[] args) {
        insertSort(data);
    }

    private static void insertSort(int[] data) {
        int temp;
        for (int i = 1; i < data.length; i++) {
            if (data[i] < data[i - 1]) {
                temp = data[i];
                int j;
                for (j = i - 1; j >= 0 && data[j] > temp; j--) {
                    data[j + 1] = data[j];
                }
                data[j + 1] = temp;
            }
        }
    }
}
