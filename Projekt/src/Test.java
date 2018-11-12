public class Test{

    private String name;

    public Test(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return "TEST: " + name;
    }
}
