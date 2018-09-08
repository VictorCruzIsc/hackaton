package models.responses;

import exceptions.ApiException;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

@Data
public class AccountStatus {

    private final String cellPhoneNumberStored;
    private final String officialId;
    private final String lastName;
    private final boolean isCellPhoneNumberVerified;
    private final String clientId;
    private final String signedContract;
    private final BigDecimal dailyRemaining;
    private final String originOfFunds;
    private final BigDecimal monthlyLimit;
    private final int verificationLevel;
    private final String proofOfResidency;
    private final String referralCode;
    private final String emailStored;
    private final BigDecimal monthlyRemaining;
    private final BigDecimal cashDeposit;
    private final String firstName;
    private final BigDecimal dailyLimit;
    private final boolean isEmailVerified;
    private final boolean isUserActive;

    public AccountStatus(JSONObject jsonObject) throws ApiException {

        try {
            this.cellPhoneNumberStored = jsonObject.getString("cellphone_number_stored");
            this.officialId = jsonObject.getString("official_id");
            this.lastName = jsonObject.getString("last_name");
            this.isCellPhoneNumberVerified = jsonObject.getString("cellphone_number").equals("verified");
            this.clientId = jsonObject.getString("client_id");
            this.signedContract = jsonObject.getString("signed_contract");
            this.dailyRemaining = new BigDecimal(jsonObject.getString("daily_remaining"));
            this.originOfFunds = jsonObject.getString("origin_of_funds");
            this.monthlyLimit = new BigDecimal(jsonObject.getString("monthly_limit"));
            this.verificationLevel = jsonObject.getInt("verification_level");
            this.proofOfResidency = jsonObject.getString("proof_of_residency");
            this.referralCode = jsonObject.getString("referral_code");
            this.emailStored = jsonObject.getString("email_stored");
            this.monthlyRemaining = new BigDecimal(jsonObject.getString("monthly_remaining"));
            this.cashDeposit = new BigDecimal(jsonObject.getString("cash_deposit_allowance"));
            this.firstName = jsonObject.getString("first_name");
            this.dailyLimit = new BigDecimal(jsonObject.getString("daily_limit"));
            this.isEmailVerified = jsonObject.getString("email").equals("verified");
            this.isUserActive = jsonObject.getString("status").equals("active");
        } catch (JSONException e) {
            throw new ApiException(e);
        }
    }
}
