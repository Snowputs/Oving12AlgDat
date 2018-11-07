public class UnsignByte {
    public static int unsignedByte(byte b) {
        int ret = 0;
        int unsignedByte = b & 0xFF;
        /*for(int i=0;i<8;i++){
            if(1==(b>>i & 1)){
                ret += Math.pow(2,i);
            }
        }*/
        return unsignedByte;
    }

    public static void main(String[] args) {
        int x = 4;
        System.out.println((Integer.toBinaryString(x)));
    }
}
