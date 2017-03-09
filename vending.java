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
    List<String> state_seq=new ArrayList();
    String[] itemset=new String[4];
    int[] cost=new int[4];
    String item;
    int pos;
    int to_pay=0; 
    Scanner in=new Scanner(System.in);
    StateObj stateobj=new StateObj();
    String state_to_be;
    vending(String state){
        stateobj.state=state;
        state_seq.add(stateobj.state);
        init();
    }
    void init(){
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
        state_seq.add("Initiation");
        if(input.equals("S")){
                if(stateobj.state.equals("Terminated")){
                    System.out.println("Initiating.....\nPlease wait for a few seconds");
                    state_to_be="Start";
                    start();
                }                        
        }
        else if(input.equals("q")){
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
        else if(input.equals("B")){
            if(stateobj.state.equals("Dispose")){
                state_to_be="Start";
                state_seq.add("Start");
            }
                
        }
        
        stateobj.change(state_to_be);
    }
    void start(){
        state_seq.add("Start");
        int i;
        menu();
        item=in.next();
        for(i=0;i<itemset.length;i++){
            if(item.equals(itemset[i])){
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
    void menu(){
        System.out.println("Select an item from the following");
        System.out.println("Latte            INR 20      L");
        System.out.println("Tea              INR 10      T");
        System.out.println("Cappuchino       INR 30      C");
        System.out.println("Masala Chai      INR 20      M");      
    }
    void selected(){
        
        if(stateobj.state.equals("Selected")){
            state_seq.add("Selected");
            System.out.println("You selected "+item);
            state_to_be="Incomplete";
            stateobj.change(state_to_be);
            incomplete();
        }
        else{
            state_to_be="Invalid";
            stateobj.change(state_to_be);
            invalid();
        }
        
    }
    void invalid(){
        state_seq.add("Invalid");
        System.out.println("You did not select anything valid");
        state_to_be="Start";
        stateobj.change(state_to_be);
        start();
    }
    void incomplete(){
        int temp;
        if(stateobj.state.equals("Incomplete"))
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
                    state_to_be="Incorrect";
                    incorrect();
                }
                stateobj.change(state_to_be);
            }
            if(to_pay==0){
                state_to_be="Complete";
            }
            stateobj.change(state_to_be);
            complete();
        }
        
    }
    boolean isAllowed(int currency){
        if(currency<=to_pay)
            return true;
    return false;
    }
    void incorrect(){
        state_seq.add("Incorrect");
        System.out.println("Invalid denomination\nPlease enter coins of 1,5 and 10 only");
    }
    void complete(){
        state_seq.add("Complete");
        state_to_be="Dispose";
        stateobj.change(state_to_be);
        dispose();
    }
    void dispose(){
        state_seq.add("Dispose");
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
        System.out.print("Press B to go back to the menu\nPress q to terminate the machine\n");
        String input=in.next();
        if(input.equals("B")){
            state_to_be="Start";
            stateobj.change(state_to_be);
            start();
        }
        else if(input.equals("q")){
            state_to_be="Terminating";
            stateobj.change(state_to_be);
            terminating();
        }
    }
    void terminating(){
        
        System.out.println("Terminating...");        
        state_seq.add("Terminating");
        state_to_be="Terminated";
        stateobj.change(state_to_be);
        terminated();
        
    }
    void terminated(){
        state_seq.add("Terminated");
        System.out.println("The sequence of states is "+state_seq);
        stateobj.state="Terminated";
        System.out.println("In termiated mode\nPress S to start the machine");
    }
    public static void main(String args[]){
        vending v=new vending("Terminated");
        String input;
        Scanner in=new Scanner(System.in);
        System.out.println("Press S to start the machine");
        System.out.println("Press q to terminate the machine");
        while(true){
            System.out.println("Press p to shut down the machine");
            input=in.next();
            if(input.equals("p"))
                System.exit(0);
            else
                v.initiating(input);
        }
    }
}
