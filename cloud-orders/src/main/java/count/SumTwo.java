package count;


import java.util.HashMap;

/**
 *  问题描述
 *  给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 *
 * 示例:
 *
 * 给定 nums = [2, 7, 11, 15], target = 9
 *
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 *
 */
public class SumTwo {

    public int[] sumTwo(int[] nums , int target){


        int[] answer = new int[2];

        //最蠢的 方式一个一个加
        for(int i=0 ;i < nums.length-1 ;i++ ){
            for(int j=0 ;j<nums.length-1-i ;j++){
                int a = nums[i];
                int b = nums[j+1];
                if(a+b == target ){
                    answer[0] = i;
                    answer[1] = j+1;
                    return answer ;
                }
            }

        }


        return  answer ;
    }


    /**
     * @param nums
     * @param target
     * @return 牛皮啊  这个 算法  。先把补数 和 下标记录下来
     * 然后 用补数去 做匹配 匹配出来的数
     *  再获取 补数下标 和 现在正在运行的下标
     *  典型的空间  换时间的 概念
     * */
    public int[] sumTwo2(int[] nums , int target){

        int[] indexs = new int[2];
        // 建立k-v ，一一对应的哈希表
        HashMap<Integer,Integer> hash = new HashMap<Integer,Integer>();
        for(int i = 0; i < nums.length; i++){
            if(hash.containsKey(nums[i])){
                indexs[0] = i;
                indexs[1] = hash.get(nums[i]);
                return indexs;
            }
            // 将数据存入 key为补数 ，value为下标
            hash.put(target-nums[i],i);
        }
        // // 双重循环 循环极限为(n^2-n)/2
        // for(int i = 0; i < nums.length; i++){
        //     for(int j = nums.length - 1; j > i; j --){
        //         if(nums[i]+nums[j] == target){
        //            indexs[0] = i;
        //            indexs[1] = j;
        //            return indexs;
        //         }
        //     }
        // }
        return indexs;

    }

}
