package SixChar;

public class ExpressionBinaryTree_ex {
    //语法正确，简化表达式，变量是单个大写字符，无空格，无多余字符串
    public static String[][] expstr=
            {//中缀，                                                   前缀，                                         后缀
//    {"A",                            "A",                      "A"},                    //0
//    {"A+B",                          "+AB",                    "AB+"},                  //1，图6.36(a)
//    {"A+B*C",                        "+A*BC",                  "ABC*+"},                //2，图6.36(b)，比较运算符优先级
//    {"A*B+C",                        "+*ABC",                  "AB*C+"},                //3，图6.36(c)，比较运算符优先级
//    {"(A+B)*C",                      "*+ABC",                  "AB+C*"},                //4，图6.36(d)，括号，比较运算符优先级
//    {"A*(B+C)",                      "*A+BC",                  "ABC+*"},                //5，意同图6.36(d)
//    {"A+B-C",                        "-+ABC",                  "AB+C-"},                //图6.37(a)，结合律
//    {"(A+B)-C",                      "-+ABC",                  "AB+C-"},                //图6.37(a)，结合律
//    {"A+(B-C)",                      "+A-BC",                  "ABC-+"},                //图6.37(b)，结合律
//    {"A+B*C-D",                      "-+A*BCD",                "ABC*+D-"},              //思考题6-6习图6.9(a)
//    {"A+(B*C-D)",                    "+A-*BCD",                "ABC*D-+"},              //思考题6-6习图6.9(b)
//    {"(A+B)*C-D",                    "-*+ABCD",                "AB+C*D-"},              //思考题6-6习图6.9(c)
//    {"A+B*(C-D)",                    "+A*B-CD",                "ABCD-*+"},              //思考题6-6习图6.9(d)
//    {"(A+B)*(C-D)",                  "*+AB-CD",                "AB+CD-*"},              //思考题6-6习图6.9(e)，图6.38，前缀后缀构造（递归）
//    {"A+B*C/D-E",                    "-+A/*BCDE",              "ABC*D/+E-"},            //中缀，使用栈
//    {"A+B*C-D/E",                    "-+A*BC/DE",              "ABC*+DE/-"},            //
//    {"A+B*(C-D)+E",                  "++A*B-CDE",              "ABCD-*+E+"},            //图4.5，后缀表达式求值（使用栈）
//    {"1+2*(3-4)+5",                  "++1 *2 -3 4 5 ",         "1 2 3 4 -*+5 +"},       //例4.2，图4.5，后缀表达式求值（使用栈），计算结果：  4
//    {"A+(B+C*D)/(E-F)",              "+A/+B*CD-EF",            "ABCD*+EF-/+"},          //图6.39，后缀构造（使用栈）
//    {"A+B*C/(D*E-F)",                "+A/*BC-*DEF",            "ABC*DE*F-/+"},          //图6.40，中缀构造，一层括号
//    {"A+B*C/(D*E-F)-G",              "-+A/*BC-*DEFG",          "ABC*DE*F-/+G-"},        //
//    {"(A+B-C)*(D-E+F)",              "*-+ABC+-DEF",            "AB+C-DE-F+*"},          //
                    //以下试题，画图？？，习4-5填空题？？
//    {"((A+B)*C-D)/(E+F*G-H)",        "/-*+ABCD-+E*FGH",        "AB+C*D-EFG*+H-/"},      //两层括号                //后缀
//    {"((A+B)*C-D)/(E+F*G/H)",        "/-*+ABCD+E/*FGH",        "AB+C*D-EFG*H/+/"},      //后缀
//    {"((A+B)*C-D)/(E+F*(G-H))",      "/-*+ABCD+E*F-GH",        "AB+C*D-EFGH-*+/"},      //
//    {"((A+B)*C-D)/(E+F*(G-H))",      "/-*+ABCD+E*F-GH",        "AB+C*D-EFGH-*+/"},      //求值
//    {"((A+B)/C+D)*E-F*(G-H)",        "-*+/+ABCDE*F-GH",        "AB+C/D+E*FGH-*-"},      //新加未画图
//    {"((1+2)/3+4)*5-6*(7-8)",        "-*+/+1 2 3 4 5 *6 -7 8", "1 2 +3 /4 +5 *6 7 8 -*-"},//计算结果：31
//    {"(A+B)*((C-D+E)/((F-G)%H+I))",  "*+AB/+-CDE+%-FGHI",      "AB+CD-E+FG-H%I+/*"},    //图6.41a，求值
//    {"A+B*(C-D*(E+F)/G+H)-(I+J)*K",  "-+A*B+-C/*D+EFGH*+IJK",  "ABCDEF+*G/-H+*+IJ+K*-"},//习4-5，习6-31，习题解答；中缀构造，二层括号

                    //以下图6.41(b)和思考题6-7习图6.10，求值，运算符集合{+,&,^,|}的优先级是{3,8,9,10},
                    //变量集合{"A","B","C","D","E"}的取值是{6,4,9,5,20}
                    //中缀，                          前缀，                  后缀
//      {"A^(B|C)",          "^A|BC",      "ABC|^"},
//      {"A^(B|C)&D",        "^A&|BCD",    "ABC|D&^"},
//      {"(A^(B|C))&D",      "&^A|BCD",    "ABC|^D&"},
                    {"(A^(B|C)&D)+E",    "+^A&|BCDE",  "ABC|D&^E+"},  //图6.41b，求值，4|9=13，13&5=5，6^5=3，3+20=23，计算结果：23
//      {"((A^(B|C))&D)+E",  "+&^A|BCDE",  "ABC|^D&E+"},  //思考题6-7习图6.10(a)，求值，4|9=13，6^13=11，11&5=1，1+20=21，计算结果：21
//      {"A^B|C&D+E",        "|^AB&C+DE",  "AB^CDE+&|"},  //思考题6-7习图6.10(b)，求值，6^4=2，5+20=25，9&25=9，2|9=11，计算结果：  11
//      {"A|B^C&D+E",        "|A^B&C+DE",  "ABCDE+&^|"},  //思考题6-7习图6.10(c)，求值，5+20=25，9&25=9，4^9=13，6|13=15，计算结果：15
            };
    //以下做不了，没有识别数字串
    //  String postfix="45 10 15 - 25 35 + 60 40 - / * + 11 -";       //第4版图6.21 后缀表达式
    //  String postfix="123 10 45 50- 20+ * 35 25- 2* 10 +/ + 11 -  ";//例4.2 后缀表达式
//     {"123+20*(3|12^15&4+6)/((35-20)%10+5)-11",  "",  ""}//例4.2 后缀表达式
    //String infix="+123+10*(-50+45+20)/((-25+35)%2+10)+(-11)+0"; //例4.6

