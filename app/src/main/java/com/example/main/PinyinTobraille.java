package com.example.main;



import java.util.HashMap;

import java.util.Map;

public class PinyinTobraille {

    public String outputYun (String string) {
        Map<String,String> map = new HashMap<>();
        //韵母

        map.put("a", "001010");
        map.put("o", "010001");
        map.put("e", "010001");
        map.put("i", "010100");
        map.put("u", "101001");
        map.put("u:","001101");
        map.put("ai","010101");
        map.put("ao","011010");
        map.put("ei","011101");
        map.put("ou","111011");
        map.put("ia","110101");
        map.put("iao","001110");
        map.put("ie", "100010");
        map.put("iu", "110011");
        map.put("ua", "111111");
        map.put("ui","010111");
        map.put("uo","101010");
        map.put("u:e","011111");
        map.put("uai", "101111");
        map.put("er", "111010");
        map.put("an", "111001");
        map.put("ang", "011001");
        map.put("en", "001011");
        map.put("eng", "001111");
        map.put("ian", "100101");
        map.put("iang", "101101");
        map.put("in", "110001");
        map.put("ing", "100001");
        map.put("uan", "110111");
        map.put("uang", "011011");
        map.put("un", "010010");
        map.put("ong", "010011");
        map.put("uan", "111101");
        map.put("u:n", "000111");
        map.put("iong", "100111");

        return  map.get(string);
    }
    public String outputSheng(String string){
        Map<String, String> map = new HashMap<>();
        //声母
        map.put("b","110000");
        map.put("p","111100");
        map.put("q","101000");
        map.put("m", "101100");
        map.put("f", "110100");
        map.put("d", "100110");
        map.put("t", "011110");
        map.put("n", "101110");
        map.put("l", "111000");
        map.put("g", "110110");
        map.put("j", "110110");
        map.put("k", "101000");
        map.put("h", "110010");
        map.put("x", "110010");
        map.put("r", "010110");
        map.put("z", "101011");
        map.put("c", "100100");
        map.put("s", "011100");
        return  map.get(string);
    }
    public String outputY(String string){
        Map<String,String> map =new HashMap<>();
        map.put("ya","110101");
        map.put("yan","100101");
        map.put("yang","101101");
        map.put("yao","001110");
        map.put("ye","100010");
        map.put("yi","010100");
        map.put("yin","110001");
        map.put("ying","100001");
        map.put("yong","100111");
        map.put("you","110011");
        map.put("yu","001101");
        map.put("yuan","111101");
        map.put("yue","001101");
        map.put("yun","000111");
        return map.get(string);
    }
    public String outputW(String string){
        Map<String,String> map = new HashMap<>();
        map.put("wa","111111");
        map.put("wai","101111");
        map.put("wan","110111");
        map.put("wang","011011");
        map.put("wei","010111");
        map.put("wen","010010");
        map.put("wo","101010");
        map.put("wu","101001");
        return map.get(string);
    }

//        public void s(){
//                Pinyin pinyin = new Pinyin();
//            System.out.print("声母,");
//                System.out.printf("%s,", pinyin.getShengmu());
//            System.out.println();
//                System.out.printf("%s,", pinyin.getYunmu());
//
//        }
}