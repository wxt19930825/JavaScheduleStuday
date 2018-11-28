package reflexDemo;


interface demoI{
     String getName();
     int getInteger();
}


class DemoSuper{
    private String username;
    private String password;
    public String state;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}

public class Demo extends  DemoSuper implements  demoI{


    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getInteger() {
        return 0;
    }
}
