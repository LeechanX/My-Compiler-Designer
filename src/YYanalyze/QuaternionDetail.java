package YYanalyze;

public class QuaternionDetail {
	private Quaternion quaternion;
	private int addr;
	public Quaternion getQuaternion() {
		return quaternion;
	}
	public void setQuaternion(Quaternion quaternion) {
		this.quaternion = quaternion;
	}
	public int getAddr() {
		return addr;
	}
	public void setAddr(int addr) {
		this.addr = addr;
	}
	public QuaternionDetail(Quaternion quaternion, int addr) {
		this.quaternion = quaternion;
		this.addr = addr;
	}
}
