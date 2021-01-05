package simple;

/**
 *
 *      -
 *     ---
 *   --------
 *      a           b           c
 */
public class Hannio {
    public static void hanio(int n, char a, char b, char c) {
        if (n == 1) {
            move(a, c);
            return;
        }
        hanio(n - 1, a, c, b); // a->c a->b
        move(a, c);            //
        hanio(n-1, b, a, c);
    }

    public static void move(char a, char c) {
        System.out.println(a + "'--->" + c);
    }

    public static void main(String[] args) {
        hanio(1,'a' , 'b', 'c');
        System.out.println("=============");
        hanio(2,'a' , 'b', 'c');
        System.out.println("=============");
        hanio(3,'a' , 'b', 'c');
        System.out.println("=============");
        hanio(4,'a' , 'b', 'c');
        System.out.println("=============");
        hanio(5111,'a' , 'b', 'c');

    }
}
