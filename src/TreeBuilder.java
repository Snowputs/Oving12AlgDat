import java.io.*;
import java.util.BitSet;
import java.util.PriorityQueue;

public class TreeBuilder {
    private final int SHOVE = 128;
    int[] frequencies = new int[256];
    String[] data2 = new String[256];
    int fileLength = 0;

    public  void buildTable(String path){
        try {
            File file = new File(path);
            DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
            fileLength = (int) file.length();
            byte x;
            while (true){
                try {
                    x = innfil.readByte();
                    //System.out.println(Integer.toBinaryString(UnsignByte.unsignedByte(x)) + " - " + (int) x);
                    frequencies[UnsignByte.unsignedByte(x)]++;

                    //System.out.print((char) x);
                }catch (EOFException e){
                    break;
                }
            }
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("src//frequencytable.txt"), "utf-8"))) {
                writer.write(fileLength + "&");
                for(int i=0;i<frequencies.length;i++){
                    writer.write(frequencies[i]+";");
                }
            }
        }catch (IOException e){
            System.out.println(e);
        }
        buildTree(frequencies);
    }

    public HuffmanNode buildTree(int[] ft){
            PriorityQueue<HuffmanNode> hQueue = new PriorityQueue<>(ft.length, new MyComparator());
            for (int i=0;i<ft.length;i++){
                if(ft[i] == 0) {continue;}
                HuffmanNode node = new HuffmanNode();
                int v = i;
                node.c = (char) v;
                node.freq = ft[i];

                node.left = null;
                node.right = null;

                hQueue.add(node);
            }

            HuffmanNode root = null;

            while (hQueue.size()>1){
                HuffmanNode one = new HuffmanNode();
                HuffmanNode two = new HuffmanNode();

                do {
                    one = hQueue.poll();
                }while(one.freq==0);
                do {
                    two = hQueue.poll();
                }while(two.freq==0);
                HuffmanNode newInnerNode = new HuffmanNode();

                newInnerNode.freq = one.freq + two.freq;

                newInnerNode.left = one;
                newInnerNode.right = two;

                root = newInnerNode;
                hQueue.add(newInnerNode);
            }
            //buildBook(root, 0b0);
            printCode(root, "");
        return root;
    }

//    public void buildBook(HuffmanNode root, long long1)
//    {
//        if (root.isLeaf()) {
//            data[root.c]=long1;
//            return;
//        }
//
//        long v = (long1<<1);
//        long j = (long1<<1);
//        v += 0b0;
//        j += 0b1;
//        buildBook(root.left, v);
//        buildBook(root.right, j);
//    }

    public void printCode(HuffmanNode root, String s)
    {
        if (root.isLeaf()) {
            data2[root.c] = s;
            return;
        }
        printCode(root.left, s + "0");
        printCode(root.right, s + "1");
    }

    public void compress(String innfile, String outfile){
        try {
            DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream(innfile)));
            DataOutputStream utfil = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outfile)));
            String st = "";
            while (true){
                try {
                    byte x = innfil.readByte();
                    int ex = UnsignByte.unsignedByte(x);
                    st += data2[ex];
                }catch (EOFException e){
                    break;
                }
            }
            innfil.close();
            System.out.println(st);

            BitSet temp = new BitSet();
            for(int j = 0; j < st.length(); j++){
                if(st.charAt(j)=='1'){
                    temp.set(j);
                }
            }

            byte[] outString = temp.toByteArray();
            utfil.write(outString);
            utfil.flush();
            utfil.close();
        }catch (IOException e){
            System.out.println("File machine broke on out");
        }

    }

    public static void main(String[] args) {
        TreeBuilder tb = new TreeBuilder();
        tb.buildTable("src//opg12.txt");

        tb.compress("src//opg12.txt","src//goal.txt");
    }

}
