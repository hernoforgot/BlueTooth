package com.example.main;

public class test {
    //测试结果
//    public static void main(String[] args) throws Exception{
//    PinyinTool tool = new PinyinTool();
//    String braille = tool.toPinYin("欧", "", PinyinTool.Type.LOWERCASE);
//        System.out.println(" 转换结果：  " + braille);
//    PinyinTobraille p = new PinyinTobraille();
//    test t = new test();
//    String s = t.toBrailleString(braille,p);
//        System.out.println(s);
//
//    }

    public String  toBrailleString(String string,PinyinTobraille p){
        char[] ch = {};
        String number="";
        ch=string.toCharArray();
        System.out.println( );
        // a e i o u v 的处理， v相当于 u: 例如 虐 nve=nu:e
        if( ch[0]=='a'||ch[0]=='o'||ch[0]=='e'||ch[0]=='i'||ch[0]=='u'||ch[0]=='v'){
            number = p.outputYun(string);
        }
        //sh 声母
        else if(ch[0]=='s'&&ch[1]=='h'){
            char[] ch2=string.toCharArray();
            for(int i =0 ;i<string.length();i++) {
                if(i<string.length()-2)
                    ch2[i] = ch2[i + 2];
                else {ch2[i]='\0';break;}
            }
            String value=String.valueOf(ch2);
            String  real = value.substring(0,value.length()-2);
            number = "100011"+p.outputYun( real);
        }
        //ch 声母
        else if(ch[0]=='c'&&ch[1]=='h'){
            char[] ch2=string.toCharArray();
            for(int i =0 ;i<string.length();i++) {
                if(i<string.length()-2)
                    ch2[i] = ch2[i + 2];
                else {ch2[i]='\0';break;}
            }
            String value=String.valueOf(ch2);
            String  real = value.substring(0,value.length()-2);
            number = "111110"+p.outputYun( real);
        }
        //zh 声母
        else if(ch[0]=='z'&&ch[1]=='h'){
            char[] ch2=string.toCharArray();
            for(int i =0 ;i<string.length();i++) {
                if(i<string.length()-2)
                    ch2[i] = ch2[i + 2];
                else {ch2[i]='\0';break;}
            }
            String value=String.valueOf(ch2);
            String  real = value.substring(0,value.length()-2);
            number = "001100"+p.outputYun( real);
        }
        //特殊的 y 开头
        else if(ch[0]=='y'){

            number ="000000"+ p.outputY(string);
        }
        //特殊的 w 开头
        else if(ch[0]=='w'){
            number ="000000" +p.outputW(string);
        }
        //余下 单字母开头
        else {
            char[] ch2=ch;
            char c=ch2[0];
            for(int i =0 ;i<string.length();i++) {
                if (i < string.length() - 1) ch2[i] = ch2[i + 1];
                else ch2[i] = '\0';
            }
            String value=String.valueOf(ch2);
            String  real = value.substring(0,value.length()-1);
            number = p.outputSheng(String.valueOf(c)) + p.outputYun( real);
        }
        return number;
    }
}
