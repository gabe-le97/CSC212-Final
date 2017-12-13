/**
 * 
 * file: SortableDataOrganizer.java
 * @author Gabe Le
 * 
 * This class allows us to sort the collection of items in
 *  ascending order without modifying any of Nickie's other classes
 *  
 */

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;


public class SortableDataOrganizer 
					extends DataOrganizer {
	
// only one instance variable needed which is our button
	Abutton sortButton;
//--------------------------------------------------	

/*
 ***************************************************
 *
 * Main Constructor does nothing
 * 
 ***************************************************
 */
	public SortableDataOrganizer() {
	}
//--------------------------------------------------		

	
/*
 ***************************************************
 *
 * Constructor that creates the start button at a specific location
 *  & determines the location of where the sorted collection is drawn
 * 
 ***************************************************
 */
	public SortableDataOrganizer(Applet someApplet) {	
		super(someApplet);
		
		x -= 1.5*Abutton.BUTTON_WIDTH;								// declaring variables that determine
		y -= 1*Abutton.BUTTON_HEIGHT;								//  where the sort button is painted

		y += 1.5*Abutton.BUTTON_HEIGHT;
		sortButton = new Abutton("Sort", Color.yellow , x , y);		// sort button is instantiated 

		x += 1.5*Abutton.BUTTON_WIDTH;
		y += 1*Abutton.BUTTON_HEIGHT;
	}
//--------------------------------------------------	

	
/*
 ***************************************************
 *  
 *  Handles the operation of the mergeSort
 * 
 ***************************************************
 */
	public void sort() {	
		collection  = mergeSort(collection);						// call mergeSort on the collection
		collection.reset(null);										// un-highlight the object after merging
	}
//--------------------------------------------------	
	
	
/*
 ***************************************************
 * 
 * Recursively splits the collection in half until they are
 *  single sorted lists, then calls mergeCollection to put it back into
 *   the collection.
 * 
 ***************************************************
 */
	public DataCollection mergeSort(DataCollection collection) {				
		collection.reset();													// resets to beginning of the list
		int collectionSize = getSize(collection);
		int mid = getSize(collection) / 2;
		
		if (collectionSize <= 1)	{										// case if there's only one item in the collection
			return collection;
		}	
		else {
			DataCollection head1 = new ArrayDataCollection();				// we are making two heads for the array being split
			DataCollection head2 = new ArrayDataCollection();

			for (int i = 0; i < mid; i++){ 									// Make first half of the array
				head1.add(collection.next());
			}
			
			for(int i = mid; i < collectionSize; i++) {						// Make other half of the array
				head2.add(collection.next());
			}
			
			head1 = mergeSort(head1);										// split the first array again 
			head2 = mergeSort(head2);										// split other array again

			return mergeCollection(head1, head2);							// begin to combine and sort the arrays
		}
	}
//--------------------------------------------------

	
/*
 ***************************************************
 * 
 * Take in two sorted collections and combine them into one by comparing
 *  each element one by one
 * 
 ***************************************************
 */
	private DataCollection mergeCollection(DataCollection head1, DataCollection head2) {
		head1.reset();													// reset so we can be at the beginning of the list again			
		head2.reset();
		
		DataCollection temp = new ArrayDataCollection(x,y);
		Item item1 = head1.next(); 
		Item item2 = head2.next();
		
		while(item1 != null && item2 != null) {
			if(item1.getValue() >= item2.getValue()) {					// compares the values in both arrays, picks the smaller one
				temp.add(item2);										//  and puts it in the new collection
				item2 = head2.next();
			}
			else if(item2.getValue() > item1.getValue()){
				temp.add(item1);
				item1 = head1.next();
			}
		}
		
		if(item2 == null){												// check if any half of the collection is empty 
			while(item1 != null){										// collection 1 has items still in it but collection 2 doesn't
				temp.add(item1);										//  so add remaining items
				item1 = head1.next();
			}
		}
		else {
			while(item2 != null){										// vice versa
				temp.add(item2);
				item2 = head2.next();
			}
		}
		
		return temp;					
	}
//--------------------------------------------------
	
	
/*
 ****************************************************
 *  
 * Allows sort to take place only if the button is pressed
 * 
 ****************************************************
 */
	public void mouseClicked(MouseEvent event){
		super.mouseClicked(event);
		if(sortButton.isInside(lastX, lastY)){
			sort();
		}
	}
//--------------------------------------------------	
	
	
/*
 ****************************************************
 * 
 * Returns the size of the collection to be referenced in the sort functions
 * 
 ****************************************************
 */
	private int getSize(DataCollection collection){	
		int counter = 0;
		while(collection.hasNext()) {						// walks through array incrementing a counter
			collection.next();								//  to keep track of how many elements there are
			counter++;	
		}
		collection.reset();									// selected item is now at the beginning again but counter stays as is
		return counter;
	}
//--------------------------------------------------	
	
	
/*
 ****************************************************
 * 
 * Allows the "animation" of the sort button being pressed
 *  by filling it with a darker box
 * 
 ****************************************************
 */
	public void flipWhenInside(){
		super.flipWhenInside();
		if(sortButton.isInside(lastX, lastY)){
			sortButton.flip();
			theApplet.repaint();
		}
	}
//--------------------------------------------------	

	
/**
 ****************************************************
 *
 * Paint the sortButton & call the paint method
 *  of the parent class to paint other buttons
 *  
 ****************************************************  
 */
	public void paint(Graphics pane) {
		super.paint(pane);
		if(sortButton != null)
			sortButton.paint(pane);
	}
//--------------------------------------------------	
	

}
// end of SortableDataCollection.java