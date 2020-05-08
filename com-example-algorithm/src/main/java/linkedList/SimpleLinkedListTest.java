package linkedList;

import java.util.Stack;

public class SimpleLinkedListTest {

    public static void main(String[] args) {

        HeroNode hero1 = new HeroNode(1, "宋江1", "及时雨1");
        HeroNode hero3 = new HeroNode(3, "宋江3", "及时雨3");
        HeroNode hero2 = new HeroNode(2, "宋江2", "及时雨2");
        HeroNode hero4 = new HeroNode(4, "宋江4", "及时雨4");

        SingleLinkedList singleLinkedList = new SingleLinkedList();

        singleLinkedList.add(hero1);
        singleLinkedList.add(hero2);
        singleLinkedList.add(hero3);
        singleLinkedList.add(hero4);

        singleLinkedList.list();

        System.out.println("逆序后");
        reversetList(singleLinkedList.head);

        singleLinkedList.list();


    }

    //创建一个链表

    static class SingleLinkedList {
        //初始化头节点
        private HeroNode head = new HeroNode(0, "", "");

        //添加节点到单向链表
        public void add(HeroNode heroNode) {
            HeroNode temp = head;
            while (true) {
                if (temp.next == null) {
                    break;
                }
                temp = temp.next; //
            }
            temp.next = heroNode;
        }

        public void list() {
            if (head.next == null) {
                return;//链表为空
            }
            HeroNode temp = head.next;
            while (true) {
                if (temp == null) {
                    break;
                }
                //输出节点信息
                System.out.println(temp);
                //temp后移
                temp = temp.next;
            }
        }

        public void addByOrder(HeroNode heroNode) {
            //因为头节点不能动，因此我们仍然通过一个辅助指针(变量)来帮助找到添加的位置
            HeroNode temp = head;
            boolean flag = false;
            while (true) {
                if (temp.next == null) {
                    break; //说明temp已经在链表的最后
                }

                //这里为什么只用判断下一个元素大于当前元素？
                //因为添加规则决定：因为头元素始终是最小的，只需要判断下一个元素和当前元素的大小
                if (temp.next.no > heroNode.no) {
                    break;
                } else if (temp.next.no == heroNode.no) {
                    flag = true;
                    break;
                }
                temp = temp.next;
            }

            if (flag) {
                System.out.println("插入的编号已经存在");
            } else {
                //添加
                heroNode.next = temp.next;
                temp.next = heroNode;
            }

        }

        //修改节点信息
        public void update(HeroNode heroNode) {
            if (head.next == null) {
                return;
            }
            HeroNode temp = head.next;
            boolean flag = false;
            //链表的遍历
            while (true) {
                if (temp == null) {
                    break;
                }
                if (temp.no == heroNode.no) {
                    flag = true;
                    break;
                }
                temp = temp.next;
            }

            if (flag) {
                temp.name = heroNode.name;
                temp.nickname = heroNode.nickname;
            } else {
                System.out.println("没有找到");
            }
        }

        //删除节点
        public void delete(int no) {
            HeroNode temp = head;
            boolean flag = false;
            while (true) {
                if (temp.next == null) {
                    break;
                }
                if (temp.next.no == no) {
                    flag = true;
                    break;
                }
                temp = temp.next;
            }

            if (flag) {
                //找到了删除的元素(改变删除元素上一个的指向)
                temp.next = temp.next.next;
            } else {
                System.out.println("未找到删除的元素");
            }
        }
    }

    public static int getLength(HeroNode heroNode) {
        if (heroNode.next == null) {
            return 0;
        }
        int length = 0;
        HeroNode cur = heroNode.next;
        while (cur != null) {
            length++;
            cur = cur.next;
        }
        return length;
    }

    public static HeroNode findLastIndexNode(HeroNode head, int index) {
        if (head.next == null) {
            return null;
        }
        int size = getLength(head);
        if (index <= 0 || index > size) {
            return null;
        }
        HeroNode cur = head.next;
        for (int i = 0; i < size - index; i++) {
            cur = cur.next;
        }
        return cur;
    }

