package com.pusvo;

public class PrinterCommands {
    public static final byte LF = 0x0A;
    public static byte[] FEED_LINE = {10};

    public static final byte[] ESC_ALIGN_LEFT = new byte[] { 0x1b, 'a', 0x00 };
    public static final byte[] ESC_ALIGN_RIGHT = new byte[] { 0x1b, 'a', 0x02 };
    public static final byte[] ESC_ALIGN_CENTER = new byte[] { 0x1b, 'a', 0x01 };
    public static final byte[] ESC_CANCEL_BOLD = new byte[] { 0x1B, 0x45, 0 };
    //change[2] = (byte) (0x10); //large
    //change[2] = (byte) (0x3); //small

    //change[2] = (byte) (0x10 | 0x8); //large bold
    // change[2] = (byte) (0x8); bold medium
    // change[2] = (byte) (0x3 | 0x8);  small bold


    //outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
    //outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
    // outputStream.write(PrinterCommands.ESC_ALIGN_RIGHT);
}