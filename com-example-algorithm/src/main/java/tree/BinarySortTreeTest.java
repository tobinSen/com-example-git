package tree;

public class BinarySortTreeTest {


    static class BinarySortTree {
        private Node root;

        public void add(Node node) {
            if (root == null) {
                root = node;
            } else {
                root.add(node);
            }

        }

        public void infixOrder() {
            if (root != null) {
                root.infixOrder();
            } else {
                System.out.println("为空");
            }
        }

        public Node search(int value) {
            if (root == null) {
                return null;
            } else {
                return root.search(value);
            }
        }

        public Node searchParent(int value) {
            if (root == null) {
                return null;
            } else {
                return root.searchParent(value);
            }
        }

        public void delNode(int value) {
            if (root == null) {
                return;
            } else {
                Node targetNode = search(value);
                if (null == targetNode) {
                    return;
                }

                if (root.left == null && root.right == null) {
                    root = null;
                    return;
                }

                Node parent = searchParent(value);
                //叶子节点
                if (targetNode.left == null && targetNode.right == null) {
                    //判断targetNode是
                    if (parent.left != null && parent.left.value == value) {
                        parent.left = null;
                    } else if (parent.right != null && parent.right.value == value) {
                        parent.right = null;
                    }
                }
            }
        }
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
