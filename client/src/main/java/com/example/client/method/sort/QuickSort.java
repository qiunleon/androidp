package com.example.client.method.sort;

/**
 * Created by Qiu on 2017/12/21.
 */

public class QuickSort {
    private static int[] data = {1, 2, 5, 76, 8, 9};

    public static void main(String[] args) {
        quickSort(data, 0, data.length - 1);
    }

    private static void quickSort(int[] data, int l, int r) {
        int i = 0;
        int j = 0;
        int x = 0;

        if (l < r) {
            i = l;
            j = r;
            x = data[l];
            while (i < j) {
                while (i < j && data[j] >= x) {
                    j--;
                }
                if (i < j) {
                    data[i++] = data[j];
                }
                while (i < j && data[i] <= x) {
                    i++;
                }
                if (i < j) {
                    data[j--] = data[i];
                }
            }
            data[i] = x;
            quickSort(data, l, i - 1);
            quickSort(data, i + 1, r);
        }
    }
}
