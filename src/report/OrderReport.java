package report;

import java.io.Serializable;

import report.IncomeReport.Quarterly;
import report.IncomeReport.ReportException;

/**
 * This class includes attributes that are needed for orders' report at the end of quarterly
 */

public class OrderReport implements Serializable{
	

	public class ReportException extends Exception {

		public ReportException(String message) {
			super(message);
		}
	}
	
	public enum Quarterly {FIRST/* Months: 1-3 */, SECOND/* Months: 4-6 */, THIRD/* Months: 7-9 */, FOURTH/* Months: 10-12 */};
	
	private Quarterly quarterly;
	private String year;
	private long storeID;
	private long totalOrdersAmount;
	private long bouquetAmount;
	private long brideBouquetAmount;
	private long flowerPotAmount;
	private long flowerAmount;
	private long plantAmount;
	
	public OrderReport(Quarterly quarterly, String year, long storeID, long totalOrdersAmount, long bouquetAmount,
			long brideBouquetAmount, long flowerPotAmount, long flowerAmount, long plantAmount) {

		this.quarterly = quarterly;
		this.year = year;
		this.storeID = storeID;
		this.totalOrdersAmount = totalOrdersAmount;
		this.bouquetAmount = bouquetAmount;
		this.brideBouquetAmount = brideBouquetAmount;
		this.flowerPotAmount = flowerPotAmount;
		this.flowerAmount = flowerAmount;
		this.plantAmount = plantAmount;
	}
	
	public Quarterly getQuarterly() {
		return quarterly;
	}
	
	public void setQuarterly(Quarterly quarterly) {
		this.quarterly = quarterly;
	}
	
	public String getYear() {
		return year;
	}
	
	public void setYear(String year) throws ReportException{
		if (Integer.parseInt(year) > 0)
			this.year = year;
		else
			throw new ReportException("Entered year is invalid!");
	}
	
	public long getStoreID() {
		return storeID;
	}
	
	public void setStoreID(long storeID) throws ReportException{
		if(storeID > 0)
			this.storeID = storeID;
		else
			throw new ReportException("Entered store's ID is invalid!");
	}
	
	public long getTotalOrdersAmount() {
		return totalOrdersAmount;
	}
	
	public void setTotalOrdersAmount(long totalOrdersAmount) throws ReportException{
		if(totalOrdersAmount > 0)
			this.totalOrdersAmount = totalOrdersAmount;
		else
			throw new ReportException("Entered total amount is invalid!");
	}
	
	public long getBouquetAmount() {
		return bouquetAmount;
	}
	
	public void setBouquetAmount(long bouquetAmount) throws ReportException{
		if(bouquetAmount > 0)
			this.bouquetAmount = bouquetAmount;
		else
			throw new ReportException("Entered bouquets' amount is invalid!");
	}
	
	public long getBrideBouquetAmount() {
		return brideBouquetAmount;
	}
	
	public void setBrideBouquetAmount(long brideBouquetAmount) throws ReportException{
		if(brideBouquetAmount > 0)
			this.brideBouquetAmount = brideBouquetAmount;
		else
			throw new ReportException("Entered bride bouquets' amount is invalid!");
	}
	
	public long getFlowerPotAmount() {
		return flowerPotAmount;
	}
	
	public void setFlowerPotAmount(long flowerPotAmount) throws ReportException{
		if(flowerPotAmount > 0)
			this.flowerPotAmount = flowerPotAmount;
		else
			throw new ReportException("Entered flower pots' amount is invalid!");
	}
	
	public long getFlowerAmount() {
		return flowerAmount;
	}
	
	public void setFlowerAmount(long flowerAmount) throws ReportException{
		if(flowerAmount > 0)
			this.flowerAmount = flowerAmount;
		else
			throw new ReportException("Entered flowers' amount is invalid!");
	}
	
	public long getPlantAmount() {
		return plantAmount;
	}
	
	public void setPlantAmount(long plantAmount) throws ReportException{
		if(plantAmount > 0)
			this.plantAmount = plantAmount;
		else 
			throw new ReportException("Entered plants' amount is invalid!");
	}
	
	
}
