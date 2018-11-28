package reflexDemo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

class Demo1{

    public String para1;
    public  int para2;

    private int privatePara1;
    private String privatePara2;

    Demo1(String para){
        System.out.println("反射调用有1个参构造方法 string");
    }

    public Demo1(int privatePara1, String privatePara2) {
        this.privatePara1 = privatePara1;
        this.privatePara2 = privatePara2;
    }

    @Override
    public String toString() {
        return "para1："+para1 +",para2:"+para2+
                ",privatePara1:"+privatePara1+",privatePara2:"+privatePara2;
    }
}
public class Main {
    public static  void main(String []args) throws  Exception {
        Class<?> demoCls = Class.forName("reflexDemo.Demo1");

        //getConstructors()方法只能获取反射类的公共构造函数（public）
        Constructor constructors[] =demoCls.getConstructors();
        System.out.println("getConstructors方法获取的方法数："+constructors.length);

        //getDeclaredConstructors()方法获取所有的构造函数
        Constructor  declaredConstructors[] = demoCls.getDeclaredConstructors();
        System.out.println("getDeclaredConstructors()方法获取的方法数："+declaredConstructors.length);


        System.out.println(demoCls.getSimpleName()+"的方法有如下几个");
        for (int i=0;i<declaredConstructors.length;i++){
            Constructor constructor = declaredConstructors[i];
            Class[] paraTypes = constructor.getParameterTypes();
            System.out.print(constructor.getName()+"(");
            for (int j = 0; j < paraTypes.length; j++) {
                Class paraType = paraTypes[j];
                if (j==(paraTypes.length-1)){
                    System.out.print(""+paraType.getSimpleName()+")");
                    break;
                }
                System.out.print(""+paraType.getSimpleName()+",");
            }
            System.out.println();
        }

        //getFields()方法获取所有的公共变量（public）；
        Field fields[] = demoCls.getFields();
        System.out.println(demoCls.getSimpleName()+"类有"+fields.length+"公共变量");

        //getDeclaredFields()获取所有的变量
        Field declaredFields[] = demoCls.getDeclaredFields();
        System.out.println(demoCls.getSimpleName()+"类有"+declaredFields.length+"个变量");

        for (int i = 0; i <declaredFields.length ; i++) {
            Field field = declaredFields[i];
            System.out.println(Modifier.toString(field.getModifiers())+
                    " "+ field.getType().getSimpleName() +" "+field.getName());
        }

        Constructor constructor = demoCls.getDeclaredConstructor(int.class,String.class);
        Demo1 demo1 = (Demo1) constructor.newInstance(123,"wxt");

        System.out.println("反射前得到的实例对象是："+demo1.toString());

        Field publicField = demoCls.getDeclaredField("para1");
        publicField.set(demo1,"liming");
        System.out.println("反射后,对公有变量赋值后得到的实例对象是："+demo1.toString());


        Field privateFiled = demoCls.getDeclaredField("privatePara1");
        privateFiled.setAccessible(true);
        privateFiled.set(demo1,1);
        System.out.println("反射后,对私有变量赋值后得到的实例对象是："+demo1.toString());

    }
}
