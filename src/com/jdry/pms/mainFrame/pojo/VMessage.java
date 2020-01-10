package com.jdry.pms.mainFrame.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="v_bdf2_message")
public class VMessage
  implements Serializable
{
  private static final long serialVersionUID = -6276286440146124045L;

  @Id
  @Column(name="ID_", length=60)
  private String id;

  @Column(name="TITLE_", length=60, nullable=false)
  private String title;

  @Column(name="CONTENT_", length=1000, nullable=false)
  private String content;

  @Column(name="SEND_DATE_")
  private Date sendDate;

  @Column(name="SENDER_", length=60, nullable=false)
  private String sender;

  @Column(name="RECEIVER_", length=60, nullable=false)
  private String receiver;

  @Column(name="READ_", nullable=false)
  private boolean read;

  @Column(name="REPLY_")
  private boolean reply;

  @Column(name="TAGS_", length=100)
  private String tags;
  
  @Column(name="CNAME_", length=100)
  private String username;

  @Column(name="receiver")
  private String receiverName;

  public String getId()
  {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getSendDate() {
    return this.sendDate;
  }

  public void setSendDate(Date sendDate) {
    this.sendDate = sendDate;
  }

  public String getSender() {
    return this.sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String getReceiver() {
    return this.receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public boolean isRead() {
    return this.read;
  }

  public void setRead(boolean read) {
    this.read = read;
  }

  public boolean isReply() {
    return this.reply;
  }

  public void setReply(boolean reply) {
    this.reply = reply;
  }

  public String getTags() {
    return this.tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  public String getUsername() {
	  return username;
  }
  
  public void setUsername(String username) {
	  this.username = username;
  }

  public String getReceiverName() {
	  return receiverName;
  }
  
  public void setReceiverName(String receiverName) {
	  this.receiverName = receiverName;
  }
  
  public static long getSerialversionuid() {
	  return serialVersionUID;
  }
  
}