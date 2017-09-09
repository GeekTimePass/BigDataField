package com.bigdatafield;

import java.io.Serializable;

public class OutputFileData implements Serializable{
	
	private static final long serialVersionUID = -8458742390059007789L;
	private String runId;

	public String getRunId() {
		return runId;
	}

	public void setRunId(String id) {
		this.runId = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((runId == null) ? 0 : runId.hashCode());
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
		OutputFileData other = (OutputFileData) obj;
		if (runId == null) {
			if (other.runId != null)
				return false;
		} else if (!runId.equals(other.runId))
			return false;
		return true;
	}
	
	

}
