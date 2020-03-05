package order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class QuickSortTest {
 @Test
    public void testA(){
     System.out.println("aaaaaa");
 }

    @Test
    public void testSort(){
       QuickSort qs = new QuickSort();
       List<Integer> alist = new ArrayList<>();
       alist.add(9);
        alist.add(4);
        alist.add(5);
        alist.add(7);
        alist.add(110);
        System.out.println(System.currentTimeMillis());
        qs.sort(alist);
        System.out.println(System.currentTimeMillis());
        System.out.println(alist);

    }

    @Test
    public void testQickSort() {
        QuickSort qs = new QuickSort();
        int arr[] = new int[]{9, 4, 5, 7, 110};
        System.out.println(System.currentTimeMillis());
        qs.QuickSort(arr,0,arr.length-1);
        System.out.println(System.currentTimeMillis());
    }

}