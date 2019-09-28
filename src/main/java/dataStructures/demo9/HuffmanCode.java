package dataStructures.demo9;

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
        String str = "i like like like java do you like a java";
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
        Byte[] bytes = huffmanZip(str);
        System.out.println(Arrays.toString(bytes));
    }

    // 封装方法
    private static Byte[] huffmanZip(String str) {
        // 生成节点集合
        List<CodeNode> nodes = getNodes(str);
        // 创建赫夫曼树
        CodeNode root = createHuffmanTree(nodes);
        // 获取赫夫曼编码表
        getCodes(root);
        Byte[] bytes = zip(str);
        return bytes;
    }

    private static Byte[] zip(String str) {
        // 获取字符串字节数组
        byte[] bytes = str.getBytes();
        System.out.println(Arrays.toString(bytes));
        // 字节数组通过哈夫曼编码表压缩
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(huffmanCodes.get(bytes[i]));
        }
        System.out.println(sb.toString());
        // 将哈夫曼数组转为byte数组
        Byte[] codes = new Byte[(sb.length() + 7) / 8];
        int length = sb.length();
        for (int i = 0, index = 0; i < sb.length(); i += 8) {
            if (i+8 > length) {
                codes[index++] = (byte)Integer.parseInt(sb.substring(i, sb.length() - 1), 2);
            } else {
                codes[index++] = (byte)Integer.parseInt(sb.substring(i, i + 8), 2);
            }
        }
        System.out.println(Arrays.toString(codes));
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

    private static List<CodeNode> getNodes(String str) {
        // 接收返回值
        ArrayList<CodeNode> nodes = new ArrayList<>();
        // 转 byte 数组
        byte[] bytes = str.getBytes();
        Map<Byte, Integer> map = new HashMap<>();
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            Integer in = map.get(b);
            map.put(b, in == null ? 1 : in + 1);
        }
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
        System.out.println(this);
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
