package recursion;

public class MiGongTest {

    public static void main(String[] args) {
        // 1.二维数组模拟迷宫
        int[][] map = new int[8][7];

        //上下全部置为1
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }

        //左右全部置为1
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }

        //输出地图
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + "\t");
            }
            System.out.println();
        }

    }

    /**
     * map ：是地图
     * i,j 表示从地图的那个位置开始出发(1,1)
     */
    //使用递归回溯来给小球找路
    public static boolean setWay(int[][] map, int i, int j) {
        if (map[6][5] == 2) { //这个是通路
            return true;
        } else {
            if (map[i][j] == 0) {
                map[i][j] = 2; //假定该点是可以走通的
                //按照策略 下->右->上->左 走
                if (setWay(map, i + 1, j)) { //向下走
                    return true;
                } else if (setWay(map, i, j + 1)) {
                    return true;
                } else if (setWay(map, i - 1, j)) {
                    return true;
                } else if (setWay(map, i, j - 1)) {
                    return true;
                } else {
                    //说明该点是死路
                    map[i][j] = 3;
                    return false;
                }
            } else { //
                return false;
            }
        }
    }
}
