package order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
public class InsertSortTest {

    @Test
    public void  testInsert(){
        InsertSort is = new InsertSort();
        int[] arry = new int[]{0,2,1,3,4,39,34,7};
        is.InsertSort(arry);


        for (int i : arry){
            System.out.println(i);
        }


    }



}