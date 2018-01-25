package report;

import java.io.Serializable;

/**
 * This class includes attributes that are needed for income report at the end
 * of quarterly
 */
public class IncomeReport extends Report {

	private float incomeAmount;

	public IncomeReport(Quarterly quarterly, String year, long storeID, float incomeAmount) throws ReportException 
	{
		super(quarterly, year, storeID);
		this.incomeAmount = incomeAmount;
		
	}
	
	public float getIncomeAmount() {
		return incomeAmount;
	}

	public void setIncomeAmount(float incomeAmount) throws ReportException{
		if(incomeAmount > 0)
			this.incomeAmount = incomeAmount;
		else
			throw new ReportException("Entered income's amount is invalid!");
	}

}
