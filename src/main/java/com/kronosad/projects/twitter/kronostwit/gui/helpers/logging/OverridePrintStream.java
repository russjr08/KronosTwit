package com.kronosad.projects.twitter.kronostwit.gui.helpers.logging;

import com.kronosad.projects.twitter.kronostwit.gui.WindowConsole;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 *
 * @author Russell
 */
public class OverridePrintStream extends PrintStream {
    private OutputStream out;
    private WindowConsole console;

    public OverridePrintStream(OutputStream out, WindowConsole console) {
        super(out);
        this.out = out;
        this.console = console;

    }



    @Override
    public void println(Object x) {
        super.println(x);
        console.appendObjectToConsole(x);

    }

    @Override
    public void println(String x) {
        super.println(x);
        console.appendToConsole(x);
    }

    @Override
    public void println(char[] x) {
        super.println(x);
        console.appendObjectToConsole(x);
    }

    @Override
    public void println(double x) {
        super.println(x);
        console.appendObjectToConsole(x);
    }

    @Override
    public void println(float x) {
        super.println(x);
        console.appendObjectToConsole(x);
    }

    @Override
    public void println(long x) {
        super.println(x);
        console.appendObjectToConsole(x);
    }

    @Override
    public void println(int x) {
        super.println(x);
        console.appendObjectToConsole(x);
    }

    @Override
    public void println(char x) {
        super.println(x);
        console.appendObjectToConsole(x);
    }

    @Override
    public void println(boolean x) {
        super.println(x);
        console.appendObjectToConsole(x);
    }

    @Override
    public void println() {
        super.println();
        console.appendToConsole("\n");
    }

    @Override
    public void write(byte[] buf, int off, int len) {
        super.write(buf, off, len);
    }

    @Override
    public void write(byte[] b) throws IOException {
        super.write(b);
    }



}