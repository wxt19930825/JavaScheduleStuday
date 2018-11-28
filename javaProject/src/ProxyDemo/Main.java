package ProxyDemo;

interface Food{
    void eat();
}

class RealFood  implements Food{
    @Override
    public void eat() {
        System.out.println("黎明吃饭，吃的很香");
    }
}


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
