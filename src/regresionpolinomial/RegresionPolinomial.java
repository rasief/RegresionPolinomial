package regresionpolinomial;

public class RegresionPolinomial {

    public static void main(String[] args) {
        //double[] x = {1, 2, 3, 4, 5};
        //double[] y = {3.8, 4.5, 5, 5.3, 5.6};
        
        double[] x = {0, 1, 2, 3, 4};
        double[] y = {2.5, 1.7, 1.2, 0.9, 0.6};
        
        double[] a = regresionPolinomial(x, y);
        System.out.println("y = " + a[0] + " + " + a[1] + "x + " + a[2] + "x^2");
        
        double coefCorrelacion = coefCorrelacion(x, y, a);
        System.out.println("Coeficiente de correlación: " + (coefCorrelacion * 100) + "%");
    }
    
    private static double[] regresionPolinomial(double[] x, double[] y) {
        double sumaX = 0, sumaX2 = 0, sumaX3 = 0, sumaX4 = 0, sumaY = 0, sumaXY = 0, sumaX2Y = 0;
        for (int i = 0; i < x.length; i++) {
            sumaX += x[i];
            sumaX2 += Math.pow(x[i], 2);
            sumaX3 += Math.pow(x[i], 3);
            sumaX4 += Math.pow(x[i], 4);
            sumaY += y[i];
            sumaXY += (x[i] * y[i]);
            sumaX2Y += (Math.pow(x[i], 2) * y[i]);
        }
        
        double[][] a = {{x.length, sumaX, sumaX2}, {sumaX, sumaX2, sumaX3}, {sumaX2, sumaX3, sumaX4}};
        double[] b = {sumaY, sumaXY, sumaX2Y};
        
        return gauss(a, b);
    }
    
    private static double[] gauss(double[][] a, double[] b) {
        double[][] aAux = duplicarArreglo(a);
        double[] bAux = duplicarArreglo(b);
        
        int n = bAux.length;
        
        //Se construye la matriz triangular superior
        for (int i = 0; i < n - 1; i++) {
            //Escalonamiento
            for (int k = i; k < n; k++) {
                double mayorAux = Math.abs(aAux[k][k]);
                for (int j = i; j < n; j++) {
                    if (mayorAux < Math.abs(aAux[k][j])) {
                        mayorAux = Math.abs(aAux[k][j]);
                    }
                }
                for (int j = i; j < n; j++) {
                    aAux[k][j] /= mayorAux;
                }
                bAux[k] /= mayorAux;
            }
            
            //Pivoteo
            double mayorAux = Math.abs(aAux[i][i]);
            int filaCambio = i;
            for (int j = i + 1; j < n; j++) {
                if (mayorAux < Math.abs(aAux[j][i])) {
                    mayorAux = Math.abs(aAux[j][i]);
                    filaCambio = j;
                }
            }
            double[] filaAux = aAux[i];
            aAux[i] = aAux[filaCambio];
            aAux[filaCambio] = filaAux;
            
            double valorAux = bAux[i];
            bAux[i] = bAux[filaCambio];
            bAux[filaCambio] = valorAux;
            
            //Eliminación
            for (int j = i + 1; j < n; j++) {
                double fact = aAux[j][i] / aAux[i][i];
                
                for (int k = 0; k < n; k++) {
                    aAux[j][k] -= (aAux[i][k] * fact);
                }
                
                bAux[j] -= (bAux[i] * fact);
            }
        }
        
        //Solución hacia atrás
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double resta = 0;
            for (int j = i + 1; j < n; j++) {
                resta += aAux[i][j] * x[j];
            }
            x[i] = (bAux[i] - resta) / aAux[i][i];
        }
        
        return x;
    }
    
    private static double[][] duplicarArreglo(double[][] m) {
        double[][] duplicado = new double[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            System.arraycopy(m[i], 0, duplicado[i], 0, m[i].length);
        }
        
        return duplicado;
    }
    
    private static double[] duplicarArreglo(double[] v) {
        double[] duplicado = new double[v.length];
        System.arraycopy(v, 0, duplicado, 0, v.length);
        
        return duplicado;
    }
    
    private static double coefCorrelacion(double[] x, double[] y, double[] coef) {
        double yp = promedio(y);
        double St = 0, Sr = 0;
        for (int i = 0; i < x.length; i++) {
            St += Math.pow(y[i] - yp, 2);
            Sr += Math.pow(y[i] - coef[0] - coef[1] * x[i] - coef[2] * Math.pow(x[i], 2), 2);
        }
        System.out.println("St = " + St);
        System.out.println("Sr = " + Sr);
        return Math.sqrt((St - Sr) / St);
    }
    
    private static double promedio(double[] v) {
        double suma = 0;
        for (int i = 0; i < v.length; i++) {
            suma += v[i];
        }
        return suma / v.length;
    }
}