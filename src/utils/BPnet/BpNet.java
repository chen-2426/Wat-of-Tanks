package utils.BPnet;
/**
 * @author chenxi
 * @id 2022119001
 */
@SuppressWarnings({"all"})
public class BpNet {
    private static final int IM = 4;                   //输入层数量
    private static final int RM = 8;                   //隐含层数量
    private static final int OM = 1;                   //输出层数量
    private double learnRate = 0.55;                   //学习率
    private double Win[][] = new double[IM][RM];      //输入到隐含连接权值
    private double dWin[][] = new double[IM][RM];     //隐含到输出连接权值修正
    private double Wout[][] = new double[RM][OM];     //隐含到输出连接权值
    private double dWout[][] = new double[RM][OM];    //隐含到输出连接权值修正
    private double Xi[] = new double[IM];             //输入计算的变量
    private double Xj[] = new double[RM];
    private double XjActive[] = new double[RM];       //中间层结果
    private double Xk[] = new double[OM];
    private double Ek[] = new double[OM];             //真实值与预测值之间的误差
    private double J = 0.1;

    public static void main(String[] arg) {
        BpNet bpNet = new BpNet();
        bpNet.train();
    }

    public void train() {
        double Well_being[] = {73.84615385, 76.92307692, 64.61538462, 63.07692308, 58.46153846, 67.69230769, 60, 78.46153846,
                56.92307692, 67.69230769, 60, 58.46153846, 53.84615385, 73.84615385, 72.30769231, 47.69230769, 66.15384615, 70.76923077,
                66.15384615, 67.69230769, 67.69230769, 72.30769231, 56.92307692, 66.15384615, 55.38461538, 72.30769231, 66.15384615,
                67.69230769, 61.53846154, 50.76923077, 55.38461538, 58.46153846, 56.92307692, 69.23076923, 76.92307692, 64.61538462,
                66.15384615, 53.84615385, 80, 69.23076923, 69.23076923, 61.53846154, 60, 66.15384615, 69.23076923, 72.30769231, 56.92307692,
                66.15384615, 72.30769231, 64.61538462, 89.23076923, 73.84615385, 72.30769231, 61.53846154, 53.84615385, 69.23076923, 58.46153846,
                66.15384615, 70.76923077, 40, 52.30769231, 90.76923077, 43.07692308, 72.30769231, 81.53846154, 55.38461538, 86.15384615,
                81.53846154, 60, 53.84615385, 70.76923077, 84.61538462, 52.30769231, 72.30769231, 63.07692308, 60, 70.76923077, 64.61538462,
                61.53846154, 58.46153846, 80, 69.23076923, 75.38461538, 61.53846154, 66.15384615, 63.07692308, 70.76923077, 64.61538462,
                95.38461538, 67.69230769, 75.38461538, 47.69230769, 61.53846154, 89.23076923, 58.46153846, 75.38461538, 73.84615385, 47.69230769,
                61.53846154, 78.46153846, 61.53846154, 66.15384615, 93.84615385, 61.53846154, 78.46153846, 73.84615385, 55.38461538, 70.76923077,
                66.15384615, 69.23076923, 66.15384615, 58.46153846, 69.23076923, 58.46153846, 80, 75.38461538, 78.46153846, 73.84615385,
                69.23076923, 75.38461538, 53.84615385, 70.76923077, 67.69230769, 80, 52.30769231, 44.61538462, 83.07692308, 69.23076923,
                66.15384615, 40, 66.15384615, 61.53846154, 78.46153846, 46.15384615, 67.69230769, 46.15384615, 67.69230769, 76.92307692,
                47.69230769, 70.76923077, 78.46153846, 58.46153846, 58.46153846, 46.15384615, 43.07692308, 55.38461538, 63.07692308, 70.76923077,
                49.23076923, 67.69230769};
        double money[] = {60, 65, 85, 60, 80, 55, 60, 60, 75, 65, 70, 80, 75, 80, 80, 65, 60, 65, 80, 55, 80, 75, 55, 50, 65, 80, 85, 60, 70,
                70, 90, 75, 85, 65, 85, 65, 65, 60, 75, 60, 75, 80, 90, 80, 80, 80, 60, 65, 60, 90, 65, 75, 60, 95, 50, 75, 70, 60, 75, 75, 90, 85,
                65, 100, 100, 55, 65, 80, 100, 60, 60, 90, 70, 75, 80, 80, 80, 75, 75, 75, 100, 55, 75, 85, 75, 75, 60, 70, 95, 80, 90, 65, 60, 100,
                65, 75, 80, 85, 75, 75, 75, 75, 80, 80, 75, 80, 80, 90, 70, 80, 80, 60, 75, 75, 80, 65, 70, 95, 80, 75, 75, 80, 70, 80, 60, 85, 100,
                65, 65, 60, 45, 70, 85, 60, 75, 80, 50, 90, 65, 80, 65, 70, 80, 55, 70, 75, 80, 75, 80, 60};
        double emotion[] = {80, 70, 100, 47.5, 75, 65, 80, 72.5, 72.5, 80, 95, 80, 80, 77.5, 100, 75, 77.5, 70, 80, 62.5, 80, 90, 82.5,
                62.5, 70, 85, 87.5, 62.5, 62.5, 65, 82.5, 77.5, 75, 77.5, 72.5, 70, 70, 65, 82.5, 75, 75, 80, 77.5, 95, 80, 80, 72.5, 75,
                37.5, 90, 72.5, 70, 75, 87.5, 72.5, 77.5, 65, 50, 77.5, 50, 75, 67.5, 55, 100, 97.5, 57.5, 67.5, 70, 97.5, 60, 52.5, 100,
                82.5, 80, 72.5, 55, 77.5, 75, 82.5, 67.5, 90, 77.5, 80, 75, 87.5, 75, 57.5, 60, 95, 80, 80, 77.5, 52.5, 95, 65, 82.5, 87.5,
                70, 67.5, 92.5, 75, 60, 100, 72.5, 70, 67.5, 82.5, 82.5, 77.5, 82.5, 100, 60, 70, 92.5, 70, 75, 72.5, 95, 77.5, 67.5, 55,
                75, 50, 80, 60, 75, 100, 67.5, 62.5, 60, 60, 70, 90, 65, 62.5, 75, 62.5, 87.5, 77.5, 70, 85, 72.5, 82.5, 62.5, 62.5, 70, 80,
                72.5, 95, 72.5};
        double health[] = {60, 60, 60, 80, 65, 60, 65, 40, 70, 65, 80, 70, 65, 55, 60, 70, 60, 60, 70, 60, 70, 50, 90, 75, 75, 50, 55, 40,
                60, 65, 60, 60, 60, 70, 50, 65, 60, 70, 60, 70, 75, 75, 60, 60, 100, 60, 100, 75, 55, 60, 75, 70, 70, 100, 100, 60, 75, 90,
                80, 75, 60, 60, 80, 70, 40, 60, 55, 70, 80, 90, 65, 60, 90, 70, 65, 75, 65, 75, 80, 75, 65, 75, 60, 75, 55, 60, 60, 60, 60,
                55, 65, 75, 65, 45, 60, 60, 75, 55, 65, 70, 65, 60, 35, 55, 60, 75, 65, 90, 50, 75, 60, 70, 80, 65, 50, 60, 65, 55, 70, 65,
                80, 60, 55, 55, 60, 80, 70, 60, 50, 60, 60, 70, 50, 75, 60, 80, 70, 55, 65, 60, 55, 65, 90, 65, 80, 100, 60, 75, 65, 75};
        double education[] = {35, 30, 25, 30, 45, 55, 40, 25, 50, 45, 35, 40, 85, 35, 25, 55, 30, 35, 20, 35, 30, 35, 50, 25, 40, 25, 35,
                35, 25, 55, 40, 40, 30, 40, 40, 35, 40, 65, 25, 30, 35, 20, 40, 35, 20, 40, 45, 40, 35, 40, 30, 35, 30, 50, 55, 40, 45, 20, 30,
                70, 20, 20, 65, 40, 20, 55, 30, 40, 40, 35, 35, 30, 35, 60, 35, 60, 30, 40, 65, 60, 30, 55, 20, 45, 25, 40, 60, 40, 20, 35, 50,
                45, 35, 20, 45, 25, 40, 50, 30, 20, 40, 45, 20, 40, 30, 25, 45, 45, 35, 55, 30, 50, 25, 50, 30, 35, 35, 20, 25, 40, 50, 45, 35,
                20, 60, 60, 20, 60, 45, 60, 30, 50, 20, 60, 45, 60, 40, 20, 50, 30, 20, 60, 60, 65, 50, 30, 60, 30, 60, 25};
        bpNetinit();
        System.out.println("training...");
        while (J > Math.pow(10, -5)) {
            for (int i = 0; i < 150; i++) {
                double input[] = {money[i] / 100.0, emotion[i] / 100.0, health[i] / 100.0, education[i] / 100.0};
                double output[] = {Well_being[i] / 100.0};
                bpNetForwardProcess(input, output);
                bpNetReturnProcess();

            }
        }
        for (int i = 0; i < 150; i++) {
            System.out.printf("%.1f  ", Well_being[i]);
            double input[] = {money[i] / 100.0, emotion[i] / 100.0, health[i] / 100.0, education[i] / 100.0};
            double output[] = {Well_being[i] / 100.0};
            System.out.printf("%f  ", bpNetOut(input)[0] * 100.0);
            System.out.println("J=" + J);
        }
    }