    /**
     * head         1 2 3
     * <p>
     * reverseHead
     */
    //将单链表进行翻转
    public static void reversetList(HeroNode head) {
        if (head.next == null || head.next.next == null) {
            return;
        }
        HeroNode cur = head.next;
        HeroNode next; //指向当前节点下一个节点
        HeroNode reverseHead = new HeroNode(0, "", "");
        //遍历原来的链表
        while (cur != null) {
            // head 1 2 3
            next = cur.next; //保存下一个节点
            cur.next = reverseHead.next; //指向新链表的
            reverseHead.next = cur;
            cur = next; //因为一进来的时候当前节点的下一个节点改变了，需要临时存储
        }
        head.next = reverseHead.next;
    }

    //逆序打印
    public static void reversePrint(HeroNode head) {
        if (head.next == null) {
            return;
        }
        Stack<HeroNode> stack = new Stack<>();
        HeroNode cur = head.next;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }

        while (!stack.empty()) {
            System.out.println(stack.pop());
        }
    }

    static class DoubleLinkedList {
        //初始化头节点
        private HeroNode2 head = new HeroNode2(0, "", "");

        public HeroNode2 getHead() {
            return head;
        }


        public void add(HeroNode2 heroNode) {
            HeroNode2 temp = head;
            while (true) {
                if (temp.next == null) {
                    break;
                }
                temp = temp.next; //
            }
            temp.next = heroNode;
            heroNode.pre = temp;
        }

        public void list() {
            if (head.next == null) {
                return;//链表为空
            }
            HeroNode2 temp = head.next;
            while (true) {
                if (temp == null) {
                    break;
                }
                //输出节点信息
                System.out.println(temp);
                //temp后移
                temp = temp.next;
            }
        }

        //修改节点信息
        public void update(HeroNode2 heroNode) {
            if (head.next == null) {
                return;
            }
            HeroNode2 temp = head.next;
            boolean flag = false;
            //链表的遍历
            while (true) {
                if (temp == null) {
                    break;
                }
                if (temp.no == heroNode.no) {
                    flag = true;
                    break;
                }
                temp = temp.next;
            }

            if (flag) {
                temp.name = heroNode.name;
                temp.nickname = heroNode.nickname;
            } else {
                System.out.println("没有找到");
            }
        }

        //删除节点(双向链表)
        public void delete(int no) {

            if (head.next == null) {
                return;
            }

            HeroNode2 temp = head.next;
            boolean flag = false;
            while (true) {
                if (temp == null) {
                    break;
                }
                if (temp.no == no) {
                    flag = true;
                    break;
                }
                temp = temp.next;
            }

            if (flag) {
                //找到了删除的元素(改变删除元素上一个的指向)
                temp.pre.next = temp.next;
                if (temp.next != null) {
                    temp.next.pre = temp.pre;
                }
            } else {
                System.out.println("未找到删除的元素");
            }

        }
    }


    static class HeroNode {
        private int no;
        private String name;
        private String nickname;
        private HeroNode next; //指向下一个节点

        public HeroNode(int no, String name, String nickname) {
            this.no = no;
            this.name = name;
            this.nickname = nickname;
        }

        @Override
        public String toString() {
            return "HeroNode{" +
                    "no=" + no +
                    ", name='" + name + '\'' +
                    ", nickname='" + nickname + '\'' +
                    '}';
        }
    }

    static class HeroNode2 {
        private int no;
        private String name;
        private String nickname;
        private HeroNode2 pre; //指向上一个节点
        private HeroNode2 next; //指向下一个节点

        public HeroNode2(int no, String name, String nickname) {
            this.no = no;
            this.name = name;
            this.nickname = nickname;
        }

        @Override
        public String toString() {
            return "HeroNode2{" +
                    "no=" + no +
                    ", name='" + name + '\'' +
                    ", nickname='" + nickname + '\'' +
                    '}';
        }
    }
}
