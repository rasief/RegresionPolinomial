package regresionpolinomial;

/**
 *
 * @author Usuario
 */
public class Test {

    public static void main(String[] args) {
        double[] x = {1, 2, 3, 4, 5};

        double[] y = {1.7, 3, 4, 4.6, 5};
        double sumaX = 0;
        double sumaxpow3 = 0;
        double sumaY = 0;
        double sumaxy = 0;
        double[] xpow2 = new double[x.length];
        double sumaxpow2 = 0;
        double[] xpow3 = new double[x.length];
        double sumaxxpow2 = 0;
        double[] xpow4 = new double[x.length];
        double sumaxpow4 = 0;
        double[] xy = new double[x.length];
        double[] xpow2y = new double[x.length];
        double sumaxpow2y = 0;
        double[] ypypow2 = new double[x.length];
        double sumaypypow2 = 0;
        double sy = 0;
        double promY = 0;
        double promX = 0;
        double syx = 0;

        double[] ya0a1xa2xpow2 = new double[x.length];
        double sumaya0a1xa2xpow2 = 0;
        double[] yaax = new double[x.length];

        double a0 = 0;
        double a1 = 0;
        double a2 = 0;
        double r = 0;

        for (int i = 0; i < x.length; i++) {
            sumaX += x[i];
            sumaY += y[i];

        }
        promY = sumaY / y.length;
        promX = sumaX / x.length;
        for (int i = 0; i < x.length; i++) {

            xpow4[i] = Math.pow(x[i], 4);
            xpow2[i] = Math.pow(x[i], 2);
            xpow3[i] = Math.pow(x[i], 3);
            xy[i] = x[i] * y[i];
            xpow2y[i] = Math.pow(x[i], 2) * y[i];
            ypypow2[i] = Math.pow((y[i] - promY), 2);
        }

        for (int i = 0; i < x.length; i++) {
            sumaypypow2 += ypypow2[i];
            sumaxpow2 += xpow2[i];
            sumaxpow3 += xpow3[i];
            sumaxpow4 += xpow4[i];
            sumaxpow2y += xpow2y[i];
            sumaxy += xy[i];

        }
        System.out.println(" sumaypypow2: " + sumaypypow2 + " sumaxpow2:  " + sumaxpow2 + " sumaxpow3: " + sumaxpow3);
        System.out.println("sumaxpow4: " + sumaxpow4 + " sumaxpow2y:  \n" + sumaxpow2y + " sumaxy: \n" + sumaxy + "sumax " + xpow2y + "sumaY " + sumaY + "sumaxx " + sumaxpow2y);

        double[][] a = {{x.length, sumaX, sumaxpow2},
        {sumaX, sumaxpow2, sumaxpow3}, {sumaxpow2, sumaxpow3, sumaxpow4}

        };
        double[] b = {sumaY, sumaxy, sumaxpow2y};
        System.out.println("Matriz Original");
        imprimirSistema(a, b);
        for (int i = 0; i < a.length; i++) {
            double pivote = a[i][i];
            if (pivote == 0) {
                for (int m = i + 1; m < a.length; m++) {
                    if (a[m][i] != 0) {
                        double[] renglonAux = a[i];
                        a[i] = a[m];
                        a[m] = renglonAux;

                        double valorAux = b[i];
                        b[i] = b[m];
                        b[m] = valorAux;

                        pivote = a[i][i];
                        break;
                    }
                }
                System.out.println("Cambio de renglón");
                imprimirSistema(a, b);
            }
            if (pivote != 1) {
                for (int j = 0; j < a[i].length; j++) {
                    a[i][j] = a[i][j] / pivote;

                }
                b[i] = b[i] / pivote;
                System.out.println("Pivoteo");
                imprimirSistema(a, b);

            } else {
                System.out.println("No requiere Pivoteo");
            }

            for (int l = 0; l < a.length; l++) {
                if (i != l) {
                    double multiplicador = a[l][i];
                    for (int j = 0; j < a[l].length; j++) {
                        a[l][j] = a[l][j] - multiplicador * a[i][j];
                    }
                    b[l] = b[l] - multiplicador * b[i];
                }
            }
            System.out.println("Reduccion");
            imprimirSistema(a, b);
        }
        for (int i = 0; i < b.length; i++) {
            a0 = b[0];
            a1 = b[1];
            a2 = b[2];
        }
        for (int i = 0; i < x.length; i++) {
            ya0a1xa2xpow2[i] = Math.pow((y[i] - a0 - a1 * x[i] - a2 * xpow2[i]), 2);
        }
        for (int i = 0; i < x.length; i++) {
            sumaya0a1xa2xpow2 += ya0a1xa2xpow2[i];
        }
        System.out.println("sumaya0a1xa2xpow2 " + sumaya0a1xa2xpow2);
        System.out.println("a0: " + a0 + " a1: " + a1 + " a2: " + a2);
        System.out.println("st: " + sumaypypow2);
        System.out.println("sr: " + sumaya0a1xa2xpow2);
        r = Math.sqrt((sumaypypow2 - sumaya0a1xa2xpow2) / (sumaypypow2));
        System.out.println("r= " + r);
        r = r * 100;
        System.out.println("r= " + r + "%");
        System.out.println("y= " + a0 + " +" + a1 + "x +" + a2 + "x¨2");

    }

    public static void imprimirSistema(double[][] a, double[] b) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println("   " + b[i]);
        }
    }
}
