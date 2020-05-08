package tree;

/**
 * 节点
 * 结构
 */
public class BinaryTreeTest {

    public static void main(String[] args) {
        //
        BinaryTree tree = new BinaryTree();
        HeroNode root = new HeroNode(1, "宋江1");
        HeroNode node2 = new HeroNode(2, "宋江2");
        HeroNode node3 = new HeroNode(3, "宋江3");
        HeroNode node4 = new HeroNode(4, "宋江4");
        root.setLeft(node2);
        root.setRight(node3);
        node3.setRight(node4);

        System.out.println("前序遍历");
        tree.setRoot(root);

        tree.preOrder();
        tree.infixOrder();
        tree.postOrder();

    }

    static class BinaryTree {
        private HeroNode root;

        public void setRoot(HeroNode root) {
            this.root = root;
        }

        public void delNode(int no) {
            if (root != null) {
                if (root.getNo() == no) {
                    root = null;
                } else {
                    root.delNode(no);
                }
            } else {
                System.out.println("删除");
            }
        }

        //前序遍历
        public void preOrder() {
            if (this.root != null) {
                this.root.preOrder();
            } else {
                System.out.println("二叉树为空,无法遍历");
            }
        }

        //中序遍历
        public void infixOrder() {
            if (this.root != null) {
                this.root.infixOrder();
            } else {
                System.out.println("二叉树为空,无法遍历");
            }
        }

        //后序遍历
        public void postOrder() {
            if (this.root != null) {
                this.root.postOrder();
            } else {
                System.out.println("二叉树为空,无法遍历");
            }
        }
    }

    static class HeroNode {
        private int no;
        private String name;
        private HeroNode left;
        private HeroNode right;

        public HeroNode(int no, String name) {
            this.no = no;
            this.name = name;
        }

        public int getNo() {
            return no;
        }

        public void setNo(int no) {
            this.no = no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public HeroNode getLeft() {
            return left;
        }

        public void setLeft(HeroNode left) {
            this.left = left;
        }

        public HeroNode getRight() {
            return right;
        }

        public void setRight(HeroNode right) {
            this.right = right;
        }

        @Override
        public String toString() {
            return "HeroNode{" +
                    "no=" + no +
                    ", name='" + name + '\'' +
                    '}';
        }

        //前序遍历
        public void preOrder() {
            System.out.println(this);
            //递归
            if (this.left != null) {
                this.left.preOrder();
            }
            if (this.right != null) {
                this.right.preOrder();
            }
        }

        //中序遍历
        public void infixOrder() {
            //递归
            if (this.left != null) {
                this.left.infixOrder();
            }
            System.out.println(this);
            //递归向右子树中序遍历
            if (this.right != null) {
                this.right.infixOrder();
            }
        }

        //后序遍历
        public void postOrder() {
            if (this.left != null) {
                this.left.postOrder();
            }
            if (this.right != null) {
                this.right.postOrder();
            }
            System.out.println(this);
        }

        /**
         * 前序遍历
         */
        public HeroNode preOrderSearch(int no) {
            if (this.no == no) {
                return this;
            }
            HeroNode resNode = null;
            if (this.left != null) {
                resNode = this.left.preOrderSearch(no);
            }
            if (null != resNode) {
                return resNode;
            }
            if (this.right != null) {
                resNode = this.right.preOrderSearch(no);
            }
            return resNode;
        }

        public HeroNode infixOrderSearch(int no) {
            HeroNode resNode = null;
            if (this.left != null) {
                resNode = this.left.preOrderSearch(no);
            }
            if (null != resNode) {
                return resNode;
            }

            if (this.no == no) {
                return this;
            }
            if (this.right != null) {
                resNode = this.right.preOrderSearch(no);
            }
            return resNode;
        }

        public HeroNode postOrderSearch(int no) {
            HeroNode resNode = null;
            if (this.right != null) {
                resNode = this.right.preOrderSearch(no);
            }
            if (null != resNode) {
                return resNode;
            }

            if (this.left != null) {
                resNode = this.left.preOrderSearch(no);
            }
            if (null != resNode) {
                return resNode;
            }

            if (this.no == no) {
                return this;
            }
            return resNode;
        }

        public void delNode(int no) {
            if (this.left != null && this.left.no == no) {
                this.left = null;
                return;
            }

            if (this.right != null && this.right.no == no) {
                this.right = null;
                return;
            }
            if (this.left != null) {
                this.left.delNode(no);
            }
            if (this.right != null) {
                this.right.delNode(no);
            }


        }

    }


}
