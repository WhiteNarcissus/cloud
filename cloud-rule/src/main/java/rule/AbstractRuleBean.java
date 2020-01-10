package rule;

public abstract class AbstractRuleBean {

	protected Object result;
	protected Integer bigoRow;	//规则踩中的行序号
	protected String version;	//当前的规则版本

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Integer getBigoRow() {
		return bigoRow;
	}

	public void setBigoRow(int bigoRow) {
		this.bigoRow = bigoRow;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
