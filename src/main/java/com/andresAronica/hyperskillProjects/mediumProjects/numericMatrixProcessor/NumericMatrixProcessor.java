package com.andresAronica.hyperskillProjects.mediumProjects.numericMatrixProcessor;

import java.util.Arrays;
import java.util.Scanner;

public class NumericMatrixProcessor {

    private static Scanner sc = new Scanner(System.in);
    private static int rowsA;
    private static int rowsB;
    private static int columnsA;
    private static int columnsB;

    // Todavia no controla si se pueden sumar o no dos matrices.
    public static void main(String[] args) {
        recursiveMenu();
    }

    private static void recursiveMenu() {

        int action;
        double[][] matrixA;
        double[][] matrixB;

        System.out.println("1. Add matrices\n" +
                "2. Multiply matrix by a constant\n" +
                "3. Multiply matrices\n" +
                "4. Transpose matrix\n" +
                "5. Calculate a determinant\n" +
                /*"6. Inverse matrix\n" +*/
                "0. Exit");

        System.out.println("Your choice: ");

        action = sc.nextInt();

        switch (action) {
            case 0:
                System.exit(0);
                break;
            case 1:
                //add Matrices
                matrixA = getFirstMatrixFromScanner();
                matrixB = getSecondMatrixFromScanner();
                addMatrices(matrixA, matrixB, rowsA, columnsA);
                recursiveMenu();
                break;
            case 2:
                // multiply matrix by a constant
                matrixA = getFirstMatrixFromScanner();
                multiplyMatrixByConstant(matrixA, rowsA, columnsA);
                recursiveMenu();
                break;
            case 3:
                // multiply matrices
                matrixA = getFirstMatrixFromScanner();
                matrixB = getSecondMatrixFromScanner();
                multiplyMatrices(matrixA, matrixB, rowsA, columnsA, rowsB, columnsB);
                recursiveMenu();
                break;
            case 4:
                // transpose matrix
                System.out.println();
                System.out.println("1. Main diagonal\n" +
                        "2. Side diagonal\n" +
                        "3. Vertical line\n" +
                        "4. Horizontal line");
                System.out.println("Your choice: ");
                int selection = sc.nextInt();

                matrixA = getFirstMatrixFromScanner();

                transposeMatrix(matrixA, rowsA, columnsA, selection);
                break;
            case 5:
                matrixA = getFirstMatrixFromScanner();
                System.out.printf("The result is:\n%.2f", determinantOfMatrix(matrixA, rowsA));
                break;
            /*case 6:
                matrixA = getFirstMatrixFromScanner();
                inverseMatrix(matrixA, rowsA, columnsA);
                break;*/
        }
    }

    private static double[][] getFirstMatrixFromScanner() {
        System.out.println("Enter size of first matrix: ");
        rowsA = sc.nextInt();
        columnsA = sc.nextInt();
        sc.nextLine();
        double[][] matrixA = new double[rowsA][columnsA];

        System.out.println("Enter first matrix");
        loadMatrix(matrixA, rowsA, columnsA);
        return matrixA;
    }

    private static double[][] getSecondMatrixFromScanner() {
        System.out.println("Enter size of second matrix: ");
        rowsB = sc.nextInt();
        columnsB = sc.nextInt();
        sc.nextLine();
        double[][] matrixB = new double[rowsB][columnsB];

        System.out.println("Enter second matrix");
        loadMatrix(matrixB, rowsB, columnsB);
        return matrixB;
    }

    private static void loadMatrix(double[][] matrixA, int rows, int columns) {
        String stringLines;
        String[] row;
        for (int i = 0; i < rows; i++) {
            stringLines = sc.nextLine();
            row = stringLines.split(" ");
            for (int j = 0; j < columns; j++) {
                matrixA[i][j] = Double.parseDouble(row[j]);
            }
        }
    }

    private static void printMatrix(double[][] matrixToPrint, int rows, int columns) {
        String temp;
        String output;
        for (int i = 0; i < rows; i++) {
            temp = Arrays.toString(matrixToPrint[i]);
            output = temp.replaceAll("[\\[\\],]+", "");
            System.out.println(output);
        }

    }

