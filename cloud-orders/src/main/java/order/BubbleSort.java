package order;

public class BubbleSort {




    /// 冒泡排序 轻的往上冒
    //缺点  时间复杂度高
    /// <param name="arry">要排序的整数数组</param>
    public  void BubbleSort(int[] arry)
    {
        for (int i = 0; i < arry.length-1; i++)
        {
            for (int j = 0; j < arry.length - 1 - i; j++)
            {
                //比较相邻的两个元素，如果前面的比后面的大，则交换位置
                if (arry[j] > arry[j + 1])
                {
                    int temp = arry[j + 1];
                    arry[j + 1] = arry[j];
                    arry[j] = temp;
                }
            }
        }
    }
}
