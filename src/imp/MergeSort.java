package imp;

public class MergeSort{

    private static void merge(int[] arr, int left, int middle, int right) {

        int size1 = middle - left + 1;
        int size2 = right - middle;

        /* Create temp arrays */
        int[] Left = new int [size1];
        int[] Right = new int [size2];

        /*Copy data to temp arrays*/
        for (int i=0; i<size1; ++i) {
            Left[i] = arr[left + i];
        }
        for (int j=0; j<size2; ++j) {
            Right[j] = arr[middle + 1 + j];
        }

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarry array
        int k = left;
        while (i < size1 && j < size2)
        {
            if (Left[i] <= Right[j])
            {
                arr[k] = Left[i];
                i++;
            }
            else
            {
                arr[k] = Right[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < size1)
        {
            arr[k] = Left[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < size2)
        {
            arr[k] = Right[j];
            j++;
            k++;
        }
    }

    // Main function that sorts arr[l..r] using
    // merge()
    static void sort(int[] arr, int left, int right) {
        if (left < right)
        {
            // Find the middle point
            int m = (left+right)/2;

            // Sort first and second halves
            sort(arr, left, m);
            sort(arr , m+1, right);

            // Merge the sorted halves
            merge(arr, left, m, right);
        }
    }

    private static void merge(long[] arr, int left, int middle, int right) {

        int size1 = middle - left + 1;
        int size2 = right - middle;

        /* Create temp arrays */
        long[] Left = new long [size1];
        long[] Right = new long [size2];

        /*Copy data to temp arrays*/
        for (int i=0; i<size1; ++i) {
            Left[i] = arr[left + i];
        }
        for (int j=0; j<size2; ++j) {
            Right[j] = arr[middle + 1 + j];
        }

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarry array
        int k = left;
        while (i < size1 && j < size2)
        {
            if (Left[i] <= Right[j])
            {
                arr[k] = Left[i];
                i++;
            }
            else
            {
                arr[k] = Right[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < size1)
        {
            arr[k] = Left[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < size2)
        {
            arr[k] = Right[j];
            j++;
            k++;
        }
    }

    // Main function that sorts arr[l..r] using
    // merge()
    static void sort(long[] arr, int left, int right) {
        if (left < right)
        {
            // Find the middle point
            int m = (left+right)/2;

            // Sort first and second halves
            sort(arr, left, m);
            sort(arr , m+1, right);

            // Merge the sorted halves
            merge(arr, left, m, right);
        }
    }
}