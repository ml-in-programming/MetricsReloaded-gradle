import java.util.List;

abstract public class A {
    volatile public int a = 0;
    volatile private double b = 0;
    volatile protected List<Integer> c = null;
    final int d = 0;

    public int aMethod() {
        return 0;
    }

    static private double bMethod() {
        return 0.0;
    }

    abstract protected List cMethod();

    int dMethod() {
        return 1;
    }

    public class B {
        volatile public int r = 0;
        final private double q = 0;
        final public int e = 0;
    }

    public static class C {
        final public int u = 0;
        volatile static protected double i = 0;
        public int x;
        public int o;
    }
}