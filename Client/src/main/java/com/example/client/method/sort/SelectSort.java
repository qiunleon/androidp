package com.example.client.method.sort;

/**
 * Created by Qiu on 2017/12/21.
 */

public class SelectSort {
    private static int[] data = {1, 2, 5, 76, 8, 9};

    public static void main(String[] args) {
        selectSort(data);
    }

    private static void selectSort(int[] data) {
        int temp;
        int min;
        for (int i = 0; i < data.length; i++) {
            min = i;
            for (int j = i + 1; j < data.length; j++) {
                if (data[j] < data[min]) {
                    min = j;
                }
            }
            temp = data[min];
            data[min] = data[i];
            data[i] = temp;
        }
    }
}
