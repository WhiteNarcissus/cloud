package order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
public class BubbleSortTest {

    @Test
    public  void testBubbleSort(){
        BubbleSort bs = new BubbleSort();
        int[] arry = new int[]{0,2,1,3,4,39,34,7};
        bs.BubbleSort(arry);

        System.out.println(arry[7]);

    }

}