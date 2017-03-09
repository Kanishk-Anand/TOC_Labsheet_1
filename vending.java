/*Implementation of a vending machine accepting denominations of 1,5,10 INR only.
Moore implementation
To shut down a machine you need to first terminate it temporarily 
The customer is first shown the menu and is allowed to choose one item at a time
The amount to be paid is calculated and the user is required to then enter the coins (1,5,10)
The item is given
Option of selecting more items or quitting
*/
import java.util.*;
/**
 *
 * @author Vartika
 */
class StateObj{
    String state;
    public void change(String s){
        state=s;
    }
}
public class vending {
    List<String> state_seq=new ArrayList(); // to store the sequence of the states traversed by inputs
    String[] itemset=new String[4];         // to store the items available to choose
    int[] cost=new int[4];                  // cost of every item    
    String item;
    int pos;
    int to_pay=0; 
    Scanner in=new Scanner(System.in);
    StateObj stateobj=new StateObj();       //to store the next state it is going to 
    String state_to_be;
    vending(String state){
        stateobj.state=state;               //Since moore starting from the termination state
        state_seq.add(stateobj.state);      //and state seq contains the first state    
        init();
    }
    void init(){                            //initializing itemset and cost
        itemset[0]="L";
        itemset[1]="T";
        itemset[2]="C";
        itemset[3]="M";
        cost[0]=20;
        cost[1]=10;
        cost[2]=30;
        cost[3]=20;
    }
    void initiating(String input){
        to_pay=0;
        state_seq.add("Initiation");                    //first state (initiation) 
        if(input.equals("S")){                          //Starting the machine
                if(stateobj.state.equals("Terminated")){
                    System.out.println("Initiating.....\nPlease wait for a few seconds");
                    state_to_be="Start";
                    start();
                }                        
        }
        else if(input.equals("q")){                     //terminating machine temporarily
            if(stateobj.state.equals("Terminated")){
                state_to_be="Terminated";
                stateobj.change(state_to_be);
                terminated();
            }
            else{
                state_to_be="Terminating";
                stateobj.change(state_to_be);
                terminating();
                
            }
        }
        else if(input.equals("B")){                     //Checking if customer wants to select some other item
            if(stateobj.state.equals("Dispose")){
                state_to_be="Start";
                state_seq.add("Start");
            }
                
        }
        
        stateobj.change(state_to_be);
    }
    void start(){
        state_seq.add("Start");                         //Start state on selection of a valid item leads to the selected state
        int i;
        menu();
        item=in.next();
        for(i=0;i<itemset.length;i++){
            if(item.equals(itemset[i])){                //Checking for the item selection and its cost
                to_pay=cost[i];
                pos=i;
                break;
            }
        }
        if(to_pay>0)
            state_to_be="Selected";
        stateobj.change(state_to_be);
        selected();
    }
    void menu(){                                                                //Displaying the menu
        System.out.println("Select an item from the following");
        System.out.println("Latte            INR 20      L");
        System.out.println("Tea              INR 10      T");
        System.out.println("Cappuchino       INR 30      C");
        System.out.println("Masala Chai      INR 20      M");      
    }
    void selected(){
                                                                                           
        if(stateobj.state.equals("Selected")){                         //Checking for selection as well as validity of the item selected
            state_seq.add("Selected");                                 //selected state
            System.out.println("You selected "+item);
            state_to_be="Incomplete";
            stateobj.change(state_to_be);
            incomplete();
        }
        else{
            state_to_be="Invalid";                                     //Invalid selection leads to invalid state
            stateobj.change(state_to_be);
            invalid();
        }
        
    }
    void invalid(){
        state_seq.add("Invalid");                                       //Invalid state
        System.out.println("You did not select anything valid");
        state_to_be="Start";                                            //Returning to the start state for customer to select again
        stateobj.change(state_to_be);
        start();
    }
    void incomplete(){      
        int temp;   
        if(stateobj.state.equals("Incomplete"))                         //Incomplete stage when the customer is yet to pay the full amount
        {
            state_seq.add("Incomplete");
            while(to_pay>0){
                System.out.println("You have to pay "+to_pay+" \nEnter your next denomination");
                temp=in.nextInt();
                if((temp==1)||(temp==5)||(temp==10)){
                    if(isAllowed(temp)){
                        state_to_be="Incomplete";
                        state_seq.add("Incomplete");
                        to_pay=to_pay-temp;
                    }                        
                }
                else{
                    state_to_be="Incorrect";                        //Denominations other than 1,5,10 lead to the incorrect state
                    incorrect();
                }
                stateobj.change(state_to_be);
            }
            if(to_pay==0){
                state_to_be="Complete";                             //Full payment leads to the complete state
            }
            stateobj.change(state_to_be);
            complete();
        }
        
    }
    boolean isAllowed(int currency){                                //method to check whether the entered currency is a valid input for payment or not
        if(currency<=to_pay)
            return true;                                            //returns true if valid
    return false;
    }
    void incorrect(){
        state_seq.add("Incorrect");                                 //Incorrect state
        System.out.println("Invalid denomination\nPlease enter coins of 1,5 and 10 only");
    }
    void complete(){
        state_seq.add("Complete");                                  //Complete state when total amount is paid... leads to the disposal of your item
        state_to_be="Dispose";
        stateobj.change(state_to_be);
        dispose();                                                  //Disposing item
    }
    void dispose(){
        state_seq.add("Dispose");                                   //Dispose state where you get your coffee
        System.out.print("Here is your fresh and hot cup of ");
        switch(pos){
            case 0:
                System.out.println("Latte");
                break;
            case 1:
                System.out.println("Tea");
                break;
            case 2:
                System.out.println("Cappuchino");
                break;
            case 3:
                System.out.println("Masala Chai");
                break;
        }
        System.out.print("Press B to go back to the menu\nPress q to terminate the machine\n");            //Go back to the menu or terminate?
        String input=in.next();
        if(input.equals("B")){
            state_to_be="Start";
            stateobj.change(state_to_be);                           //Go back to the menu leads to the start state again and the process repeats
            start();
        }
        else if(input.equals("q")){
            state_to_be="Terminating";
            stateobj.change(state_to_be);                           //Terminating machine
            terminating();
        }
    }
    void terminating(){
        
        System.out.println("Terminating...");        
        state_seq.add("Terminating");                               //Terminating which whill lead to terminated state
        state_to_be="Terminated";
        stateobj.change(state_to_be);
        terminated();
        
    }
    void terminated(){
        state_seq.add("Terminated");
        System.out.println("The sequence of states is "+state_seq);
        stateobj.state="Terminated";                                            //Machine terminated Restart using "S" shut down using "p"
        System.out.println("In termiated mode\nPress S to start the machine");
    }
    //Driver method
    public static void main(String args[]){
        vending v=new vending("Terminated");
        String input;
        Scanner in=new Scanner(System.in);
        System.out.println("Press S to start the machine");
        System.out.println("Press q to terminate the machine");
        while(true){
            System.out.println("Press p to shut down the machine");
            input=in.next();
            if(input.equals("p"))                                   //To check if the input taken is to shut down the machine
                System.exit(0);
            else
                v.initiating(input);
        }
    }
}
