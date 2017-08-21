/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package greenwayhealth;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author James Ballew
 */
public class ItemController {

    private final Item item;
    private final DataSetView view;
    private int dataSets;
    private int[] output;

    public ItemController(Item item, DataSetView view) {
        this.item = item;
        this.view = view;
        getUserInput();
        updateView();
    }

    private void getUserInput() {
        Scanner in = new Scanner(System.in);
        try {
            //read in the positive number of datasets
            dataSets = in.nextInt();
            if (dataSets < 1) {
                throw new InvalidInput("Atleast 1 dataset must be entered.");
            }
            output = new int[dataSets];
            //consume \n
            in.nextLine();
            //begin getting user input for each item
            for (int i = 0; i < dataSets; i++) {
                getDenomsAndPrices(in);
                getConversionFactors(in);
                getItemsPrices(in);
                getPriceDifference(i);
                System.out.println("Data gathered for item: " + (i + 1));
            }
        } catch (NumberFormatException | InputMismatchException | InvalidInput exception) {
            System.out.println(exception);
        }
    }

    //get valid user input for line 2, # of denominations and # of prices
    private void getDenomsAndPrices(Scanner in) throws InvalidInput {
        //scan in nextLine so that it's size can be validated.
        String input = in.nextLine();
        String[] tempInput = input.split(" ");
        if (tempInput.length == 2) {
            //get # of denoms input and set # of denoms for item
            int denoms = Integer.valueOf(tempInput[0]);
            setItemDenoms(denoms);
            //get # of prices input and set # of prices for item
            int numOfPrices = Integer.valueOf(tempInput[1]);
            setItemNumOfPrices(numOfPrices);
            if (getItemDenoms() < 2 || getItemDenoms() > 7 || getItemNumOfPrices() < 2 || getItemNumOfPrices() > 10) {
                throw new InvalidInput("This line accepts  2 <= D <=7 and 2 <= N <= 10");
            }
        } else {
            throw new InvalidInput("This line accepts 2 integers to denote the number of denominations"
                    + " and the number of different prices. Please validate the dataset.");
        }
    }

    //get valid user input for conversion factors
    private void getConversionFactors(Scanner in) throws InvalidInput {
        String error = "This line accepts " + (getItemDenoms() - 1) + " positive integer(s) that represents the conversions between denominations."
                + " Please validate the dataset.";
        int[] conversions = new int[getItemDenoms()];
        String input = in.nextLine();
        String[] tempInput = input.split(" ");
        //make sure the input has a valid length of D-1
        if (tempInput.length == (getItemDenoms() - 1)) {
            //Store the input into conversions array
            for (int j = 0; j < tempInput.length; j++) {
                int value = Integer.valueOf(tempInput[j]);
                //check that the input containts only positive integers
                if (value > 0) {
                    conversions[j] = value;
                } else {
                    throw new InvalidInput(error);
                }
            }
            setItemConversions(conversions);
        } else {
            throw new InvalidInput(error);
        }

    }

    //get valid user input for item's prices
    private void getItemsPrices(Scanner in) throws InvalidInput {
        int[][] prices = new int[getItemNumOfPrices()][getItemDenoms()];
        String error = "This line accepts " + getItemDenoms() + " non-negative integers to denote "
                + "the combination of denominations needed to purchase this item.";
        for (int i = 0; i < getItemNumOfPrices(); i++) {
            String input = in.nextLine();
            String[] tempInput = input.split(" ");
            if (tempInput.length == getItemDenoms()) {
                for (int j = 0; j < tempInput.length; j++) {
                    int subPrice = Integer.valueOf(tempInput[j]);
                    if (subPrice >= 0) {
                        prices[i][j] = subPrice;
                    } else {
                        throw new InvalidInput(error);
                    }
                }
                setItemPrices(prices);
            } else {
                throw new InvalidInput(error);
            }
        }
    }

    /*Because the conversion factors for denominations must be a positive integer we can determine the total price
    in terms of the smallest denomination by converting each denomination to the very last denomination
    for example: given 3 denominations and the conversion 2 5 and the price 1 3 5, we find the total by
    (1 * 2 * 5) + (3 * 5) + 5 */
    private void getPriceDifference(int dataset) {

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int exchangeValue;
        int totalValue = 0;
        int[][] prices = getItemPrices();
        int[] conversions = getItemConversions();

        //iterate over the list of prices
        for (int i = 0; i < getItemNumOfPrices(); i++) {
            for (int j = 0; j < getItemDenoms(); j++) {
                exchangeValue = prices[i][j];
                //now convert denomination at [i][j] by iterating over the conversions starting at j
                for (int q = j; q < conversions.length - 1; q++) {
                    exchangeValue *= conversions[q];
                }
                totalValue += exchangeValue;
                //just add the total denominations required for the last 
                //denomination since it doesn't need to be converted
                if (j == getItemDenoms()) {
                    totalValue += prices[i][j];
                }
            }
            //determine if this prices total meets the requirements to be a min or max value
            if (totalValue < min) {
                min = totalValue;
            } else if (totalValue > max) {
                max = totalValue;
            }

            //set totalValue = 0 for next iteration
            totalValue = 0;
        }
        //determine the difference between the highest and lowest price and add it to the output
        int difference = max - min;
        output[dataset] = difference;
    }

    private void setItemDenoms(int denoms) {
        item.setDenoms(denoms);
    }

    private int getItemDenoms() {
        return item.getDenoms();
    }

    private void setItemNumOfPrices(int numOfPrices) {
        item.setNumOfPrices(numOfPrices);
    }

    private int getItemNumOfPrices() {
        return item.getNumOfPrices();
    }

    private void setItemPrices(int[][] prices) {
        item.setPrices(prices);
    }

    private int[][] getItemPrices() {
        return item.getPrices();
    }

    private void setItemConversions(int[] conversions) {
        item.setConversions(conversions);
    }

    private int[] getItemConversions() {
        return item.getConversions();
    }

    private void updateView() {
        view.printDataSet(dataSets, output);
    }
}

class InvalidInput extends Exception {

    InvalidInput(String s) {
        super(s);
    }
}