    //变量取值表，默认变量集合是{"A","B","C","D","E","F","G","H","I"}，单字母且从A开始递增；
    public static int[][] values={
//    	    {1,2,3,4,5},                            //例4.2图4.5，后缀表达式求值（使用栈），计算结果：  4
//    	    {1,2,3,4,5,6,7,8},
//            {13,17,45,50,20,35,20,10,5},           //图6.40a，求值，计算结果：30
            {6,4,9,5,20}           //图6.40b，求值
    };
    public static void main(String[] args)
    {
        ExpressionBinaryTree expbitree = new ExpressionBinaryTree(expstr[0][0]); //由中缀表达式构造，使用栈；递归算法不成立
//        ExpressionBinaryTree expbitree = new ExpressionBinaryTree();
//        expbitree.createByPrefix(expstr[0][1]);          //由前缀表达式构造，递归算法，默认从左向右
//        expbitree.createByPostfix(expstr[0][2]);         //由后缀表达式构造，递归算法，根在最后，从右向左
//        expbitree.createByPostfixStack(expstr[0][2]);    //由后缀表达式构造，使用栈

//        expbitree.createPostfix(expstr[0][2]);           //由后缀表达式构造，递归算法，根在最后，从左向右，不行
//        expbitree.createInfix(expstr[0][0]);             //由中缀表达式构造，递归算法，从左向右，不行

        System.out.print("中缀表达式：  ");  expbitree.inorder();
//      System.out.println("中缀表达式：  "+expbitree.toString());
        System.out.print("前缀表达式：  ");  expbitree.preorder();
        System.out.print("后缀表达式：  ");  expbitree.postorder();
//      System.out.print("计算结果：  "+expbitree.toValue());
//        System.out.println("计算结果：  "+expbitree.toValue(values[0]));

        int A=values[0][0], B=values[0][1], C=values[0][2], D=values[0][3], E=values[0][4];
////        int F=values[0][5], G=values[0][6], H=values[0][7];
        int value=(A^(B|C)&D)+E;                 //图6.41(b)，计算结果：23，HashMap((%,3),(&,8),(*,3),(+,4),(|,10),(-,4),(^,9),(/,3))
////        int value=((A^(B|C))&D)+E;               //习图6.10(a)，计算结果：21
////        int value=A^B|C&D+E;                     //习图6.10(b)，计算结果：11
//        int value=A|B^C&D+E;                       //习图6.10(c)，计算结果：15
        System.out.println(expstr[0][0]+"="+value);
    }
}
