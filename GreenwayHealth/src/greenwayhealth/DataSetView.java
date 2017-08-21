/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package greenwayhealth;

/**
 *
 * @author James Ballew
 */
public class DataSetView {

    public void printDataSet(int dataSet, int[] output) {
        for (int i = 0; i < dataSet; i++) {
            System.out.println("Data Set " + (i+1) + ":");
            System.out.println(output[i]);
        }
    }
}
