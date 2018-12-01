package ProxyDemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface  FoodDynmic{
    void eat();
}

/**
 * 代理模式核心业务处理
 */
class RealFoodDynmic  implements FoodDynmic{
    @Override
    public void eat() {
        System.out.println("李明吃饭，吃的很香");
    }
}

/**
 * 创建StuInvocationHandler类，
 * 这个类中持有一个被代理对象的实例target。
 * InvocationHandler中有一个invoke方法，所有执行代理对象的方法都会被替换成执行invoke方法。
 * 再在invoke方法中执行被代理对象target的相应方法。
 * 当然，在代理过程中，我们在真正执行被代理对象的方法前加入自己其他处理。
 *
 * 这也是Spring中的AOP实现的主要原理，这里还涉及到一个很重要的关于java反射方面的基础知识。
 * @param <T>
 */

class FoodInvocationHandler<T> implements InvocationHandler {

    //invocationHandler持有的代理对象
    T target;

    public FoodInvocationHandler(T target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理执行" +method.getName() + "方法");

        Object result = method.invoke(target, args);
        return result;
    }
}


public class MainDynmic {

    public static  void main(String []args){
        //创建被代理的实例对象
        FoodDynmic foodDynmic = new RealFoodDynmic();

        //创建一个与代理对象相关联的InvocationHandler
        InvocationHandler foodInvHandler = new FoodInvocationHandler<FoodDynmic>(foodDynmic);

        ////创建一个代理对象foodDynmicProxy来代理foodDynmic，代理对象的每个执行方法都会替换执行Invocation中的invoke方法
        FoodDynmic foodDynmicProxy = (FoodDynmic)Proxy.newProxyInstance(
                foodDynmic.getClass().getClassLoader(),new Class[]{FoodDynmic.class},foodInvHandler);

        //代理执行吃的方法
        foodDynmicProxy.eat();

    }
}
