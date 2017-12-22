package com.example.client.method.sort;

/**
 * Created by Qiu on 2017/12/21.
 */
public class BubbleSort {

    private static int[] data = {1, 2, 5, 76, 8, 9};

    public static void main(String[] args) {
        bubbleSort(data);
    }

    private static void bubbleSort(int[] data) {
        int temp;
        for (int i = 0; i < data.length; i++) {
            for (int j = 1; j < data.length - i; j++) {
                if (data[j - 1] > data[j]) {
                    temp = data[j - 1];
                    data[j - 1] = data[j];
                    data[j] = temp;
                }
            }
        }
    }
}
