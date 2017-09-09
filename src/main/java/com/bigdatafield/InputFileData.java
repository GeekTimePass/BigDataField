/**
 * 
 */
package com.bigdatafield;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author mukes
 *
 */
public class InputFileData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7832601062779713992L;
	private BigInteger count;
	private String runId;
	private Date timeStamp;
	private String status;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + count.intValue();
		result = prime * result + ((runId == null) ? 0 : runId.hashCode());
		result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InputFileData other = (InputFileData) obj;
		if (count != other.count)
			return false;
		if (runId == null) {
			if (other.runId != null)
				return false;
		} else if (!runId.equals(other.runId))
			return false;
		if (timeStamp == null) {
			if (other.timeStamp != null)
				return false;
		} else if (!timeStamp.equals(other.timeStamp))
			return false;
		return true;
	}
	public BigInteger getCount() {
		return count;
	}
	public void setCount(BigInteger count) {
		this.count = count;
	}
	public String getRunId() {
		return runId;
	}
	public void setRunId(String id) {
		this.runId = id;
	}
	

}
