package com.ljx.ologlib.api

class StringUtils {

    companion object {
        /**
         * @author wyd 2012-12-17
         * 一个字符串在另一个字符串中 第N次出现的位置
         * (如果是倒序则将indexOf改成lastIndexOf)
         * 使用 :
         * int num = getStringNum("aaasdfzsssasef", "s", 10);
         * System.out.println(num);
         * @param sA 是第一个字符(大的字符串)
         * @param sB 被包含的字符串
         * @param num sB在sA 中第几次出现  num 必须大于1
         * @return sB 在sA 中第num次出现的位置
         */
        fun getStringNum(sA: String, sB: String, num: Int): Int {
            var weizhi = 0
            for (i in 1 until num) {
                if (weizhi == 0) {
                    weizhi = sA.indexOf(sB)
                }
                // System.out.println(sB+" 在"+sA+" 中第"+i+"次出现的位置"+(weizhi+1));
                weizhi = sA.indexOf(sB, weizhi + 1)
            }
            return weizhi
        }
    }
}