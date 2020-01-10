package com.jdry.pms.assignWork.pojo;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
/**
 * 描述：工单何处理人关系
 * @author hezuping
 * 时间：2016年10月8日11:55:55
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "t_r_order_person")
public class DispatchPerson implements Serializable 
{

	private String id;
	private String rpt_id;
	private String handle_id;
	private String handle_name;
	private String event_state;
	private String remark;
	
	  @Id
	  @Column(name = "id")
	  @GeneratedValue(generator = "system-uuid")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	  @Column(name = "rpt_id")
	public String getRpt_id() {
		return rpt_id;
	}
	  @Column(name = "handle_id")
	public String getHandle_id() {
		return handle_id;
	}
	  @Column(name = "handle_name") 
	public String getHandle_name() {
		return handle_name;
	}
	  @Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setRpt_id(String rpt_id) {
		this.rpt_id = rpt_id;
	}
	public void setHandle_id(String handle_id) {
		this.handle_id = handle_id;
	}
	public void setHandle_name(String handle_name) {
		this.handle_name = handle_name;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "event_state")
	public String getEvent_state() {
		return event_state;
	}
	public void setEvent_state(String event_state) {
		this.event_state = event_state;
	}
}
