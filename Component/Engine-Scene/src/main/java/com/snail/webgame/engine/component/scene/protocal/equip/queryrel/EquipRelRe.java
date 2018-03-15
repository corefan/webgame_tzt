package com.snail.webgame.engine.component.scene.protocal.equip.queryrel;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class EquipRelRe  extends MessageBody {

	protected long id;
	protected long equipId;
	protected int partNO;
	protected int equipNo;
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("id", 0);
		ps.add("equipId", 0);
		ps.add("partNO", 0);
		ps.add("equipNo", 0);
	}

	public long getEquipId() {
		return equipId;
	}

	public void setEquipId(long equipId) {
		this.equipId = equipId;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPartNO() {
		return partNO;
	}

	public void setPartNO(int partNO) {
		this.partNO = partNO;
	}

	public int getEquipNo() {
		return equipNo;
	}

	public void setEquipNo(int equipNo) {
		this.equipNo = equipNo;
	}

}
