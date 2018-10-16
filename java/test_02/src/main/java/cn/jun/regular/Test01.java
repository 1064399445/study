package cn.jun.regular;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test01 {

    public static void main(String[] args){
        String exp = "a";
        String b = "b";
        Pattern pattern = Pattern.compile(exp);
        String test = "ab";
        Matcher matcher = pattern.matcher(test);
        System.out.println(matcher.find());

    }

}
