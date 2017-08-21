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
public class Item {
    private int denoms;
    private int numOfPrices;
    private int[][] prices;
    private int[] conversions;

    public int getDenoms() {
        return denoms;
    }

    public void setDenoms(int denoms) {
        this.denoms = denoms;
    }

    public int getNumOfPrices() {
        return numOfPrices;
    }

    public void setNumOfPrices(int numOfPrices) {
        this.numOfPrices = numOfPrices;
    }

    public int[][] getPrices() {
        return prices;
    }

    public void setPrices(int[][] prices) {
        this.prices = prices;
    }

    public int[] getConversions() {
        return conversions;
    }

    public void setConversions(int[] conversions) {
        this.conversions = conversions;
    }
}
