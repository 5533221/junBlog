package com.jun;

import com.alibaba.excel.util.StringUtils;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.Scanner;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/9 20:25
 */
@SpringBootTest(classes = softTest.class)
public class softTest {



    public static void main(String[] args) {

//        testOne();
//        testTwo();
//        testThree();
        testFour();
    }

    private static void testFour() {

        //用正则表达式 校验密码强度，并返回是否可用标识
        //九九乘法表

    }

    private static void testThree() {

        //枪托 枪管 枪机 的价钱
        int qt_price=30;
        int qg_price=25;
        int qj_price=45;

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入枪托数量");
        int qt= scanner.nextInt();
        if(qt<=0 ||qt >80 ){

            System.out.println("枪托数量不合理！！");
            return;
        }

        System.out.println("请输入枪管数量");
        int qg=scanner.nextInt();

        if(qg<=0||qg>90){

            System.out.println("枪管数量不合理！！");
            return;
        }


        System.out.println("请输入枪机数量");
        int qj=scanner.nextInt();
        if(qj<=0||qj>70){

            System.out.println("枪机数量不合理！！");
            return;
        }


        //买时的价钱
        int buy_money = qt * qt_price + qg * qg_price + qj * qj_price;
        //最大的限额
        int max_money=80*qt_price + 90*qg_price + 70 *qj_price;

        double yongjing=0;

        //判断佣金
        if(buy_money <= max_money){


            double part2=0;
            double part1=0;
            double part3=0;

            if(buy_money>1000){

                //第一部分
                part1=  (1000*0.1);

                if(buy_money<=1800){

                    part2=  ((buy_money-1000)*0.15);

                }else if(buy_money>1800){

                    part2=(1800-1000)*0.15;
                    part3=  ((buy_money-1800)*0.20);
                }

            }else if(buy_money <=1000){

                    part1= buy_money*0.1;
            }
           yongjing= part1+part2+part3;
            System.out.println("买的价钱:"+buy_money);
            System.out.println("part1:"+part1);
            System.out.println("part2:"+part2);
            System.out.println("part3:"+part3);
            System.out.println("佣金为:"+yongjing);
            System.out.println("枪机数量为"+qj+",枪托数量为:"+qt+",枪管数量为:"+qg+",销售额为:"+buy_money+",佣金为:"+yongjing);


        }





    }

    private static void testTwo() {

//        需求描述：输入年月日（年份取值范围[1600,2100]），求出：下一天
        //平年 2月  28天
        //闰年 2月  29天
        //判断闰年:  1、非整百年：年数除以4余数为1是闰年
//                 2、整百年：年数除以400余数为1是闰年

        int year=0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入年:");

        year = scanner.nextInt();
        if(year<1600 || year >2100){
            System.out.println("年份过大或年份过小");
            return;
        }
        System.out.println("请输入月:");
        int month = scanner.nextInt();

        System.out.println("请输入日:");
        int day = scanner.nextInt();

        switch (month){

            case 2:
                if((year % 4 == 1 ) || year % 400 ==1){
                    System.out.println(year+"是平年");

                    if(day>0 && day<28){
                        day+=1;
                        System.out.println("下一天是:"+year+"年"+month+"月"+day+"日");
                        break;
                    }else if(day==28){
                        month+=1;
                        day=1;
                        System.out.println("下一天是:"+year+"年"+month+"月"+day+"日");
                        break;
                    }else{
                        System.out.println("输入天数超过！！");
                        break;
                    }
                }else{
                    System.out.println(year+"是闰年");
                    if(day>0 && day<29){
                        day+=1;
                        System.out.println("下一天是:"+year+"年"+month+"月"+day+"日");
                        break;
                    }else if(day==29){
                        month+=1;
                        day=1;
                        System.out.println("下一天是:"+year+"年"+month+"月"+day+"日");
                        break;
                    }else{
                        System.out.println("输入天数超过！！");
                        break;
                    }
                }
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:

                JudgmentDaysHas31(year,month,day);
                break;
            case 4:
            case 6:
            case 9:
            case 11:

                JudgmentDaysHas30(year,month,day);
              break;


        }


        }

        public static void   JudgmentDaysHas31(int year,int month,int day) {

            if(day<32){
                if(month==12 && day==31){

                    year+=1;month=1;day=1;

                    System.out.println("下一天是:"+year+"年"+month+"月"+day+"日");
                } else if(day==31){
                    month+=1;
                    day=1;
                    System.out.println("下一天是:"+year+"年"+month+"月"+day+"日");
                }else{
                    day+=1;
                    System.out.println("下一天是:"+year+"年"+month+"月"+day+"日");
                }

            }else{
                System.out.println("天数超过！！");

            }

        }

        public static void   JudgmentDaysHas30(int year,int month,int day) {

        if(day<31){

            if(day==30){
                month+=1;
                day=1;
                System.out.println("下一天是:"+year+"年"+month+"月"+day+"日");
            }else{
                day+=1;
                System.out.println("下一天是:"+year+"年"+month+"月"+day+"日");
            }

        }
        else{
            System.out.println("天数超过！！");

        }

    }





        private static void testOne() {

        /**
         * 输入三角形三条边（实型数，取值范围[1,200]），
         * 判断是什么三角形（等边、等腰、一般、非三角形、输入数据非法）
         * */

        Scanner scanner = new Scanner(System.in);

        int num1=0;
        int num2=0;
        int num3=0;
        System.out.println("请输入第一边边长:");

        String temp=scanner.next();

        if(!StringUtils.isNumeric(temp)){
            System.out.println("输入的不是数字");
            return;
        }else{
            num1 = Integer.parseInt(temp);
        }

        System.out.println("请输入第二边边长:");
        String temp2=scanner.next();
        if(!StringUtils.isNumeric(temp2)){
            System.out.println("输入的不是数字");
            return;
        }else{
            num2 = Integer.parseInt(temp2);
        }

        System.out.println("请输入第三边边长:");
        String temp3=scanner.next();
        if(!StringUtils.isNumeric(temp3)){
            System.out.println("输入的不是数字");
            return;
        }else{
            num3 = Integer.parseInt(temp3);
        }

        if(num1<1 || num2<1 || num3 <1 ||num1>200 ||num2>200 || num3>200){

            System.out.println("数据不合法");

        }else if(num1+num2<num3 || num1+num3<num2 || num2+num3<num1){

             System.out.println("这不是三角形");
         }
         else if(num1==num2 || num1==num3 || num2==num3 ){

             if(num1==num2 && num1==num3){
                 System.out.println("这是等边三角形");
             }else{
                 System.out.println("这是等腰三角形");
             }

        }else {

             System.out.println("这是普通三角形");
         }





    }

}
