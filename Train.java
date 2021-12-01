public class Train {
	private int numberOfEngines = 3;
	private int trainId;
	private boolean isIdle = false;
	private boolean isFull = false;
	private double CurrentTrainFillLevel = 0.0;
	private double arrivalTime;
	private double departureTime;
	private int demurrageCostTotal;
	private int demurrage = 5000;
	private int trainCapacity;

	public Train(int numberOfEngines_, boolean isIdle_, boolean isFull_, double fillLevel_, double arrivalTime_,
			double departureTime_) {
		numberOfEngines = numberOfEngines_;
		isIdle = isIdle_;
		isFull = isFull_;
		CurrentTrainFillLevel = fillLevel_;
		arrivalTime = arrivalTime_;
		departureTime = departureTime_;
		if (numberOfEngines == 3) {
			trainCapacity = 1;
		} else {
			trainCapacity = 2;
		}

	}

	public Train(int trainId, int numberOfEngines, boolean isFull, double CurrentTrainFillLevel, double arrivalTime) {
		this.trainId = trainId;
		this.numberOfEngines = numberOfEngines;
		this.isFull = isFull;
		this.CurrentTrainFillLevel = CurrentTrainFillLevel;
		this.arrivalTime = arrivalTime;
		this.departureTime = 
	}

	/**
	 * @param demurrageCostTotal the demurrageCostTotal to set
	 */
	public void setDemurrageCostTotal(int demurrageCostTotal) {
		this.demurrageCostTotal = demurrageCostTotal;
	}

	/**
	 * @param isIdle the isIdle to set
	 */
	public void setIdle(boolean isIdle) {
		this.isIdle = isIdle;
	}

	public void setTrainCapacity(int trainCapacity) {
		this.trainCapacity = trainCapacity;
	}

	public void setIsFull(boolean isFull_) {
		this.isFull = isFull_;
	}

	public void setNumberOfEngines(int numberOfEngines) {
		this.numberOfEngines = numberOfEngines;
	}

	public void setFillLevel(double fillLevel) {
		this.CurrentTrainFillLevel = fillLevel;
	}

	public void setArrivalTime(double arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public void setDepartureTime(double departureTime) {
		this.departureTime = departureTime;
	}

	public void setDemurrageCost(int demurrageCostTotal) {
		this.demurrageCostTotal = demurrageCostTotal;
	}

	public boolean getIsIdle() {
		return isIdle;
	}

	public boolean getIsFull() {
		return isFull;
	}

	public double getFillLevel() {
		return CurrentTrainFillLevel;
	}

	public int getNumberOfEngines() {
		return numberOfEngines;
	}

	public double getArrivalTime() {
		return arrivalTime;
	}

	public double getDepartureTime() {
		return departureTime;
	}

	public int getDemurrageCost() {
		return demurrageCostTotal;
	}

	/**
	 * @return the currentTrainFillLevel
	 */
	public double getCurrentTrainFillLevel() {
		return CurrentTrainFillLevel;
	}

	/**
	 * @param currentTrainFillLevel the currentTrainFillLevel to set
	 */
	public void setCurrentTrainFillLevel(double currentTrainFillLevel) {
		CurrentTrainFillLevel = currentTrainFillLevel;
	}

	/**
	 * @return the demurrageCostTotal
	 */
	public int getDemurrageCostTotal() {
		return demurrageCostTotal;
	}

	public int getTrainCapacity() {
		return trainCapacity;
	}

	public int calculateTrainFees() {

		this.demurrageCostTotal = numberOfEngines * demurrage * (int) (Math.abs(departureTime - arrivalTime));
		return this.demurrageCostTotal;

	}

	public void printInfo() {
		// TO-DO reflect more information once it is found such as arrival time and cost
		System.out.printf("%s  ", String.valueOf(this.trainId));
		System.out.printf("%s  ", String.valueOf(this.numberOfEngines));
		System.out.printf("%s  ", String.valueOf(this.isFull));
		System.out.printf("%s  ", String.valueOf(this.CurrentTrainFillLevel));
		System.out.printf("%s  ", String.valueOf(this.arrivalTime));
		System.out.printf("%s  ", String.valueOf(this.calculateTrainFees()));
		System.out.printf("%s  ", String.valueOf(this.departureTime));
		System.out.println();
	}

}