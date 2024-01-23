import javax.swing.JOptionPane;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

    try{
        SalesCalculator appobj = new SalesCalculator();
        appobj.start();}

    catch(Exception e){

           JOptionPane.showMessageDialog(null, e.getMessage());

            }

    }
}