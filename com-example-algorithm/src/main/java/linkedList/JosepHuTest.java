package linkedList;


/**
 * 单向环形链表
 */
public class JosepHuTest {


    static class CircleSingleLinkedList {
        //第一个节点
        private Boy first = new Boy(-1);

        public void addBoy(int nums) {
            if (nums < 1) {
                System.out.println("nums值不正确");
                return;
            }
            Boy curBoy = null; //辅助指针，帮助构建环形链表
            //创建环形链表
            for (int i = 0; i < nums; i++) {
                //根据编号，创建小孩节点
                Boy boy = new Boy(i);
                if (i == 1) {
                    first = boy;
                    first.setNext(first);
                    curBoy = first; //
                } else {
                    curBoy.setNext(boy);
                    boy.setNext(first);
                    curBoy = boy;
                }
            }
        }

        public void showBoy() {
            if (first == null) {
                System.out.println("没有");
                return;
            }

            Boy curBoy = first;
            while (true) {
                System.out.println(curBoy.no);
                if (curBoy.getNext() == first) {
                    break;
                }
                curBoy = curBoy.getNext();
            }
        }

        public void countBoy(int startNo, int countNum, int nums) {
            if (first == null || startNo < 1 || startNo > nums) {
                System.out.println("输入有误");
                return;
            }
            Boy helper = first;
            while (true) {
                if (helper.getNext() == first) {
                    break;
                }
                helper = helper.getNext(); //使help为最后一个
            }
            //
            for (int i = 0; i < startNo - 1; i++) {
                first = first.getNext();
                helper = helper.getNext();
            }

            //
            while (true) {
                if (helper == first) {
                    break;
                }
                for (int i = 0; i < countNum - 1; i++) {
                    first = first.getNext();
                    helper = helper.getNext();
                }
                System.out.println("出圈" + first.no);
                first = first.getNext();
                helper.setNext(first);
            }

            System.out.println("最后在圈中的" + first.no);
        }

    }

    static class Boy {

        private int no;
        private Boy next;

        public Boy(int no) {
            this.no = no;
        }

        public Boy getNext() {
            return next;
        }

        public void setNext(Boy next) {
            this.next = next;
        }
    }
}
