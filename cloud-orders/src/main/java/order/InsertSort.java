package order;

public class InsertSort {
    /// <summary>
    /// 直接插入排序
    /// </summary>
    /// <param name="arry">要排序的数组</param>
    public  void InsertSort( int[] arry)
    {
        //直接插入排序是将待比较的数值与它的前一个数值进行比较，所以外层循环是从第二个数值开始的
        for (int i = 1; i < arry.length; i++)
        {
            //如果当前元素小于其前面的元素
            if (arry[i] < arry[i - 1])
            {
                //用一个变量来保存当前待比较的数值，因为当一趟比较完成时，我们要将待比较数值置入比它小的数值的后一位
                int temp = arry[i];
                int j = 0;
                for (j = i - 1; j >= 0 && temp < arry[j]; j--)
                {
                    arry[j + 1] = arry[j];
                }
                arry[j + 1] = temp;
            }
        }
    }


}