    private static void addMatrices(double[][] matrixA, double[][] matrixB, int rows, int columns) {
        double[][] addedMatrices = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                addedMatrices[i][j] = matrixA[i][j] + matrixB[i][j];
            }
        }
        printMatrix(addedMatrices, rows, columns);
        System.out.println();
    }

    private static void multiplyMatrixByConstant(double[][] matrixA, int rows, int columns) {
        double[][] multipliedMatrix = new double[rows][columns];

        int multiplier = sc.nextInt();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                multipliedMatrix[i][j] = matrixA[i][j] * multiplier;
            }
        }
        printMatrix(multipliedMatrix, rows, columns);
        System.out.println();
    }

    private static void multiplyMatrices(double[][] matrixA, double[][] matrixB, int rowsA, int columnsA, int rowsB, int columnsB) {
        if (columnsA != rowsB) {
            System.out.println("The matrices can't be multiplied with each other");
        } else {
            double[][] multipliedMatrices = new double[rowsA][columnsB];
            double sum = 0;
            for (int i = 0; i < rowsA; i++) {
                for (int j = 0; j < columnsB; j++) {
                    for (int k = 0; k < rowsB; k++) {
                        sum += matrixA[i][k] * matrixB[k][j];
                    }
                    multipliedMatrices[i][j] = sum;
                    sum = 0;
                }
            }
            System.out.println("The multiplication result is: ");
            printMatrix(multipliedMatrices, rowsA, columnsB);
            System.out.println();
        }
    }

    private static void transposeMatrix(double[][] matrix, int rows, int columns, int transposeDecision) {
        double[][] transposedMatrix;
        switch (transposeDecision) {
            case 1:
                // Mi propia atrocidad. Ya encontre la magia mistica loca
                /*for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {
                        if (i == 0 && j == 0) {
                            temp = matrix[i][j + 1];
                            transposedMatrix[i + 1][j] = temp;

                            temp = matrix[i + 1][j];
                            transposedMatrix[i][j + 1] = temp;
                            transposedMatrix[i][j] = matrix[i][j];
                        } else if (i == 1 && j == 1) {
                            temp = matrix[i - 1][j + 1];
                            transposedMatrix[i + 1][j - 1] = temp;

                            temp = matrix[i + 1][j - 1];
                            transposedMatrix[i - 1][j + 1] = temp;
                            transposedMatrix[i][j] = matrix[i][j];
                        } else if (i == j) {
                            temp = matrix[i][j - 1];
                            transposedMatrix[i - 1][j] = temp;

                            temp = matrix[i - 1][j];
                            transposedMatrix[i][j - 1] = temp;
                            transposedMatrix[i][j] = matrix[i][j];
                        }
                    }
                }*/

                // Main diagonal
                transposedMatrix = new double[rows][columns];

                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j <columns; j++) {
                        if (i == j) {
                            transposedMatrix[i][j] = matrix[i][j];
                        } else {
                            transposedMatrix[j][i] = matrix[i][j];
                        }
                    }
                }
                System.out.println("The result is: ");
                printMatrix(transposedMatrix, rows, columns);
                break;
            case 2:
                // Intento que casi me vuelve loco.
                /*
                int suppRows = 0;
                int suppColumns = 0;
                boolean firstPos = false;
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {
                        if (i == 0 && j == 0) {
                            transposedMatrix[rows-1][columns-1] = matrix[i][j];
                        } else if (i == 0 && j == (columns - 1)) {
                            // Lista la primer pos.
                            suppRows = i;
                            suppColumns = j;
                            firstPos = true;
                            temp = matrix[i][j-1];
                            transposedMatrix[i+1][j] = temp;

                            temp = matrix[i+1][j];
                            transposedMatrix[i][j-1] = temp;

                            transposedMatrix[i][j] = matrix[i][j];
                        } else if(firstPos && i == suppRows+1 && j == suppColumns - 1) {
                            firstPos = false;

                            temp = matrix[i-1][j-1];
                            transposedMatrix[i+1][j+1] = temp;

                            temp = matrix[i+1][j+1];
                            transposedMatrix[i-1][j-1] = temp;

                            temp = matrix[i][j-1];
                            transposedMatrix[i+1][j] = temp;

                            temp = matrix[i+1][j];
                            transposedMatrix[i][j-1] = temp;

                            transposedMatrix[i][j] = matrix[i][j];

                            suppRows++;
                            suppColumns--;

                        } else if (i == suppRows+1 && j == suppColumns - 1) {

                            temp = matrix[i-1][j-1];
                            transposedMatrix[i+1][j+1] = temp;

                            temp = matrix[i+1][j+1];
                            transposedMatrix[i-1][j-1] = temp;

                            transposedMatrix[i][j] = matrix[i][j];

                            temp = matrix[i-1][j];
                            transposedMatrix[i][j+1] = temp;

                            temp = matrix[i][j+1];
                            transposedMatrix[i-1][j] = temp;

                            transposedMatrix[i][j] = matrix[i][j];

                            suppRows++;
                            suppColumns--;

                        } else if (i == (rows - 1) && j == 0) {
                            // Lista la ultima pos.
                            temp = matrix[i-1][j];
                            transposedMatrix[i][j+1] = temp;

                            temp = matrix[i][j+1];
                            transposedMatrix[i-1][j] = temp;

                            transposedMatrix[i][j] = matrix[i][j];
                        } else if (i == rows-1 && j == columns-1) {
                            transposedMatrix[0][0] = matrix[i][j];
                        }
                    }
                }*/

                // Side diagonal
                transposedMatrix = new double[rows][columns];

                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j <columns; j++) {
                        if (i == 0 && j == columns -1 || i == rows-1 && j == 0) {
                            transposedMatrix[i][j] = matrix[i][j];
                        } else {
                            transposedMatrix[((columns-1)-j)][((rows-1)-i)] = matrix[i][j];
                        }
                    }
                }
                System.out.println("The result is: ");
                printMatrix(transposedMatrix, rows, columns);
                break;
            case 3:
                // Vertical line
                transposedMatrix = new double[rows][columns];

                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {
                        transposedMatrix[i][((columns-1)-j)] = matrix[i][j];
                    }
                }
                System.out.println("The result is: ");
                printMatrix(transposedMatrix, rows, columns);
                break;
            case 4:
                // Horizontal line
                transposedMatrix = new double[rows][columns];

                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {
                        //transposedMatrix[i][((columns-1)-j)] = matrix[i][j];
                        transposedMatrix[((rows-1)-i)][j] = matrix[i][j];
                    }
                }
                System.out.println("The result is: ");
                printMatrix(transposedMatrix, rows, columns);
                break;
        }
    }

    // Not done
    /*private static void inverseMatrix(double[][] matrix, int rows, int columns) {
        double[][] invertedMatrix;
    }*/

    /*private static double calculateDeterminant(double[][] matrix, int rows, int columns) {
        //double result;
        *//*if (rows == 2 && columns == 2) {
            return (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]);
        } else {
            return 0;
        }*//*
    }*/

    private static void getCofactor(double[][] mat,
                                    double[][] temp, int p, int q, int n)
    {
        int i = 0, j = 0;

        // Looping for each element of
        // the matrix
        for (int row = 0; row < n; row++)
        {
            for (int col = 0; col < n; col++)
            {

                // Copying into temporary matrix
                // only those element which are
                // not in given row and column
                if (row != p && col != q)
                {
                    temp[i][j++] = /*(int)*/ mat[row][col];

                    // Row is filled, so increase
                    // row index and reset col
                    //index
                    if (j == n - 1)
                    {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    /* Recursive function for finding determinant
    of matrix. n is current dimension of mat[][]. */
    static double determinantOfMatrix(double[][] mat, int n)
    {
        double D = 0; // Initialize result

        // Base case : if matrix contains single
        // element
        if (n == 1)
            return mat[0][0];

        // To store cofactors
        double[][] temp = new double[rowsA][columnsA];

        // To store sign multiplier
        int sign = 1;

        // Iterate for each element of first row
        for (int f = 0; f < n; f++)
        {
            // Getting Cofactor of mat[0][f]
            getCofactor(mat, temp, 0, f, n);
            D += sign * mat[0][f]
                    * determinantOfMatrix(temp, n - 1);

            // terms are to be added with
            // alternate sign
            sign = -sign;
        }

        return D;
    }

}
