import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        System.out.println(testString("{()[(){}]}"));
    }

    public static Boolean testString(String str) {
        Stack<Character> stack = new Stack<>();
        for(int i =0;i < str.length(); i++) {
            Character ch = str.charAt(i);
            if(ch == '{' || ch == '[' || ch == '(') {
                stack.push(ch);
            } else if(ch == '}' || ch == ']' || ch == ')') {
                if(stack.isEmpty()) return false;
                Character top = stack.pop();
                if((ch == '}' && top != '{') ||
                        (ch == ']' && top != '[') ||
                        (ch == ')' && top != '(')) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}