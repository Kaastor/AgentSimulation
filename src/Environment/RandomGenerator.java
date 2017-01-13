package Environment;


import dissim.random.SimGenerator;

public class RandomGenerator extends SimGenerator{
    private final static SimGenerator ourInstance = new SimGenerator();

    public static SimGenerator getInstance() {
        return ourInstance;
    }

    private RandomGenerator() {
    }
}
