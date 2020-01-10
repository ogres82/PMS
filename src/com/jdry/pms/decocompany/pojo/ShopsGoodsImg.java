package com.jdry.pms.decocompany.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "t_shops_goods_img")
public class ShopsGoodsImg {

	String  id;
	String  goodsId;
	String  imgUrl;
	String  imgPath;
	  
	@Id
	@Column(name = "id")
	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id =  id;
	}
	
	@Column(name = "goods_id")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	@Column(name = "img_url")
	public String getImgUrl(){
		return this.imgUrl;
	}
	
	public void setImgUrl(String imgUrl){
		this.imgUrl =  imgUrl;
	}
	@Column(name = "img_path")
	public String getImgPath(){
		return this.imgPath;
	}
	
	public void setImgPath(String imgPath){
		this.imgPath =  imgPath;
	}
	  
}
