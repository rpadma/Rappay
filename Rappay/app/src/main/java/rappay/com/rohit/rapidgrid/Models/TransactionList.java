package rappay.com.rohit.rapidgrid.Models;

/**
 * Created by Rohit on 3/24/2016.
 */
public class TransactionList {


    private String TransactionDate;
    private String TransactionAmount;
    private String ClosingBalance;
    private String CreditOrDebit;
    private String TransactionType;


        public String getTransactionDate() {
            return TransactionDate;
        }

        public void setTransactionDate(String TransactionDate) {
            this.TransactionDate = TransactionDate;
        }



    public String getTransactionAmount() {
        return TransactionAmount;
    }

    public void setTransactionAmount(String TransactionAmount) {
        this.TransactionAmount = TransactionAmount;
    }



    public String getClosingBalance() {
        return ClosingBalance;
    }

    public void setClosingBalance(String ClosingBalance) {
        this.ClosingBalance = ClosingBalance;
    }


    public String getCreditOrDebit() {
        return CreditOrDebit;
    }

    public void setCreditOrDebit(String CreditOrDebit) {
        this.CreditOrDebit = CreditOrDebit;
    }



    public String getTransactionType() {
        return TransactionType;
    }

    public void setTransactionType(String TransactionType) {
        this.TransactionType = TransactionType;
    }




}
