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
public class GreenwayHealth {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Create item
        Item item = new Item();

        //Create view
        DataSetView view = new DataSetView();

        //Create controller and set it the item and view
        ItemController controller = new ItemController(item, view);
        // TODO code application logic here
    }

}
