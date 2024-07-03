package cfg;

public class Feline {
    public String type = "f";

    public Feline() {
        System.out.print("feline");
    }

    void go() {
        System.out.print(this.type);
    }
}

class Cougar extends Feline {
    public Cougar() {
        System.out.print("cougar");
    }

    public static void main(String[] args) {
        new Cougar().go();
    }

    void go() {
        type = "c";
        System.out.println(this.type + super.type);
    }
}
