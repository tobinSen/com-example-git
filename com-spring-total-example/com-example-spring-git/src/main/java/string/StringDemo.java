package string;

public class StringDemo {
    public static void main(String[] args) {
        String s0 = "1";
        final String s1 = "2";
        String s2 = "1" + s1;
        System.out.println(s0 == s2); //false

        System.out.println("==================");

        String s3 = "abc";
        String s4 = new String("abc");
        System.out.println(s3==s4);    // false
        System.out.println(s3==s4.intern()); //true
    }

}
