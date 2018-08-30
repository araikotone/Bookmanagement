package test;

public class Calculation

{



	// 査定結果
    private static int RP;

    /**
     * 査定計算
     * @param val 計算対象金額
     * @param g1 ラジオボタン区分
     * @return 査定計算結果
     */
    public static int chkresultPrice(double val, String g1) {
        // Javaの場合
        if ("Java".equals(g1)) {
            RP = (int) (val * 0.7);
        }
        // MySQLの場合
        else if ("MySQL".equals(g1)) {
            RP = (int) (val * 0.6);
        }
        // C++の場合
        else if ("C++".equals(g1)) {
            RP = (int) (val * 0.65);
        }
        // Otherの場合
        else if ("Other".equals(g1)) {
            RP = (int) (val * 0.5);
        }
        return RP;
    }

}