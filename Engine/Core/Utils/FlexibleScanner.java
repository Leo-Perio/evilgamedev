package Engine.Core.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class FlexibleScanner {
    private Scanner scanner;

    public FlexibleScanner(String in) {scanner = new Scanner(in);}
    public FlexibleScanner(InputStream in) {scanner = new Scanner(in);}
    public FlexibleScanner(File file) throws FileNotFoundException {scanner = new Scanner(file);}

    public String nextLine() {return scanner.nextLine();}
    public String next() {return scanner.next();}
    public long nextLong() {return scanner.nextLong();}
    public int nextInt() {return scanner.nextInt();}
    public double nextDouble() {return scanner.nextDouble();}
    public boolean hasNextLine() {return scanner != null && scanner.hasNextLine();}

    public void setInput(File file) throws FileNotFoundException {scanner = new Scanner(file);}
    public void setInput(String in) {scanner = new Scanner(in);}
    public void setInput(InputStream in) {scanner = new Scanner(in);}

}