    public void bpNetinit() {
        //初始化权值和清零//
        for (int i = 0; i < IM; i++)
            for (int j = 0; j < RM; j++) {
                Win[i][j] = 0.5 - Math.random();
                Xj[j] = 0;
            }
        for (int j = 0; j < RM; j++)
            for (int k = 0; k < OM; k++) {
                Wout[j][k] = 0.5 - Math.random();
                Xk[k] = 0;
            }
    }

    /**
     * @param inputParameter  归一化后的理想输入数组值
     * @param outputParameter 归一化后的理想输出数组值
     */
    public void bpNetForwardProcess(double inputParameter[], double outputParameter[]) {
        for (int i = 0; i < IM; i++) {
            Xi[i] = inputParameter[i];
        }
        //隐含层权值和计算//
        for (int j = 0; j < RM; j++) {
            Xj[j] = 0;
            for (int i = 0; i < IM; i++) {
                Xj[j] = Xj[j] + Xi[i] * Win[i][j];
            }
        }
        //隐含层S激活输出//
        for (int j = 0; j < RM; j++) {
            XjActive[j] = 1 / (1 + Math.exp(-Xj[j]));
        }
        //输出层权值和计算//
        for (int k = 0; k < OM; k++) {
            Xk[k] = 0;
            for (int j = 0; j < RM; j++) {
                Xk[k] = Xk[k] + XjActive[j] * Wout[j][k];
            }
        }
        //计算输出与理想输出的偏差//
        for (int k = 0; k < OM; k++) {
            Ek[k] = outputParameter[k] - Xk[k];
        }
        //求误差和//
        J = 0;
        for (int k = 0; k < OM; k++) {
            J = J + Ek[k] * Ek[k] / 2.0;
        }
    }

