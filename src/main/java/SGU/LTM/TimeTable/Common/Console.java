package SGU.LTM.TimeTable.Common;

import java.util.Scanner;

public class Console {
    private Scanner scanner;

    public Console() {
        this.scanner = new Scanner(System.in);
    }

    public String getInput() {
        return this.scanner.nextLine();
    }

    public void close() {
        this.scanner.close();
    }
}
