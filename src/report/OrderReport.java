package report;

import java.io.Serializable;

/**
 * This class includes attributes that are needed for orders' report at the end of quarterly
 */

public class OrderReport extends Report{
		
	private long totalOrdersAmount;
	private long bouquetAmount;
	private long brideBouquetAmount;
	private long flowerPotAmount;
	private long flowerAmount;		//Maybe need to be removed
	private long plantAmount;
	
	public OrderReport(Quarterly quarterly, String year, long storeID, long totalOrdersAmount, long bouquetAmount,
			long brideBouquetAmount, long flowerPotAmount, long flowerAmount, long plantAmount) throws ReportException
	{
		super(quarterly, year, storeID);
		this.totalOrdersAmount = totalOrdersAmount;
		this.bouquetAmount = bouquetAmount;
		this.brideBouquetAmount = brideBouquetAmount;
		this.flowerPotAmount = flowerPotAmount;
		this.flowerAmount = flowerAmount;
		this.plantAmount = plantAmount;
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