    //
    //BP神经网络反向传播修改连接权值
    //
    public void bpNetReturnProcess() {
        //反向传播修改权值//
        for (int i = 0; i < IM; i++)  //输入到隐含权值修正
        {
            for (int j = 0; j < RM; j++) {
                for (int k = 0; k < OM; k++) {
                    dWin[i][j] = dWin[i][j] + learnRate * (Ek[k] * Wout[j][k] * XjActive[j] * (1 - XjActive[j]) * Xi[i]);
                }
                Win[i][j] = Win[i][j] + dWin[i][j];
            }
        }

        for (int j = 0; j < RM; j++)  //隐含到输出权值修正
        {
            for (int k = 0; k < OM; k++) {
                dWout[j][k] = learnRate * Ek[k] * XjActive[j];
                Wout[j][k] = Wout[j][k] + dWout[j][k];
            }
        }
    }

    //
    //输出结果

    /**
     * @param inputParameter 测试的归一化后的输入数组
     * @return 返回归一化后的BP神经网络输出数组
     */
    public double[] bpNetOut(double[] inputParameter) {
        //在线学习后输出预测值//
        for (int i = 0; i < IM; i++) {
            Xi[i] = inputParameter[i];
        }
        //隐含层权值和计算//
        for (int j = 0; j < RM; j++) {
            Xj[j] = 0;
            for (int i = 0; i < IM; i++) {
                Xj[j] = Xj[j] + Xi[i] * Win[i][j];
            }
        }
        //隐含层S激活输出//
        for (int j = 0; j < RM; j++) {
            XjActive[j] = 1 / (1 + Math.exp(-Xj[j]));
        }
        //输出层权值和计算//
        double Uk[] = new double[OM];
        for (int k = 0; k < OM; k++) {
            Xk[k] = 0;
            for (int j = 0; j < RM; j++) {
                Xk[k] = Xk[k] + XjActive[j] * Wout[j][k];
                Uk[k] = Xk[k];
            }
        }
        return Uk;
    }
}
