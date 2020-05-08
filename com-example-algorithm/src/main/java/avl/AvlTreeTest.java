package avl;


public class AvlTreeTest {


    static class AvlTree {

    }

    static class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }

        public int leftHeight() {
            if (left == null) {
                return 0;
            }
            return left.height();
        }

        public int rightHeight() {
            if (right == null) {
                return 0;
            }
            return right.height();
        }

        //如何判断一个左子树的高度？
        //递归：1->2->3(0)
        public int height() {
            return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
        }

        //左旋
        public void leftRotate() {
            //1.创一个新的节点，等于当前节点
            Node newNode = new Node(value);
            //2.当前节点的左节点变为新节点的左节点
            newNode.left = left;
            //3.当前节点的右子树的左子树变为新节点的右子树
            newNode.right = right.left;
            //4.把当前节点的值替换成右子树的值
            this.value = right.value;
            //5.当前节点右子树的设置为右子树的右子树
            right = right.right;
            //6.把当前节点的左子树指向新节点
            left = newNode;
        }

        //右旋
        public void rigthRotate() {
            Node newNode = new Node(value);
            newNode.right = right;
            newNode.left = left.right;
            this.value = left.value;
            left = left.left;
            right = newNode;
        }


        //查找就删除
        public Node search(int value) {
            if (value == this.value) {
                return this;
            } else if (value < this.value) {
                if (this.left == null) {
                    return null;
                }
                return this.left.search(value);
            } else {
                if (this.right == null) {
                    return null;
                }
                return this.right.search(value);
            }
        }

        public Node searchParent(int value) {
            // 如果当前节点就是要删除的结点的父结点，就返回
            if ((this.left != null && this.left.value == value) || (this.right != null && this.right.value == value)) {
                return this;
            } else {
                //如果查找的值小于当前结点值，并且当前结点的左子节点不为空
                if (value < this.value && this.left != null) {
                    return this.left.searchParent(value);
                } else if (value >= this.value && this.right != null) {
                    return this.right.searchParent(value);
                } else {
                    return null;
                }
            }

        }

        //添加节点
        public void add(Node node) {
            if (null == node) {
                return;
            }
            if (node.value < this.value) {
                if (this.left == null) {
                    this.left = node;
                } else {
                    //递归
                    this.left.add(node);
                }
            } else {
                if (this.right == null) {
                    this.right = node;
                } else {
                    this.right.add(node);
                }
            }

            //当前左子树和右子树的高度超过1的时候，需要进行旋转的
            //问题：可能存在，左旋或者右旋后，还是没有达成AVL树结构？
            //答案：通过一个规律进行处理，先进行右旋的时候，对当前节点的左节点进行左旋
            if (rightHeight() - leftHeight() > 1) {
                if (right != null && right.leftHeight() > right.rightHeight()) {
                    rigthRotate();
                    leftRotate();
                } else {
                    leftRotate();
                }
                return;
            }

            if (leftHeight() - rightHeight() > 1) {
                if (left != null && left.rightHeight() > left.leftHeight()) {
                    left.leftRotate();
                    rigthRotate();
                } else {
                    rigthRotate();
                }
            }
        }

        //中序遍历
        public void infixOrder() {
            if (this.left != null) {
                this.left.infixOrder();
            }
            System.out.println(this);
            if (this.right != null) {
                this.right.infixOrder();
            }
        }
    }
}
