package reflexDemo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class reflexExample {
    public static void main(String []args) throws Exception {
        List<Integer> integerList = new ArrayList<>();

        integerList.add(123);
        integerList.add(23);
        integerList.add(44);

        Class listCls = integerList.getClass();
        Method method = listCls.getDeclaredMethod("add", Object.class);
        method.invoke(integerList,"1add");

        System.out.println(integerList);
    }
}
