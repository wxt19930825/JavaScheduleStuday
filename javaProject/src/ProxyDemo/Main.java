package ProxyDemo;

/**
 * 代理模式一个接口
 */
interface Food{
    void eat();
}

/**
 * 代理模式核心业务处理
 */
class RealFood  implements Food{
    @Override
    public void eat() {
        System.out.println("李明吃饭，吃的很香");
    }
}


/**
 * 代理模式辅助业务操作
 */
class ProxyFood implements  Food{
    private Food food;

    public  Food bind(Food food){
        this.food = food;
        return  this;
    }

    public  void prepare(){
        System.out.println("吃饭前的准备。。。。。。。");
    }

    public  void after(){
        System.out.println("吃饭后的准备。。。。。。。");
    }

    @Override
    public void eat() {
        prepare();
        this.food.eat();
        after();
    }
}

public class Main {

    public static  void main(String []args){

        Food proxyFood = new ProxyFood().bind(new RealFood());
        proxyFood.eat();
    }
}
