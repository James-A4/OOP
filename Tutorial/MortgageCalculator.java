import java.text.NumberFormat;
import java.util.Scanner;

public class MortgageCalculator {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Principal: ");
        int Principal = scanner.nextInt();

        System.out.print("Annual Interest Rate: ");
        float AnnualInterestRate = scanner.nextFloat();

        System.out.print("Period (Years): ");
        int Years = scanner.nextInt();

        float MonthlyInterestRate = AnnualInterestRate / 12 / 100;
        int NumberOfPayments = Years * 12;

        double Mortgage = Principal * (MonthlyInterestRate * Math.pow(1 + MonthlyInterestRate, NumberOfPayments)) / (Math.pow(1 + MonthlyInterestRate, NumberOfPayments) - 1);

        String mortgageFormatted = NumberFormat.getCurrencyInstance().format(Mortgage);

        System.out.println("Mortgage: " + mortgageFormatted);
    }
}