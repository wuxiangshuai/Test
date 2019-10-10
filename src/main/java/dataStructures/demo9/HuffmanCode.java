package dataStructures.demo9;

import java.io.*;
import java.util.*;

/**
 * @ClassName: HuffmanCode
 * @Author: WuXiangShuai
 * @Time: 16:27 2019/9/26.
 * @Description:
 */
public class HuffmanCode {

    // 赫夫曼编码表
    static Map<Byte, String> huffmanCodes = new HashMap<>();
    // 叶子节点路径
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) {
//        String str = "i like like like java do you like a java";
        // ------
//        List<CodeNode> nodes = getNodes(str);
//        System.out.println(nodes);
//
//        System.out.println("赫夫曼树");
//        CodeNode root = createHuffmanTree(nodes);
//        root.pre();
//
//        System.out.println("赫夫曼编码");
//        getCodes(root);
//
//        System.out.println(huffmanCodes);
//
//        Byte[] bytes = zip(str);
        // ------
        // 按赫夫曼编码压缩后的byte[]数组
//        byte[] bytes = huffmanZip(str.getBytes());
//        System.out.println(Arrays.toString(bytes));
        // 解压
//        byte[] decode = decode(bytes);
//        System.out.println(new String(decode));
        // ------
//        zipFile("C:\\Users\\Administrator\\Desktop\\haha.png", "C:\\Users\\Administrator\\Desktop\\dst.zip");
        unZipFile("C:\\Users\\Administrator\\Desktop\\dst.zip", "C:\\Users\\Administrator\\Desktop\\haha.png");
    }

    // 解压
    private static void unZipFile(String srcFile, String dstFile) {
        // 定义输入流
        InputStream is = null;
        // 定义对象输入流
        ObjectInputStream ois = null;
        // 定义文件输出流
        OutputStream os = null;
        try{
            // 创建文件输入流
            is = new FileInputStream(srcFile);
            // 创建与is相关的对象输入流
            ois = new ObjectInputStream(is);
            // 读取byte数组
            byte[] huffmanBytes = (byte[]) ois.readObject();
            // 读取赫夫曼编码表
            Map<Byte, String> huffmanCodes = (Map<Byte, String>) ois.readObject();
            // 解码
            byte[] bytes = decode(huffmanBytes, huffmanCodes);
            os = new FileOutputStream(dstFile);
            os.write(bytes);
        } catch (Exception e){
            e.printStackTrace();
        } finally{
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // 压缩
    private static void zipFile(String srcFile, String dstFile) {
        // 创建输入流
        FileInputStream fis = null;
        // 创建输出流
        OutputStream ops = null;
        ObjectOutputStream oos = null;
        try{
            fis = new FileInputStream(srcFile);
            // 接收源文件
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            // 压缩
            byte[] zip = huffmanZip(bytes);
            // 新建文件输出流
            ops = new FileOutputStream(dstFile);
            // 新建一个与文件输出流关联的ObjectOutputStream
            oos = new ObjectOutputStream(ops);
            // 赫夫曼编码后的字节数组写入压缩文件
            oos.writeObject(zip);
            oos.writeObject(huffmanCodes);
        } catch (Exception e){
            e.printStackTrace();
        } finally{
            try {
                fis.close();
                ops.close();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 对压缩数据的解码
     * @param huffmanBytes 赫夫曼编码后的字节数组
     * @return 原字符串对应的数组
     */
    private static byte[] decode(byte[] huffmanBytes, Map<Byte, String> huffmanCodes) {
        // byte数组转对应的字符串
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < huffmanBytes.length; i++) {
            // arg1：是否是最后一个元素，是为false，无需补高位，arg2
            sb.append(byteToBitString(!(i == huffmanBytes.length - 1), huffmanBytes[i]));
        }
//        System.out.println(sb);
        // 反向查询赫夫曼编码
        Map<String, Byte> map = new HashMap<>();
        huffmanCodes.forEach((key, value) -> {
            map.put(value, key);
        });
        // 获取解压后的byte集合
        List<Byte> list = new ArrayList<>();
        for (int i = 0; i < sb.length();) {
            int count = 1;
            Byte b = null;
            // 在反向赫夫曼编码表内查找value，找不到向后指一位，继续查找
            while (b == null) {
                String sub = sb.substring(i, i + count);
                b = map.get(sub);
                count++;
            }
            list.add(b);
            count--;
            i += count;
        }
        // 集合转数组
        byte[] bytes = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            bytes[i] = list.get(i);
        }
        return bytes;
    }

    /**
     * byte转二进制字符串
     * @param flag 标识是否需要补高位，true需要，false不需要
     * @param b
     * @return b 对应的二进制字符串。【补码】
     */
    private static String byteToBitString(boolean flag, byte b) {
        // 保存 b，转 int
        int temp = b;
        // 正数补高位，负数不需要
//        temp = flag ? temp | 256 : temp;
        if (flag) {
            temp |= 256;
        }
        String str = Integer.toBinaryString(temp);
//        System.out.println(str);
        if (flag) {
            return str.substring(str.length() - 8);
        } else {
            return str;
        }
    }

    // 封装方法
    private static byte[] huffmanZip(byte[] bytes) {
        // 生成节点集合
        List<CodeNode> nodes = getNodes(bytes);
        // 创建赫夫曼树
        CodeNode root = createHuffmanTree(nodes);
        // 获取赫夫曼编码表
        getCodes(root);
        byte[] zipBytes = zip(bytes);
        return zipBytes;
    }

    private static byte[] zip(byte[] bytes) {
//        System.out.println(Arrays.toString(bytes));
        // 字节数组通过哈夫曼编码表压缩
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(huffmanCodes.get(bytes[i]));
        }
//        System.out.println(sb.toString());
        // 将哈夫曼数组转为byte数组
        byte[] codes = new byte[(sb.length() + 7) / 8];
        int length = sb.length();
        for (int i = 0, index = 0; i < sb.length(); i += 8) {
            if (i+8 > length) {
                codes[index++] = (byte)Integer.parseInt(sb.substring(i, sb.length()), 2);
            } else {
                codes[index++] = (byte)Integer.parseInt(sb.substring(i, i + 8), 2);
            }
        }
//        System.out.println(Arrays.toString(codes));
        return codes;
    }

    // 重载getCodes，方便调用
    private static void getCodes(CodeNode root) {
        if (root == null) {
            return;
        }
        getCodes(root.getLeft(), "0", sb);
        getCodes(root.getRight(), "1", sb);
    }

    // 生成哈夫曼编码
    private static void getCodes(CodeNode root, String code, StringBuilder sb) {
        // 生成新的编码
        StringBuilder sb2 = new StringBuilder(sb);
        // 拼接编码，左节点 + 0，右节点 + 1
        sb2.append(code);
        if (root != null) {
            // data = null，表明该节点不为叶子节点
            if (root.getData() == null) {
                getCodes(root.getLeft(), "0", sb2);
                getCodes(root.getRight(), "1", sb2);
            }
            // 叶子节点
            else {
                huffmanCodes.put(root.getData(), sb2.toString());
            }
        }
    }

    // 创建赫夫曼树
    private static CodeNode createHuffmanTree(List<CodeNode> nodes) {
        while (nodes.size() > 1) {
            // 重排序
            Collections.sort(nodes);
            // 做小节点
            CodeNode left = nodes.get(0);
            // 其次节点
            CodeNode right = nodes.get(1);
            // 生成根节点
            CodeNode parent = new CodeNode(null, left.getWeight() + right.getWeight());
            // 生成树
            parent.setLeft(left);
            parent.setRight(right);
            // 集合删除该两个节点
            nodes.remove(left);
            nodes.remove(right);
            // 放入根节点
            nodes.add(parent);
        }
        return nodes.get(0);
    }

    private static List<CodeNode> getNodes(byte[] bytes) {
        // 接收返回值
        ArrayList<CodeNode> nodes = new ArrayList<>();
        // 获取元素：元素出现次数
        Map<Byte, Integer> map = new HashMap<>();
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            Integer in = map.get(b);
            map.put(b, in == null ? 1 : in + 1);
        }
        // map 转 Node 树
        map.forEach((i, j) -> {
            CodeNode node = new CodeNode(i, j);
            nodes.add(node);
        });
        return nodes;
    }
}

class CodeNode implements Comparable<CodeNode> {
    // 对应值
    private Byte data;
    // 权值
    private int weight;
    private CodeNode left;
    private CodeNode right;

    public CodeNode(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    public Byte getData() {
        return data;
    }

    public CodeNode setData(Byte data) {
        this.data = data;
        return this;
    }

    public int getWeight() {
        return weight;
    }

    public CodeNode setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    public CodeNode getLeft() {
        return left;
    }

    public CodeNode setLeft(CodeNode left) {
        this.left = left;
        return this;
    }

    public CodeNode getRight() {
        return right;
    }

    public CodeNode setRight(CodeNode right) {
        this.right = right;
        return this;
    }

    public void pre() {
//        System.out.println(this);
        if (left != null) {
            left.pre();
        }
        if (right != null) {
            right.pre();
        }
    }

    @Override
    public String toString() {
        return "CodeNode{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }

    // 从小到大排序
    @Override
    public int compareTo(CodeNode node) {
        return this.weight - node.getWeight();
    }
}
