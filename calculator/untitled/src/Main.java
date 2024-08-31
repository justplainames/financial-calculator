import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        System.out.println("Test");
        calculator(
                28,
                45,
                1500f,
                249700f,
                961f,
                2.6f,
                COMPOUND_TYPE.MONTHLY,
                0f,
                5f
                );

    }

    public static void calculator(
            float ageStart,
            float ageEnd,
            float cashContribution,
            float loan,
            float cpfContribution,
            float cpfAnnualInterestRate,
            COMPOUND_TYPE compoundType,
            float startingCash,
            float savingsInterestRate
            ){
        float savePercentage = 0.0f;
        float payPercentage = 1.0f;

        for(int i=0; i<11; i++){
            System.out.println("\n\n-------------------------------------------------------------------------");
            float sp = savePercentage * cashContribution;
            float pp = cashContribution * payPercentage;
            HashMap<String, Float> cpfResult = Calculator.cpfLoanRepaymentPeriod(
                    loan,
                    cpfContribution,
                    pp,
                    cpfAnnualInterestRate,
                    compoundType
            );

            if((cpfResult.get("number_of_months") > (ageEnd - ageStart)*12) ){
                return;
            }
            HashMap<String, Float> savingsResult = Calculator.savingsCalculator(
                 startingCash,
                 sp,
                 savingsInterestRate,
                 cpfResult.get("number_of_months"),
                17f,
                cashContribution
            );

            System.out.printf(
                    "Saving: %f\nPaying: %f%n" , sp, pp
            );
            System.out.printf(
                    "It will take %f months\nYou will pay a total of $%f\nwith an interest incurred of $%f",
                    cpfResult.get("number_of_months"),
                    cpfResult.get("total_amount_paid"),
                    cpfResult.get("total_interest_incurred")
            );
            System.out.printf(
                    "\nYou would have saved a total of $%f\ndepositing $%f, earning $%f in interests",
                    savingsResult.get("total_savings"),
                    savingsResult.get("total_deposited"),
                    savingsResult.get("interest_earned")
            );
            System.out.println("\n\nTotal gain = " + (savingsResult.get("interest_earned")-cpfResult.get("total_interest_incurred")));
            savePercentage += 0.1;
            payPercentage -= 0.1;

            System.out.printf(
                    "|\n|\n|\nIf You would have continued saving a total of $%f\ndepositing $%f, earning $%f in interests",
                    savingsResult.get("total_savings_new"),
                    savingsResult.get("total_deposited_new"),
                    savingsResult.get("interest_earned_new")
            );

            System.out.printf(
                    "|\n|\n|\nIf You would have continued, you have saved a total of $%f\ndepositing $%f, earning $%f in interests",
                    savingsResult.get("total_savings_new"),
                    savingsResult.get("total_deposited_new"),
                    savingsResult.get("interest_earned_new")
            );
            System.out.printf(
                    "|\n|\n|\nIf You would have continued and used your full cash, \nyou have saved a total of $%f\ndepositing $%f, earning $%f in interests",
                    savingsResult.get("total_savings_new"),
                    savingsResult.get("total_deposited_new"),
                    savingsResult.get("interest_earned_new")
            );
        }
    }

}