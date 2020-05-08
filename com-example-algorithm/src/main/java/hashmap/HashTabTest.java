package hashmap;

public class HashTabTest {

    public static void main(String[] args) {

        //创建一个hash
        HashTab hashTab = new HashTab(7);

    }

    static class HashTab {
        private EmpLinkedList[] empLinkedListArray;
        private int size;

        public HashTab(int size) {
            this.size = size;
            empLinkedListArray = new EmpLinkedList[size];
            //初始化每条链表
            for (int i = 0; i < size; i++) {
                empLinkedListArray[i] = new EmpLinkedList();
            }
        }

        public void add(Emp emp) {
            int empLinkedListN0 = hashFun(emp.id);
            empLinkedListArray[empLinkedListN0].add(emp);
        }

        public int hashFun(int id) {
            return id % size;
        }

        public void list() {
            for (int i = 0; i < size; i++) {
                empLinkedListArray[i].list();
            }
        }

        public void findEmpById(int id) {
            int empLinkedList0 = hashFun(id);
            Emp emp = empLinkedListArray[empLinkedList0].findEmpById(id);
            if (null == emp) {
                System.out.println("查无此信息");
            } else {
                System.out.println(emp);
            }
        }
    }

    static class Emp {
        public int id;
        public String name;
        public Emp next;

        public Emp(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    static class EmpLinkedList {
        public Emp head;

        //添加到最后
        public void add(Emp emp) {
            if (head == null) {
                head = emp;
                return;
            }
            Emp curEmp = head;
            while (true) {
                if (curEmp.next == null) {
                    break;
                }
                curEmp = curEmp.next;
            }

            curEmp.next = emp;
        }

        //遍历链表
        public void list() {
            if (head == null) {
                System.out.println("链表为空");
                return;
            }
            Emp curEmp = head;
            while (true) {
                System.out.println(curEmp.id + curEmp.name);
                if (curEmp.next == null) {
                    break;
                }
                curEmp = curEmp.next;//后移，遍历
            }
            System.out.println();
        }

        public Emp findEmpById(int id) {
            System.out.println("链表为空");

            if (head == null) {
                return null;
            }
            Emp curEmp = head;
            while (true) {
                if (curEmp.id == id) {
                    break;
                }
                if (curEmp.next == null) {
                    curEmp = null;
                    break;
                }
                curEmp = curEmp.next;
            }
            return curEmp;
        }
    }
}
