import java.io.*;
import java.util.Arrays;
import java.util.BitSet;

public class Decoder {
    int[] frequencies = new int[256];
    TreeBuilder tb = new TreeBuilder();
    HuffmanNode root = new HuffmanNode();
    int fileLength = 0;

    void fillFrequencies(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String st = br.readLine();
            String[] firstSplit = st.split("&");
            String[] stt = firstSplit[1].split(";");
            fileLength = Integer.parseInt(firstSplit[0]);
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

    void decodeFile(String innfile, String outfile, String key){
        fillFrequencies(key);
        buildTree();
        String st = "";
        try{
            DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream(innfile)));
            DataOutputStream utfil = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outfile)));

            BitSet bs = BitSet.valueOf(getByte("src//goal.txt"));
            String bits = bitSetToString(bs);
            System.out.println(bits);
            int iterator = 0;
            for(int i=0;i<fileLength;i++){
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
                st += curr.c;
            }
            utfil.writeBytes(st);
            System.out.println(st);
            utfil.flush();
            utfil.close();
        }catch (IOException e){
            e.printStackTrace();
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
        dc.decodeFile("src//goal.txt","src//unencrypt.txt","src//frequencytable.txt");
    }
}


