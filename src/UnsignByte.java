public class UnsignByte {
    public static int unsignedByte(byte b) {
        int unsignedByte = b & 0xFF;
        return unsignedByte;
    }

    public static void main(String[] args) {
        byte x = -40;
        char y = 216;
        System.out.println(unsignedByte(x));
        System.out.println(y);

        System.out.println(unsignedByte((byte)"&".charAt(0)));
    }
}
