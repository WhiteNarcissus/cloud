package count;

import org.junit.Test;

import static org.junit.Assert.*;

public class SumTwoTest {


    @Test
    public void   test(){

        SumTwo sumTwo = new SumTwo();
        int[] a = new int[2] ;
        int[] b = new int[2];
        int[] nums = new int[]{2,7,11,115};
        a=sumTwo.sumTwo(nums,9);
        b= sumTwo.sumTwo2(nums,9);
         System.out.println(a[1]+""+a[0]);
         System.out.println(b[1]+""+b[0]);


    }

}