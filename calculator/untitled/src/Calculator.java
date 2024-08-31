import java.util.HashMap;

public class Calculator {
    public static float cpfSavingsCalculator(float principal,
                                          float monthlySavings,
                                          float interestRate,
                                          float numberOfYears) {
        float total = principal;
        for(int i=0; i<numberOfYears; i++){

            float yearlySaved = total;
            for(int j=0; j < 11; j++){
                yearlySaved += monthlySavings;
                yearlySaved += interestRate/12.0f/100.0f;
            }
            total += yearlySaved;
        }

        return total;
    }

    public static HashMap<String, Float> cpfLoanRepaymentPeriod(float principalLoan,
                                                 float monthlyPaymentCpf,
                                                 float monthlyPayment,
                                                 float annualInterestRate,
                                                 COMPOUND_TYPE compoundType ){
        int numberOfMonths = 0;
        float totalPaid = 0;
        float loanLeft = principalLoan;

        switch (compoundType){
            case MONTHLY -> {
                while(loanLeft > 0) {
                    numberOfMonths += 1;
                    float monthlyContribution =monthlyPayment+monthlyPaymentCpf;
                    if(loanLeft<(monthlyPaymentCpf+monthlyPaymentCpf)){
                        totalPaid += loanLeft;
                        break;
                    }
                    totalPaid +=  monthlyContribution;
                    loanLeft -= monthlyContribution;

                    loanLeft *= (annualInterestRate / 12.0f / 100.0f + 1.0f);
                }
            }
            case DAILY -> {}
            case YEARLY -> {
                int yearlyTimer = 12;
                while(loanLeft > 0) {
                    monthlyPayment += 1;
                    yearlyTimer -= 1;
                    loanLeft -= monthlyPayment;
                    if (yearlyTimer == 0){
                        loanLeft *= annualInterestRate/100.0f + 1;
                        yearlyTimer = 12;
                    }

                }
            }
            default -> {
                while(loanLeft > 0) {
                    monthlyPayment += 1;
                    loanLeft -= monthlyPayment;
                }
            }


        }
        HashMap<String, Float> result = new HashMap<>();
        result.put("total_amount_paid" , totalPaid);
        result.put("total_interest_incurred", totalPaid - principalLoan);
        result.put("number_of_months", (float) numberOfMonths);
        return result;
    }


    public static HashMap<String, Float> savingsCalculator(float principal,
                                             float monthlySavings,
                                             float interestRate,
                                             float numberOfMonths,
                                             float maxYears,
                                             float fullMonthlySavings) {
        float total = principal;
        float maxMonths = 12*maxYears-numberOfMonths;
        HashMap<String, Float> result = new HashMap<String, Float>();
        for(int i=1; i<=numberOfMonths; i++) {
            total += monthlySavings;
//            System.out.println(i%12 + ") Total = " + total);
            if (i % 12 == 0) {
//                System.out.println("Adding Interest = " );
                total *= (interestRate / 100.0f + 1);
//                System.out.println("New total = " + total+"\n");
            }
        }


        float remaining = maxMonths % 12;
        maxMonths -= remaining;
        float totalNew = total;
        float totalNewTransfer = total;
        for(int i=1; i<=remaining; i++) {
            totalNew += monthlySavings;
            totalNewTransfer += totalNew;
        }

        totalNew *= (interestRate / 100.0f + 1);
        for(int i=1; i<=maxMonths; i++){
            totalNew += monthlySavings;
            totalNewTransfer += fullMonthlySavings;

            if(i%12 == 0 ){
                totalNew *= (interestRate/100.0f + 1);
                fullMonthlySavings *= (interestRate/100.0f + 1);
            }
        }



        float totalCashDeposited = numberOfMonths*monthlySavings;
        float totalCashDepositedNew = maxYears*12*monthlySavings;
        float totalCashDepositedNewTransfer = totalCashDeposited + (maxYears*12 - numberOfMonths)*fullMonthlySavings;
        result.put("total_savings", total);
        result.put("interest_earned", total - totalCashDeposited);
        result.put("total_deposited", totalCashDeposited);
        result.put("total_savings_new", totalNew);
        result.put("total_deposited_new", totalCashDepositedNew);
        result.put("interest_earned_new", totalNew - totalCashDepositedNew);
        result.put("full_total_savings_new", totalNewTransfer);
        result.put("full_total_deposited_new", totalCashDepositedNewTransfer);
        result.put("full_interest_earned_new", totalNew - totalCashDepositedNewTransfer);
        return result;

    }

}
