package core.view;

import java.util.Scanner;

public class ConsoleView implements IView {

    private Scanner in;

    public ConsoleView() {

        this.in = new Scanner(System.in);

    }

    @Override
    public String get() {
        
        return in.next();

    }

    @Override
    public void set(String str) {
        
        System.out.println(str);

    }
    
}
