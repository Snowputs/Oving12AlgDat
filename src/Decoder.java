import java.io.*;
import java.util.Arrays;
import java.util.BitSet;

public class Decoder {
    static final int SHOVE = 128;
    int[] frequencies = new int[256];
    TreeBuilder tb = new TreeBuilder();
    HuffmanNode root = new HuffmanNode();

    void fillFrequencies() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src//frequencytable.txt"));
            String st = br.readLine();
            String[] stt = st.split(";");
            for(int i=0;i<frequencies.length;i++){
                frequencies[i] = Integer.parseInt(stt[i]);
                //System.out.println(frequencies[i]);
            }
        }catch (IOException e){
            System.out.println(e);
        }
    }

    void buildTree(){
        root = tb.buildTree(frequencies);
    }

    void decodeFile(){
        fillFrequencies();
        buildTree();
        String st = "";
        try{
           // boolean flag = true;
            DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream("src//goal.txt")));
            //HuffmanNode current = root;
            BitSet bs = BitSet.valueOf(getByte("src//goal.txt"));
            String bits = bitSetToString(bs);
            System.out.println(bits);
            int iterator = 0;
            for(int i=0;i<1000;i++){
                HuffmanNode curr = root;
                while(!curr.isLeaf() && iterator<bits.length()){
                    if(bits.charAt(iterator) == '0'){
                       curr = curr.left;
                       iterator++;
                    }else {
                        curr = curr.right;
                        iterator++;
                    }
                }
                st += curr.c;/*
                    if(!current.isLeaf() && bits.charAt(i)=='0'){
                        current = current.left;
                        System.out.println("0");
                    }else if(!current.isLeaf() && bits.charAt(i)=='1'){
                        current = current.right;
                        System.out.println("1");
                    }else{
                        //st += current.c;
                    }*/
            }

            System.out.println(st);
        }catch (IOException e){
            System.out.println(e);
        }
    }

    public static byte[] getByte(String path){
        byte[] getBytes = {};
        try{
            File file = new File(path);
            getBytes = new byte[(int) file.length()];
            InputStream is = new FileInputStream(file);
            is.read(getBytes);
            is.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return getBytes;
    }

    public String bitSetToString(BitSet k){
        String p = "";
        for(int i=0;i<k.size();i++){
            boolean t = k.get(i);
            if (t){
                p+="1";
            }else{
                p+="0";
            }
        }
        return p;
    }

    public static void main(String[] args) {
        Decoder dc = new Decoder();
        dc.decodeFile();
    }
}


